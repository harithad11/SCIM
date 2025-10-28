/**
 * WebConfig.java
 *
 * Configures global web settings and registers interceptors.
 */
package com.okta.scim.server.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        registry.addInterceptor(new AuthInterceptor());
    }
}
