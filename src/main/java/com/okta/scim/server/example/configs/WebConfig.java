/**
 * WebConfig.java
 *
 * Configures global web settings and registers interceptors.
 */
package com.okta.scim.server.example.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.okta.scim.server.example.interceptors.AuthInterceptor;

/**
 * Registers the {@link AuthInterceptor} for request handling.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Adds the authentication interceptor to all incoming requests.
     *
     * @param registry interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Use the Spring-managed bean, not a new instance
        registry.addInterceptor(new AuthInterceptor());
    }
}
