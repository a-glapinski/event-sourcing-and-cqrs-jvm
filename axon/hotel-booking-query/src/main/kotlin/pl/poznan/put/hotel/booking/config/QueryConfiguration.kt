package pl.poznan.put.hotel.booking.config

import com.mongodb.client.MongoClient
import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore
import org.axonframework.messaging.interceptors.LoggingInterceptor
import org.axonframework.serialization.Serializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.poznan.put.hotel.booking.config.query.AxonDefaultMongoTemplate
import pl.poznan.put.hotel.booking.config.query.AxonMongoTemplate

@Configuration
class QueryConfiguration {
    @Autowired
    fun configure(config: EventProcessingConfigurer) {
        config.registerDefaultHandlerInterceptor { _, u -> LoggingInterceptor(u) }
    }

    @Bean
    fun axonMongoTemplate(
        mongo: MongoClient,
        @Value("\${spring.data.mongodb.database}") databaseName: String,
    ): AxonMongoTemplate =
        AxonDefaultMongoTemplate.builder()
            .mongoDatabase(mongo, databaseName)
            .trackingTokensCollectionName("trackingTokens")
            .build()

    @Bean
    fun tokenStore(
        axonMongoTemplate: AxonMongoTemplate,
        @Qualifier("serializer") serializer: Serializer,
    ): TokenStore =
        MongoTokenStore.builder()
            .mongoTemplate(axonMongoTemplate)
            .serializer(serializer)
            .build()
}