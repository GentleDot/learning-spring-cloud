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
            .route(predicateSpec ->
                predicateSpec.host("*.circuitbreaker.com")
                    .filters(gatewayFilterSpec -> gatewayFilterSpec
                        .circuitBreaker(config ->
                            config.setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                    .uri("http://httpbin.org:80"))
            .build();
    }
}
