package pl.poznan.put.hotel.booking.config.axon

import com.fasterxml.jackson.databind.ObjectMapper
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class SerializerConfiguration(
    private val objectMapper: ObjectMapper,
) {
    @Bean
    @Primary
    @Qualifier("serializer")
    fun serializer(): Serializer =
        JacksonSerializer.builder()
            .objectMapper(objectMapper.copy())
            .defaultTyping()
            .build()

    @Bean
    @Qualifier("messageSerializer")
    fun messageSerializer(
        @Qualifier("serializer") serializer: Serializer,
    ): Serializer =
        serializer
}