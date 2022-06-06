package pl.poznan.put.hotel.inventory

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(info = Info(title = "Axon Hotel Inventory API", version = "1.0"))
@SpringBootApplication
class AxonHotelInventoryApplication

fun main(args: Array<String>) {
    runApplication<AxonHotelInventoryApplication>(*args)
}
