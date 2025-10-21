package com.learningportal.config;

import com.learningportal.annotation.ApiVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

/**
 * API Versioning Configuration
 * Handles API versioning through headers and URL paths
 */
@Configuration
public class ApiVersioningConfig {

    @Bean
    public RequestMappingHandlerMapping apiVersionRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {
            @Override
            protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
                RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
                
                if (info == null) {
                    return null;
                }
                
                ApiVersion apiVersion = method.getAnnotation(ApiVersion.class);
                if (apiVersion == null) {
                    apiVersion = handlerType.getAnnotation(ApiVersion.class);
                }
                
                if (apiVersion != null) {
                    String version = apiVersion.value();
                    // Add version to path: /api/v1/modules
                    RequestMappingInfo versionInfo = RequestMappingInfo
                        .paths("/" + version)
                        .build();
                    info = versionInfo.combine(info);
                }
                
                return info;
            }
        };
    }
}