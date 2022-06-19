package pl.poznan.put.hotel.booking.room.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.room.command.AddRoomCommand
import pl.poznan.put.hotel.booking.room.command.BookRoomCommand
import pl.poznan.put.hotel.booking.room.command.CheckInCommand
import pl.poznan.put.hotel.booking.room.command.CheckOutCommand
import pl.poznan.put.hotel.booking.room.command.MarkRoomAsPreparedCommand
import pl.poznan.put.hotel.booking.room.dto.RoomBookingDto
import pl.poznan.put.hotel.booking.room.dto.RoomRequest
import pl.poznan.put.util.axon.sendAny
import reactor.core.publisher.Mono
import java.util.*

@Service
class RoomCommandService(
    private val reactorCommandGateway: ReactorCommandGateway,
) {
    fun add(roomRequest: RoomRequest): Mono<Void> =
        reactorCommandGateway.sendAny(
            AddRoomCommand(
                roomNumber = roomRequest.roomNumber,
                roomDescription= roomRequest.description
            )
        )

    fun book(roomNumber: Int, roomBooking: RoomBookingDto): Mono<Void> =
        reactorCommandGateway.sendAny(
            BookRoomCommand(
                roomNumber = roomNumber,
                roomBooking = roomBooking.toDomain()
            )
        )

    fun markAsPrepared(roomNumber: Int, roomBookingId: UUID): Mono<Void> =
        reactorCommandGateway.sendAny(
            MarkRoomAsPreparedCommand(
                roomNumber = roomNumber,
                roomBookingId = roomBookingId
            )
        )

    fun checkIn(roomNumber: Int, roomBookingId: UUID): Mono<Void> =
        reactorCommandGateway.sendAny(
            CheckInCommand(
                roomNumber = roomNumber,
                roomBookingId = roomBookingId
            )
        )

    fun checkOut(roomNumber: Int, roomBookingId: UUID): Mono<Void> =
        reactorCommandGateway.sendAny(
            CheckOutCommand(
                roomNumber = roomNumber,
                roomBookingId = roomBookingId
            )
        )
}