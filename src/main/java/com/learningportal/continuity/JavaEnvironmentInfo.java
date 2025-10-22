package com.learningportal.continuity;

import java.time.LocalDateTime;

/**
 * Comprehensive Java environment information for technical state tracking.
 */
public class JavaEnvironmentInfo {
    
    private String version;
    private String vendor;
    private String runtimeName;
    private String runtimeVersion;
    private String vmName;
    private String vmVersion;
    private String vmVendor;
    private String javaHome;
    private String classPath;
    private long maxMemory;
    private long totalMemory;
    private long freeMemory;
    private LocalDateTime captureTime;
    
    public JavaEnvironmentInfo() {}
    
    // Getters and Setters
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getVendor() {
        return vendor;
    }
    
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    
    public String getRuntimeName() {
        return runtimeName;
    }
    
    public void setRuntimeName(String runtimeName) {
        this.runtimeName = runtimeName;
    }
    
    public String getRuntimeVersion() {
        return runtimeVersion;
    }
    
    public void setRuntimeVersion(String runtimeVersion) {
        this.runtimeVersion = runtimeVersion;
    }
    
    public String getVmName() {
        return vmName;
    }
    
    public void setVmName(String vmName) {
        this.vmName = vmName;
    }
    
    public String getVmVersion() {
        return vmVersion;
    }
    
    public void setVmVersion(String vmVersion) {
        this.vmVersion = vmVersion;
    }
    
    public String getVmVendor() {
        return vmVendor;
    }
    
    public void setVmVendor(String vmVendor) {
        this.vmVendor = vmVendor;
    }
    
    public String getJavaHome() {
        return javaHome;
    }
    
    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }
    
    public String getClassPath() {
        return classPath;
    }
    
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
    
    public long getMaxMemory() {
        return maxMemory;
    }
    
    public void setMaxMemory(long maxMemory) {
        this.maxMemory = maxMemory;
    }
    
    public long getTotalMemory() {
        return totalMemory;
    }
    
    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }
    
    public long getFreeMemory() {
        return freeMemory;
    }
    
    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }
    
    public LocalDateTime getCaptureTime() {
        return captureTime;
    }
    
    public void setCaptureTime(LocalDateTime captureTime) {
        this.captureTime = captureTime;
    }
    
    @Override
    public String toString() {
        return "JavaEnvironmentInfo{" +
                "version='" + version + '\'' +
                ", vendor='" + vendor + '\'' +
                ", vmName='" + vmName + '\'' +
                ", maxMemory=" + (maxMemory / 1024 / 1024) + "MB" +
                ", freeMemory=" + (freeMemory / 1024 / 1024) + "MB" +
                '}';
    }
}