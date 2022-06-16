package pl.poznan.put.gateway.config

import org.springdoc.core.GroupedOpenApi
import org.springdoc.core.SwaggerUiConfigParameters
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.gateway.route.RouteDefinitionLocator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class OpenApiConfig {
    @Bean
    @RefreshScope
    @Lazy(false)
    fun apis(
        swaggerUiConfigParameters: SwaggerUiConfigParameters,
        locator: RouteDefinitionLocator,
    ): List<GroupedOpenApi> {
        val routeDefinitions = locator.routeDefinitions.collectList().block()
        return routeDefinitions
            ?.filter { routeDefinition -> routeDefinition.id.matches("axon-.*".toRegex()) }
            ?.map { routeDefinition ->
                val name = routeDefinition.id
                swaggerUiConfigParameters.addGroup(name)
                GroupedOpenApi.builder()
                    .pathsToMatch("/$name/**")
                    .group(name)
                    .build()
            }
            .orEmpty()
    }
}