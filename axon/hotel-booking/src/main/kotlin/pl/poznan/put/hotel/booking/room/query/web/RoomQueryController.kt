package pl.poznan.put.hotel.booking.room.query.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.booking.room.query.dto.RoomAvailabilityResponse
import pl.poznan.put.hotel.booking.room.query.dto.RoomCheckoutScheduleResponse
import pl.poznan.put.hotel.booking.room.query.dto.RoomCleaningScheduleResponse
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/rooms")
class RoomQueryController(
    private val roomQueryService: RoomQueryService,
) {
    @GetMapping("/{roomId}/availability")
    fun getAvailability(@PathVariable roomId: Int): Mono<RoomAvailabilityResponse> =
        roomQueryService.getAvailability(roomId)

    @GetMapping("/{roomId}/account/{accountId}/availability")
    fun getAvailability(@PathVariable roomId: Int, @PathVariable accountId: UUID): Mono<RoomAvailabilityResponse> =
        roomQueryService.getAvailability(roomId, accountId)

    @GetMapping("/cleaning-schedules")
    fun getAllCleaningSchedules(): Mono<List<RoomCleaningScheduleResponse>> =
        roomQueryService.getAllCleaningSchedules()

    @GetMapping("/checkout-schedules")
    fun getAllCheckoutSchedules(): Mono<List<RoomCheckoutScheduleResponse>> =
        roomQueryService.getAllCheckoutSchedules()
}