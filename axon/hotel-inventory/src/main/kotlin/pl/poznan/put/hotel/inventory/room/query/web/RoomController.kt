package pl.poznan.put.hotel.inventory.room.query.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.inventory.room.query.dto.RoomOverviewResponse
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val roomService: RoomService,
) {
    @GetMapping
    fun getAll(): Mono<List<RoomOverviewResponse>> =
        roomService.getAll()
}