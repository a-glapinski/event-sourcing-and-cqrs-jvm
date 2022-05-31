package pl.poznan.put.hotel

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.poznan.put.hotel.config.command.CommandProperties

@OpenAPIDefinition(info = Info(title = "Axon API", version = "1.0"))
@EnableConfigurationProperties(CommandProperties::class)
@SpringBootApplication
class AxonHotelApplication

fun main(args: Array<String>) {
    runApplication<AxonHotelApplication>(*args)
}
