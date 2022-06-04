package pl.poznan.put.hotel.booking.config.query

import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.messaging.interceptors.LoggingInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration
class QueryConfiguration {
    @Autowired
    fun configure(config: EventProcessingConfigurer) {
        config.registerDefaultHandlerInterceptor { _, u ->
            LoggingInterceptor(u)
        }
    }
}