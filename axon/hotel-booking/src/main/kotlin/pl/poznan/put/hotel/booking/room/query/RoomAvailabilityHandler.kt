package pl.poznan.put.hotel.booking.room.query

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomAddedEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomBookedEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomBookingRejectedEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomCheckedOutEvent
import pl.poznan.put.hotel.booking.room.query.coreapi.FindRoomAvailabilityForAccountQuery
import pl.poznan.put.hotel.booking.room.query.coreapi.FindRoomAvailabilityQuery
import pl.poznan.put.hotel.booking.room.query.dto.RoomAvailabilityResponse
import pl.poznan.put.hotel.booking.room.query.model.BookingEntity
import pl.poznan.put.hotel.booking.room.query.model.FailedBookingEntity
import pl.poznan.put.hotel.booking.room.query.model.RoomAvailabilityEntity
import pl.poznan.put.hotel.booking.room.query.model.RoomStatusEntity
import pl.poznan.put.util.repository.findByIdOrThrow

@Component
@ProcessingGroup("room-availability")
class RoomAvailabilityHandler(
    private val queryUpdateEmitter: QueryUpdateEmitter,
    private val roomAvailabilityEntityRepository: RoomAvailabilityEntityRepository,
) {
    @EventHandler
    fun on(event: RoomAddedEvent) {
        RoomAvailabilityEntity(
            roomNumber = event.roomNumber,
            roomDescription = event.roomDescription,
            roomStatus = RoomStatusEntity.EMPTY
        )
            .let { roomAvailabilityEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(RoomAvailabilityResponse(it)) { query: FindRoomAvailabilityQuery ->
                    query.roomId == event.roomNumber
                }
            }
    }

    @EventHandler
    fun on(event: RoomBookedEvent) {
        roomAvailabilityEntityRepository.findByIdOrThrow(event.roomNumber)
            .apply {
                roomStatus = RoomStatusEntity.BOOKED
                bookings.add(
                    BookingEntity(
                        id = event.roomBooking.bookingId,
                        startDate = event.roomBooking.startDate,
                        endDate = event.roomBooking.endDate,
                        accountId = event.roomBooking.accountId
                    )
                )
            }
            .let { roomAvailabilityEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(
                    RoomAvailabilityResponse(it, event.roomBooking.accountId)
                ) { query: FindRoomAvailabilityForAccountQuery ->
                    query.accountId == event.roomBooking.accountId && query.roomId == event.roomNumber
                }
            }
            .also {
                queryUpdateEmitter.emit(RoomAvailabilityResponse(it)) { query: FindRoomAvailabilityQuery ->
                    query.roomId == event.roomNumber
                }
            }
    }

    @EventHandler
    fun on(event: RoomBookingRejectedEvent) {
        roomAvailabilityEntityRepository.findByIdOrThrow(event.roomNumber)
            .apply {
                failedBookings.add(
                    FailedBookingEntity(
                        id = event.roomBooking.bookingId,
                        startDate = event.roomBooking.startDate,
                        endDate = event.roomBooking.endDate,
                        accountId = event.roomBooking.accountId,
                        reason = event.reason
                    )
                )
            }
            .let { roomAvailabilityEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(
                    RoomAvailabilityResponse(it, event.roomBooking.accountId)
                ) { query: FindRoomAvailabilityForAccountQuery ->
                    query.accountId == event.roomBooking.accountId && query.roomId == event.roomNumber
                }
            }
            .also {
                queryUpdateEmitter.emit(RoomAvailabilityResponse(it)) { query: FindRoomAvailabilityQuery ->
                    query.roomId == event.roomNumber
                }
            }
    }

    @EventHandler
    fun on(event: RoomCheckedOutEvent) {
        val roomAvailability = roomAvailabilityEntityRepository.findByIdOrThrow(event.roomNumber)
        val accountId = roomAvailability.bookings
            .first { booking -> event.roomBookingId == booking.id }
            .accountId

        roomAvailability
            .apply {
                roomStatus = RoomStatusEntity.EMPTY
                bookings.removeIf { it.id == event.roomBookingId }
            }
            .let { roomAvailabilityEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(
                    RoomAvailabilityResponse(it, accountId)
                ) { query: FindRoomAvailabilityForAccountQuery ->
                    query.accountId == accountId && query.roomId == event.roomNumber
                }
            }
            .also {
                queryUpdateEmitter.emit(RoomAvailabilityResponse(it)) { query: FindRoomAvailabilityQuery ->
                    query.roomId == event.roomNumber
                }
            }
    }

    @QueryHandler
    fun handle(query: FindRoomAvailabilityForAccountQuery): RoomAvailabilityResponse =
        roomAvailabilityEntityRepository
            .findByIdOrThrow(query.roomId)
            .let { RoomAvailabilityResponse(it, query.accountId) }

    @QueryHandler
    fun handle(query: FindRoomAvailabilityQuery): RoomAvailabilityResponse =
        roomAvailabilityEntityRepository
            .findByIdOrThrow(query.roomId)
            .let { RoomAvailabilityResponse(it) }
}