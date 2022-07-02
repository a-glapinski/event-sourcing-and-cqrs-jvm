package pl.poznan.put.hotel.booking.config.axon.metrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.micrometer.core.instrument.Tags
import org.axonframework.commandhandling.CommandBus
import org.axonframework.config.Configurer
import org.axonframework.config.ConfigurerModule
import org.axonframework.config.MessageMonitorFactory
import org.axonframework.eventhandling.TrackingEventProcessor
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.micrometer.CapacityMonitor
import org.axonframework.micrometer.MessageCountingMonitor
import org.axonframework.micrometer.MessageTimerMonitor
import org.axonframework.micrometer.TagsUtil
import org.axonframework.monitoring.MultiMessageMonitor
import org.axonframework.queryhandling.QueryBus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetricsConfiguration {
    @Bean
    fun metricsConfigurer(meterRegistry: MeterRegistry) = ConfigurerModule { configurer ->
        instrumentEventStore(meterRegistry, configurer)
        instrumentEventProcessors(meterRegistry, configurer)
        instrumentCommandBus(meterRegistry, configurer)
        instrumentQueryBus(meterRegistry, configurer)
    }

    private fun instrumentEventStore(meterRegistry: MeterRegistry, configurer: Configurer) {
        val messageMonitorFactory = MessageMonitorFactory { _, _, componentName ->
            val messageCounter = MessageCountingMonitor.buildMonitor(
                componentName, meterRegistry
            ) { message ->
                Tags.of(TagsUtil.PAYLOAD_TYPE_TAG, message.payloadType.simpleName)
                    .and(
                        message.metaData.entries
                            .map { (key, value) ->
                                Tag.of(key, value.toString())
                            }
                    )
            }
            // Naming the Timer monitor/meter with the name of the component (eventStore)
            // Registering the Timer with custom tags: payloadType.
            val messageTimer = MessageTimerMonitor.buildMonitor(
                componentName, meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG,
                    message.payloadType.simpleName
                )
            }
            MultiMessageMonitor(messageCounter, messageTimer)
        }
        configurer.configureMessageMonitor(EventStore::class.java, messageMonitorFactory)
    }

    private fun instrumentEventProcessors(meterRegistry: MeterRegistry, configurer: Configurer) {
        val messageMonitorFactory = MessageMonitorFactory { _, _, componentName ->
            // Naming the Counter monitor/meter with the fixed name `eventProcessor`.
            // Registering the Counter with custom tags: payloadType and processorName.
            val messageCounter = MessageCountingMonitor.buildMonitor(
                "eventProcessor", meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG, message.payloadType.simpleName,
                    TagsUtil.PROCESSOR_NAME_TAG, componentName
                )
            }
            // Naming the Timer monitor/meter with the fixed name `eventProcessor`.
            // Registering the Timer with custom tags: payloadType and processorName.
            val messageTimer = MessageTimerMonitor.buildMonitor(
                "eventProcessor", meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG, message.payloadType.simpleName,
                    TagsUtil.PROCESSOR_NAME_TAG, componentName
                )
            }
            // Naming the Capacity/Gauge monitor/meter with the fixed name `eventProcessor`.
            // Registering the Capacity/Gauge with custom tags: payloadType and processorName.
            val capacityMonitor1Minute = CapacityMonitor.buildMonitor(
                "eventProcessor", meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG, message.payloadType.simpleName,
                    TagsUtil.PROCESSOR_NAME_TAG, componentName
                )
            }
            MultiMessageMonitor(
                messageCounter,
                messageTimer,
                capacityMonitor1Minute
            )
        }
        configurer.configureMessageMonitor(TrackingEventProcessor::class.java, messageMonitorFactory)
    }

    private fun instrumentCommandBus(meterRegistry: MeterRegistry, configurer: Configurer) {
        val messageMonitorFactory = MessageMonitorFactory { _, _, componentName ->
            val messageCounter = MessageCountingMonitor.buildMonitor(
                componentName, meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG, message.payloadType.simpleName,
                    "messageId", message.identifier
                )
            }
            val messageTimer = MessageTimerMonitor.buildMonitor(
                componentName, meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG,
                    message.payloadType.simpleName
                )
            }
            val capacityMonitor1Minute = CapacityMonitor.buildMonitor(
                componentName, meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG,
                    message.payloadType.simpleName
                )
            }
            MultiMessageMonitor(
                messageCounter,
                messageTimer,
                capacityMonitor1Minute
            )
        }
        configurer.configureMessageMonitor(CommandBus::class.java, messageMonitorFactory)
    }

    private fun instrumentQueryBus(meterRegistry: MeterRegistry, configurer: Configurer) {
        val messageMonitorFactory = MessageMonitorFactory { _, _, componentName ->
            val messageCounter = MessageCountingMonitor.buildMonitor(
                componentName, meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG, message.payloadType.simpleName,
                    "messageId", message.identifier
                )
            }
            val messageTimer = MessageTimerMonitor.buildMonitor(
                componentName, meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG,
                    message.payloadType.simpleName
                )
            }
            val capacityMonitor1Minute = CapacityMonitor.buildMonitor(
                componentName, meterRegistry
            ) { message ->
                Tags.of(
                    TagsUtil.PAYLOAD_TYPE_TAG,
                    message.payloadType.simpleName
                )
            }
            MultiMessageMonitor(
                messageCounter,
                messageTimer,
                capacityMonitor1Minute
            )
        }
        configurer.configureMessageMonitor(QueryBus::class.java, messageMonitorFactory)
    }
}