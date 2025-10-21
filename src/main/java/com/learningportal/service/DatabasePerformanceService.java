package com.learningportal.service;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database Performance Monitoring Service
 * 
 * Monitors database performance including connection pool metrics,
 * slow query detection, and database optimization recommendations
 */
@Service
public class DatabasePerformanceService {

    private static final Logger log = LoggerFactory.getLogger(DatabasePerformanceService.class);
    
    private final DataSource dataSource;
    private final PerformanceMonitoringService performanceMonitoringService;
    
    // Slow query threshold in milliseconds
    private static final long SLOW_QUERY_THRESHOLD = 1000;
    
    public DatabasePerformanceService(DataSource dataSource, 
                                    PerformanceMonitoringService performanceMonitoringService) {
        this.dataSource = dataSource;
        this.performanceMonitoringService = performanceMonitoringService;
    }

    /**
     * Get comprehensive database performance metrics
     */
    public DatabasePerformanceMetrics getDatabasePerformanceMetrics() {
        DatabasePerformanceMetrics metrics = new DatabasePerformanceMetrics();
        metrics.setTimestamp(LocalDateTime.now());
        
        // Connection pool metrics
        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            HikariPoolMXBean poolBean = hikariDataSource.getHikariPoolMXBean();
            
            ConnectionPoolMetrics poolMetrics = new ConnectionPoolMetrics();
            poolMetrics.setActiveConnections(poolBean.getActiveConnections());
            poolMetrics.setIdleConnections(poolBean.getIdleConnections());
            poolMetrics.setTotalConnections(poolBean.getTotalConnections());
            poolMetrics.setThreadsAwaitingConnection(poolBean.getThreadsAwaitingConnection());
            poolMetrics.setMaximumPoolSize(hikariDataSource.getMaximumPoolSize());
            poolMetrics.setMinimumIdle(hikariDataSource.getMinimumIdle());
            
            metrics.setConnectionPoolMetrics(poolMetrics);
        }
        
        // Database statistics
        try {
            DatabaseStatistics dbStats = collectDatabaseStatistics();
            metrics.setDatabaseStatistics(dbStats);
        } catch (SQLException e) {
            log.error("Error collecting database statistics", e);
        }
        
        // Performance recommendations
        metrics.setRecommendations(generatePerformanceRecommendations(metrics));
        
