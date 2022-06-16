package pl.poznan.put.hotel.booking.room.web

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.booking.room.dto.RoomBookingDto
import pl.poznan.put.hotel.booking.room.dto.RoomRequest
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/rooms")
class RoomCommandController(
    private val roomCommandService: RoomCommandService,
) {
    @PostMapping
    fun add(@RequestBody roomRequest: RoomRequest): Mono<Int> =
        roomCommandService.add(roomRequest)

    @PostMapping("/{roomNumber}/booked")
    fun book(@PathVariable roomNumber: Int, @RequestBody roomBooking: RoomBookingDto): Mono<Int> =
        roomCommandService.book(roomNumber, roomBooking)

    @PostMapping("/{roomNumber}/bookings/{roomBookingId}/prepared")
    fun markAsPrepared(@PathVariable roomNumber: Int, @PathVariable roomBookingId: UUID): Mono<Int> =
        roomCommandService.markAsPrepared(roomNumber, roomBookingId)

    @PostMapping("/{roomNumber}/bookings/{roomBookingId}/checked-in")
    fun checkIn(@PathVariable roomNumber: Int, @PathVariable roomBookingId: UUID): Mono<Int> =
        roomCommandService.checkIn(roomNumber, roomBookingId)

    @PostMapping("/{roomNumber}/bookings/{roomBookingId}/checked-out")
    fun checkOut(@PathVariable roomNumber: Int, @PathVariable roomBookingId: UUID): Mono<Int> =
        roomCommandService.checkOut(roomNumber, roomBookingId)
}