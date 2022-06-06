package pl.poznan.put.hotel.booking

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.poznan.put.hotel.booking.config.command.CommandProperties

@OpenAPIDefinition(info = Info(title = "Axon Hotel Booking API", version = "1.0"))
@EnableConfigurationProperties(CommandProperties::class)
@SpringBootApplication
class AxonHotelBookingApplication

fun main(args: Array<String>) {
    runApplication<AxonHotelBookingApplication>(*args)
}