        return metrics;
    }

    /**
     * Execute query with performance monitoring
     */
    public <T> T executeWithMonitoring(String queryName, QueryExecutor<T> executor) throws SQLException {
        long startTime = System.currentTimeMillis();
        
        try {
            T result = executor.execute();
            long executionTime = System.currentTimeMillis() - startTime;
            
            // Record query performance
            performanceMonitoringService.recordDatabaseQueryTime(queryName, executionTime);
            
            // Log slow queries
            if (executionTime > SLOW_QUERY_THRESHOLD) {
                log.warn("Slow query detected: {} took {}ms", queryName, executionTime);
            }
            
            return result;
        } catch (SQLException e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("Query failed: {} after {}ms", queryName, executionTime, e);
            throw e;
        }
    }

    /**
     * Collect database statistics from PostgreSQL
     */
    private DatabaseStatistics collectDatabaseStatistics() throws SQLException {
        DatabaseStatistics stats = new DatabaseStatistics();
        
        try (Connection connection = dataSource.getConnection()) {
            // Database size
            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT pg_size_pretty(pg_database_size(current_database())) as db_size")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    stats.setDatabaseSize(rs.getString("db_size"));
                }
            }
            
            // Active connections
            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT count(*) as active_connections FROM pg_stat_activity WHERE state = 'active'")) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    stats.setActiveConnections(rs.getInt("active_connections"));
                }
            }
            
            // Table statistics
            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT schemaname, tablename, n_tup_ins, n_tup_upd, n_tup_del, n_live_tup, n_dead_tup " +
                "FROM pg_stat_user_tables ORDER BY n_live_tup DESC LIMIT 10")) {
                ResultSet rs = stmt.executeQuery();
                List<TableStatistics> tableStats = new ArrayList<>();
                
                while (rs.next()) {
                    TableStatistics tableStat = new TableStatistics();
                    tableStat.setSchemaName(rs.getString("schemaname"));
                    tableStat.setTableName(rs.getString("tablename"));
                    tableStat.setInserts(rs.getLong("n_tup_ins"));
                    tableStat.setUpdates(rs.getLong("n_tup_upd"));
                    tableStat.setDeletes(rs.getLong("n_tup_del"));
                    tableStat.setLiveTuples(rs.getLong("n_live_tup"));
                    tableStat.setDeadTuples(rs.getLong("n_dead_tup"));
                    tableStats.add(tableStat);
                }
                
                stats.setTableStatistics(tableStats);
            }
            
            // Index usage statistics
            try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT schemaname, tablename, indexname, idx_tup_read, idx_tup_fetch " +
                "FROM pg_stat_user_indexes ORDER BY idx_tup_read DESC LIMIT 10")) {
                ResultSet rs = stmt.executeQuery();
                List<IndexStatistics> indexStats = new ArrayList<>();
                
                while (rs.next()) {
                    IndexStatistics indexStat = new IndexStatistics();
                    indexStat.setSchemaName(rs.getString("schemaname"));
                    indexStat.setTableName(rs.getString("tablename"));
                    indexStat.setIndexName(rs.getString("indexname"));
                    indexStat.setTuplesRead(rs.getLong("idx_tup_read"));
                    indexStat.setTuplesFetched(rs.getLong("idx_tup_fetch"));
                    indexStats.add(indexStat);
                }
                
                stats.setIndexStatistics(indexStats);
            }
        }
        
        return stats;
    }

    /**
     * Generate performance optimization recommendations
     */
    private List<String> generatePerformanceRecommendations(DatabasePerformanceMetrics metrics) {
        List<String> recommendations = new ArrayList<>();
        
        ConnectionPoolMetrics poolMetrics = metrics.getConnectionPoolMetrics();
        if (poolMetrics != null) {
            // Connection pool recommendations
            double poolUtilization = (double) poolMetrics.getActiveConnections() / poolMetrics.getMaximumPoolSize();
            
            if (poolUtilization > 0.8) {
                recommendations.add("High connection pool utilization (" + 
                    String.format("%.1f", poolUtilization * 100) + "%). Consider increasing maximum pool size.");
            }
            
            if (poolMetrics.getThreadsAwaitingConnection() > 0) {
                recommendations.add("Threads waiting for connections detected. Consider optimizing query performance or increasing pool size.");
            }
            
            if (poolMetrics.getIdleConnections() > poolMetrics.getMaximumPoolSize() * 0.5) {
                recommendations.add("High number of idle connections. Consider reducing minimum idle connections.");
            }
        }
        
        DatabaseStatistics dbStats = metrics.getDatabaseStatistics();
        if (dbStats != null && dbStats.getTableStatistics() != null) {
            // Table-specific recommendations
            for (TableStatistics tableStat : dbStats.getTableStatistics()) {
                if (tableStat.getDeadTuples() > tableStat.getLiveTuples() * 0.1) {
                    recommendations.add("Table " + tableStat.getTableName() + 
                        " has high dead tuple ratio. Consider running VACUUM ANALYZE.");
                }
            }
        }
        
        if (recommendations.isEmpty()) {
            recommendations.add("Database performance is optimal. No immediate optimizations needed.");
        }
        
        return recommendations;
    }

    /**
     * Functional interface for query execution
     */
    @FunctionalInterface
    public interface QueryExecutor<T> {
        T execute() throws SQLException;
    }

    // Data classes for metrics
    public static class DatabasePerformanceMetrics {
        private LocalDateTime timestamp;
        private ConnectionPoolMetrics connectionPoolMetrics;
        private DatabaseStatistics databaseStatistics;
        private List<String> recommendations;

        // Getters and setters
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        
        public ConnectionPoolMetrics getConnectionPoolMetrics() { return connectionPoolMetrics; }
        public void setConnectionPoolMetrics(ConnectionPoolMetrics connectionPoolMetrics) { this.connectionPoolMetrics = connectionPoolMetrics; }
        
        public DatabaseStatistics getDatabaseStatistics() { return databaseStatistics; }
        public void setDatabaseStatistics(DatabaseStatistics databaseStatistics) { this.databaseStatistics = databaseStatistics; }
        
        public List<String> getRecommendations() { return recommendations; }
        public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    }

    public static class ConnectionPoolMetrics {
        private int activeConnections;
        private int idleConnections;
        private int totalConnections;
        private int threadsAwaitingConnection;
        private int maximumPoolSize;
        private int minimumIdle;

        // Getters and setters
        public int getActiveConnections() { return activeConnections; }
        public void setActiveConnections(int activeConnections) { this.activeConnections = activeConnections; }
        
        public int getIdleConnections() { return idleConnections; }
        public void setIdleConnections(int idleConnections) { this.idleConnections = idleConnections; }
        
        public int getTotalConnections() { return totalConnections; }
        public void setTotalConnections(int totalConnections) { this.totalConnections = totalConnections; }
        
        public int getThreadsAwaitingConnection() { return threadsAwaitingConnection; }
        public void setThreadsAwaitingConnection(int threadsAwaitingConnection) { this.threadsAwaitingConnection = threadsAwaitingConnection; }
        
        public int getMaximumPoolSize() { return maximumPoolSize; }
        public void setMaximumPoolSize(int maximumPoolSize) { this.maximumPoolSize = maximumPoolSize; }
        
        public int getMinimumIdle() { return minimumIdle; }
        public void setMinimumIdle(int minimumIdle) { this.minimumIdle = minimumIdle; }
    }

    public static class DatabaseStatistics {
        private String databaseSize;
        private int activeConnections;
        private List<TableStatistics> tableStatistics;
        private List<IndexStatistics> indexStatistics;

        // Getters and setters
        public String getDatabaseSize() { return databaseSize; }
        public void setDatabaseSize(String databaseSize) { this.databaseSize = databaseSize; }
        
        public int getActiveConnections() { return activeConnections; }
        public void setActiveConnections(int activeConnections) { this.activeConnections = activeConnections; }
        
        public List<TableStatistics> getTableStatistics() { return tableStatistics; }
        public void setTableStatistics(List<TableStatistics> tableStatistics) { this.tableStatistics = tableStatistics; }
        
        public List<IndexStatistics> getIndexStatistics() { return indexStatistics; }
        public void setIndexStatistics(List<IndexStatistics> indexStatistics) { this.indexStatistics = indexStatistics; }
    }

    public static class TableStatistics {
        private String schemaName;
        private String tableName;
        private long inserts;
        private long updates;
        private long deletes;
        private long liveTuples;
        private long deadTuples;

        // Getters and setters
        public String getSchemaName() { return schemaName; }
        public void setSchemaName(String schemaName) { this.schemaName = schemaName; }
        
        public String getTableName() { return tableName; }
        public void setTableName(String tableName) { this.tableName = tableName; }
        
        public long getInserts() { return inserts; }
        public void setInserts(long inserts) { this.inserts = inserts; }
        
        public long getUpdates() { return updates; }
        public void setUpdates(long updates) { this.updates = updates; }
        
        public long getDeletes() { return deletes; }
        public void setDeletes(long deletes) { this.deletes = deletes; }
        
        public long getLiveTuples() { return liveTuples; }
        public void setLiveTuples(long liveTuples) { this.liveTuples = liveTuples; }
        
        public long getDeadTuples() { return deadTuples; }
        public void setDeadTuples(long deadTuples) { this.deadTuples = deadTuples; }
    }

    public static class IndexStatistics {
        private String schemaName;
        private String tableName;
        private String indexName;
        private long tuplesRead;
        private long tuplesFetched;

        // Getters and setters
        public String getSchemaName() { return schemaName; }
        public void setSchemaName(String schemaName) { this.schemaName = schemaName; }
        
        public String getTableName() { return tableName; }
        public void setTableName(String tableName) { this.tableName = tableName; }
        
        public String getIndexName() { return indexName; }
        public void setIndexName(String indexName) { this.indexName = indexName; }
        
        public long getTuplesRead() { return tuplesRead; }
        public void setTuplesRead(long tuplesRead) { this.tuplesRead = tuplesRead; }
        
        public long getTuplesFetched() { return tuplesFetched; }
        public void setTuplesFetched(long tuplesFetched) { this.tuplesFetched = tuplesFetched; }
    }
}