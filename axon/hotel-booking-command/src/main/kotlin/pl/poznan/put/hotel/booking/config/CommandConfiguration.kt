package pl.poznan.put.hotel.booking.config

import org.axonframework.commandhandling.CommandBus
import org.axonframework.common.caching.Cache
import org.axonframework.common.caching.WeakReferenceCache
import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.EventBus
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition
import org.axonframework.eventsourcing.SnapshotTriggerDefinition
import org.axonframework.eventsourcing.Snapshotter
import org.axonframework.messaging.interceptors.LoggingInterceptor
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

@Configuration
class CommandConfiguration {
    @Autowired
    fun registerCommandInterceptorsOnBus(commandBus: CommandBus) {
        commandBus.registerHandlerInterceptor(LoggingInterceptor())
    }

    @Autowired
    fun registerEventInterceptors(eventBus: EventBus) {
        eventBus.registerDispatchInterceptor(LoggingInterceptor())
    }

    @Autowired
    fun configure(config: EventProcessingConfigurer) {
        config.registerDefaultHandlerInterceptor { _, u -> LoggingInterceptor(u) }
    }

    @Bean
    fun accountCache(): Cache =
        WeakReferenceCache()

    @Bean
    fun paymentCache(): Cache =
        WeakReferenceCache()

    @Bean
    fun roomCache(): Cache =
        WeakReferenceCache()

    @Bean
    fun snapshotter(): SpringAggregateSnapshotterFactoryBean =
        SpringAggregateSnapshotterFactoryBean().apply {
            setExecutor(Executors.newSingleThreadExecutor())
        }

    @Bean("roomSnapshotTriggerDefinition")
    fun roomSnapshotTriggerDefinition(
        snapshotter: Snapshotter,
        commandProperties: CommandProperties,
    ): SnapshotTriggerDefinition = EventCountSnapshotTriggerDefinition(
        snapshotter,
        commandProperties.snapshotTriggerThresholdRoom
    )

    @Bean("accountSnapshotTriggerDefinition")
    fun accountSnapshotTriggerDefinition(
        snapshotter: Snapshotter,
        commandProperties: CommandProperties,
    ): SnapshotTriggerDefinition = EventCountSnapshotTriggerDefinition(
        snapshotter,
        commandProperties.snapshotTriggerThresholdAccount
    )

    @Bean("paymentSnapshotTriggerDefinition")
    fun paymentSnapshotTriggerDefinition(
        snapshotter: Snapshotter,
        commandProperties: CommandProperties,
    ): SnapshotTriggerDefinition = EventCountSnapshotTriggerDefinition(
        snapshotter,
        commandProperties.snapshotTriggerThresholdPayment
    )
}