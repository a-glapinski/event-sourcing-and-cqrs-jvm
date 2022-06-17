package pl.poznan.put.hotel.booking.room.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.room.command.AddRoomCommand
import pl.poznan.put.hotel.booking.room.command.BookRoomCommand
import pl.poznan.put.hotel.booking.room.command.CheckInCommand
import pl.poznan.put.hotel.booking.room.command.CheckOutCommand
import pl.poznan.put.hotel.booking.room.command.MarkRoomAsPreparedCommand
import pl.poznan.put.hotel.booking.room.dto.RoomAvailabilityResponse
import pl.poznan.put.hotel.booking.room.dto.RoomBookingDto
import pl.poznan.put.hotel.booking.room.dto.RoomRequest
import pl.poznan.put.hotel.booking.room.query.FindRoomAvailabilityForAccountQuery
import pl.poznan.put.hotel.booking.room.query.FindRoomAvailabilityQuery
import pl.poznan.put.util.axon.queryUpdates
import pl.poznan.put.util.axon.sendAny
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.*

@Service
class RoomCommandService(
    private val reactorCommandGateway: ReactorCommandGateway,
    private val reactorQueryGateway: ReactorQueryGateway,
) {
    fun add(roomRequest: RoomRequest): Mono<Int> =
        Mono
            .`when`(subscribeToRoomUpdates(roomRequest.roomNumber))
            .and(
                reactorCommandGateway.sendAny(
                    AddRoomCommand(
                        roomNumber = roomRequest.roomNumber,
                        roomDescription = roomRequest.description
                    )
                )
            )
            .then(Mono.just(roomRequest.roomNumber))

    fun book(roomNumber: Int, roomBooking: RoomBookingDto): Mono<Int> =
        Mono
            .`when`(subscribeToRoomForAccountUpdates(roomNumber, roomBooking.accountId))
            .and(
                reactorCommandGateway.sendAny(
                    BookRoomCommand(
                        roomNumber = roomNumber,
                        roomBooking = roomBooking.toDomain()
                    )
                )
            )
            .then(Mono.just(roomNumber))

    fun markAsPrepared(roomNumber: Int, roomBookingId: UUID): Mono<Int> =
        reactorCommandGateway.send(
            MarkRoomAsPreparedCommand(
                roomNumber = roomNumber,
                roomBookingId = roomBookingId
            )
        )

    fun checkIn(roomNumber: Int, roomBookingId: UUID): Mono<Int> =
        reactorCommandGateway.send(
            CheckInCommand(
                roomNumber = roomNumber,
                roomBookingId = roomBookingId
            )
        )

    fun checkOut(roomNumber: Int, roomBookingId: UUID): Mono<Int> =
        Mono.`when`(subscribeToRoomUpdates(roomNumber))
            .and(
                reactorCommandGateway.sendAny(
                    CheckOutCommand(
                        roomNumber = roomNumber,
                        roomBookingId = roomBookingId
                    )
                )
            ).then(Mono.just(roomNumber))

    private fun subscribeToRoomUpdates(roomId: Int): Mono<RoomAvailabilityResponse> =
        reactorQueryGateway.queryUpdates<_, RoomAvailabilityResponse>(
            FindRoomAvailabilityQuery(roomId)
        )
            .next()
            .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))

    private fun subscribeToRoomForAccountUpdates(roomId: Int, accountId: UUID) =
        reactorQueryGateway.queryUpdates<_, RoomAvailabilityResponse>(
            FindRoomAvailabilityForAccountQuery(roomId, accountId)
        )
            .next()
            .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))

    companion object {
        private const val TIMEOUT_SECONDS = 5L
    }
}