package pl.poznan.put.hotel.booking.config

import org.axonframework.eventhandling.tokenstore.TokenStore
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore
import org.axonframework.serialization.Serializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.axonframework.extensions.mongo.DefaultMongoTemplate as AxonDefaultMongoTemplate
import org.axonframework.extensions.mongo.MongoTemplate as AxonMongoTemplate

@Configuration
class TokenStoreConfiguration {
    @Bean
    fun axonMongoTemplate(
        mongoDatabaseFactory: MongoDatabaseFactory,
    ): AxonMongoTemplate =
        AxonDefaultMongoTemplate.builder()
            .mongoDatabase(mongoDatabaseFactory.mongoDatabase)
            .trackingTokensCollectionName("trackingTokens")
            .build()

    @Bean
    fun tokenStore(
        axonMongoTemplate: AxonMongoTemplate,
        serializer: Serializer,
    ): TokenStore =
        MongoTokenStore.builder()
            .mongoTemplate(axonMongoTemplate)
            .serializer(serializer)
            .build()
}