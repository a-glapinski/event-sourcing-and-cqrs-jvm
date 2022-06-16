package pl.poznan.put.hotel.booking

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@OpenAPIDefinition(info = Info(title = "Axon Hotel Booking API", version = "1.0"))
@ConfigurationPropertiesScan
@EnableDiscoveryClient
@SpringBootApplication
class AxonHotelBookingApplication

fun main(args: Array<String>) {
    runApplication<AxonHotelBookingApplication>(*args)
}
