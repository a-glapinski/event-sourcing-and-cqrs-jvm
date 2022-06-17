package pl.poznan.put.hotel.booking.config

import org.axonframework.commandhandling.distributed.CommandRouter
import org.axonframework.commandhandling.distributed.RoutingStrategy
import org.axonframework.extensions.springcloud.commandhandling.SpringCloudCommandRouter
import org.axonframework.extensions.springcloud.commandhandling.mode.CapabilityDiscoveryMode
import org.axonframework.serialization.Serializer
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.serviceregistry.Registration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class DistributedCommandBusConfiguration {
    @Bean
    @Primary
    fun springCloudCommandRouter(
        discoveryClient: DiscoveryClient,
        localServiceInstance: Registration,
        routingStrategy: RoutingStrategy,
        capabilityDiscoveryMode: CapabilityDiscoveryMode,
        serializer: Serializer,
    ): CommandRouter =
        SpringCloudCommandRouter.builder()
            .discoveryClient(discoveryClient)
            .localServiceInstance(localServiceInstance)
            .routingStrategy(routingStrategy)
            .capabilityDiscoveryMode(capabilityDiscoveryMode)
            .serializer(serializer)
            .serviceInstanceFilter { instance -> instance.serviceId.matches(".*-command".toRegex()) }
            .build()
}