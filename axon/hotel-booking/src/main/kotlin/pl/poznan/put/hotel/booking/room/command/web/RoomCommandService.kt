package pl.poznan.put.hotel.booking.room.command.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.room.command.coreapi.AddRoomCommand
import pl.poznan.put.hotel.booking.room.command.coreapi.BookRoomCommand
import pl.poznan.put.hotel.booking.room.command.coreapi.CheckInCommand
import pl.poznan.put.hotel.booking.room.command.coreapi.CheckOutCommand
import pl.poznan.put.hotel.booking.room.command.coreapi.MarkRoomAsPreparedCommand
import pl.poznan.put.hotel.booking.room.command.dto.RoomBookingDto
import pl.poznan.put.hotel.booking.room.command.dto.RoomRequest
import pl.poznan.put.hotel.booking.room.query.coreapi.FindRoomAvailabilityForAccountQuery
import pl.poznan.put.hotel.booking.room.query.coreapi.FindRoomAvailabilityQuery
import pl.poznan.put.hotel.booking.room.query.dto.RoomAvailabilityResponse
import pl.poznan.put.util.axon.queryUpdates
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
                reactorCommandGateway.send<Any>(
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
                reactorCommandGateway.send<Any>(
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
                reactorCommandGateway.send<Any>(
                    CheckOutCommand(
                        roomNumber = roomNumber,
                        roomBookingId = roomBookingId
                    )
                )
            ).then(Mono.just(roomNumber))

    private fun subscribeToRoomUpdates(roomId: Int): Mono<RoomAvailabilityResponse> =
        reactorQueryGateway.queryUpdates<FindRoomAvailabilityQuery, RoomAvailabilityResponse>(
            FindRoomAvailabilityQuery(roomId)
        )
            .next()
            .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))

    private fun subscribeToRoomForAccountUpdates(roomId: Int, accountId: UUID) =
        reactorQueryGateway.queryUpdates<FindRoomAvailabilityForAccountQuery, RoomAvailabilityResponse>(
            FindRoomAvailabilityForAccountQuery(roomId, accountId)
        )
            .next()
            .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))

    companion object {
        private const val TIMEOUT_SECONDS = 5L
    }
}