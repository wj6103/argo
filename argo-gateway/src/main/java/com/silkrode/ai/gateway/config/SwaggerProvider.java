package com.silkrode.ai.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The information about the api that swagger requires.
 */
@Component
public class SwaggerProvider implements SwaggerResourcesProvider {

    private static final String SWAGGER2URL = "/v2/api-docs";
    private final RouteLocator routeLocator;

    @Value("${spring.application.name}")
    private String selfName;

    public SwaggerProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routeHosts = new ArrayList<>();

        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
                .filter(route -> !selfName.equals(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));

        Set<String> deal = new HashSet<>();
        routeHosts.forEach(instance -> {
            if (instance.contains("SERVICE")) {
                String url = "/" + instance.toLowerCase() + SWAGGER2URL;
                if (!deal.contains(url)) {
                    deal.add(url);
                    SwaggerResource swaggerResource = new SwaggerResource();
                    swaggerResource.setSwaggerVersion("2.0");
                    swaggerResource.setUrl(url);
                    swaggerResource.setName(instance);
                    resources.add(swaggerResource);
                }
            }
        });
        return resources;
    }
}
