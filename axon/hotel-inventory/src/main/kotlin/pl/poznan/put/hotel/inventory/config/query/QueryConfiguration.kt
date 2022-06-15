package pl.poznan.put.hotel.inventory.config.query

import com.mongodb.client.MongoClient
import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore
import org.axonframework.serialization.Serializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryConfiguration {
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