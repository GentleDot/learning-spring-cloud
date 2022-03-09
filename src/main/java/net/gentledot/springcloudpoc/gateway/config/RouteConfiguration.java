package net.gentledot.springcloudpoc.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(predicateSpec ->
                predicateSpec.path("/get")
                    .filters(
                        gatewayFilterSpec -> gatewayFilterSpec.addRequestHeader("Hello", "World"))
                    .uri("http://httpbin.org:80"))
            .build();
    }
}
