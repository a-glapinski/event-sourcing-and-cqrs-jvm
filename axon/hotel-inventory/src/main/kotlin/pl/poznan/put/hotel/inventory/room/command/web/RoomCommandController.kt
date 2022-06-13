package pl.poznan.put.hotel.inventory.room.command.web

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.inventory.room.query.dto.RoomRequest
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/rooms")
class RoomCommandController(
    private val roomCommandService: RoomCommandService,
) {
    @PostMapping
    fun create(@RequestBody roomRequest: RoomRequest): Mono<UUID> =
        roomCommandService.create(roomRequest)

    @PostMapping("/{roomId}/in-inventory")
    fun addToInventory(@PathVariable roomId: UUID): Mono<UUID> =
        roomCommandService.addToInventory(roomId)
}