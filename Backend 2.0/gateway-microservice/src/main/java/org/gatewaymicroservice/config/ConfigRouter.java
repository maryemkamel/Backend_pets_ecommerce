package org.gatewaymicroservice.config;

import lombok.AllArgsConstructor;
import org.gatewaymicroservice.gatewayGlobalFilter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.gatewaymicroservice.constants.MSConstant.*;

@Configuration
@AllArgsConstructor
public class ConfigRouter {
    private final JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-microservice", r -> r.path("/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-microservice"))
                .route("auth-microservice", r -> r.path("/auth/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://auth-microservice"))
                .route("Product-Microservice", r -> r.path("/categories/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://Product-Microservice"))
                .route("Product-Microservice", r -> r.path("/subcategories/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://Product-Microservice"))
                .route("Product-Microservice", r -> r.path("/products/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://Product-Microservice"))
                .route("orders", r-> r.path("/orders/**")
                        .filters(f->f.filters(filter))
                        .uri("lb://orders"))
                .route("orders", r-> r.path("/orders/**")
                        .filters(f->f.filters(filter))
                        .uri("lb://orders"))
                .route("Review-Microservice", r -> r.path("/reviews/**")
                        .filters(f->f.filter(filter))
                        .uri("lb://Review-Microservice"))
                .build();
    }

               /* .route(USER_MICROSERVICE, r -> r.path(USER_PATH)
                        .filters(f->f.filter(filter))
                        .uri(USER_MICROSERVICE_URI))

                .route(CATALOGUE_MICROSERVICE, r -> r.path(CATALOGUE_PRODUCTS_PATH)
                        .uri(CATALOGUE_MICROSERVICE_URI))

                .route(CATALOGUE_MICROSERVICE, r -> r.path(CATALOGUE_CATEGORIES_PATH)
                        .uri(CATALOGUE_MICROSERVICE_URI))

                .route(PAYMENT_MICROSERVICE, r -> r.path(PAYMENT_PATH)
                        .filters(f->f.filter(filter))
                        .uri(PAYMENT_MICROSERVICE_URI))

                .route(ORDER_MICROSERVICE, r -> r.path(ORDER_PATH)
                        .uri(ORDER_MICROSERVICE_URI))

                .route(AUTH_MICROSERVICE, r -> r.path(AUTH_PATH)
                        .filters(f->f.filter(filter))
                        .uri(AUTH_MICROSERVICE_URI))

                .build();
    }
*/

}
