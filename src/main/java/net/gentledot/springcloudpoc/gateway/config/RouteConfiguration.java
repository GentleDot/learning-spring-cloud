package net.gentledot.springcloudpoc.gateway.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UriConfiguration.class)
public class RouteConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
        String httpUri = uriConfiguration.getHttpbin();

        return builder.routes()
            .route("user-service", predicateSpec ->
                predicateSpec.path("/user/**")
                    .filters(gatewayFilterSpec ->
                        gatewayFilterSpec.rewritePath("/user/?(?<segment>.*)", "/${segment}"))
                    .uri("lb://USER-SERVICE/")
            )

            .route("html-service", predicateSpec ->
                predicateSpec.path("/html/**")
                    .filters(gatewayFilterSpec ->
                        gatewayFilterSpec.rewritePath("/html/?(?<segment>.*)", "/${segment}"))
                    .uri("lb://HTML-SERVICE/"))
            .build();
    }
}
