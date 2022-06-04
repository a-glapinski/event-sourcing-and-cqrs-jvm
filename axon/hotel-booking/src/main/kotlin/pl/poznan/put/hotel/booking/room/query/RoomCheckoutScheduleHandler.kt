package pl.poznan.put.hotel.booking.room.query

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomBookedEvent
import pl.poznan.put.hotel.booking.room.event.coreapi.RoomCheckedInEvent
import pl.poznan.put.hotel.booking.room.query.coreapi.FindAllRoomCheckoutSchedulesQuery
import pl.poznan.put.hotel.booking.room.query.dto.RoomCheckoutScheduleResponse
import pl.poznan.put.hotel.booking.room.query.model.BookingEntity
import pl.poznan.put.hotel.booking.room.query.model.RoomCheckoutScheduleEntity
import pl.poznan.put.hotel.booking.room.query.model.RoomStatusEntity
import pl.poznan.put.hotel.booking.util.repository.findByIdOrThrow

@Component
@ProcessingGroup("room-checkout")
class RoomCheckoutScheduleHandler(
    private val queryUpdateEmitter: QueryUpdateEmitter,
    private val roomCheckoutScheduleRepository: RoomCheckoutScheduleRepository,
) {
    @EventHandler
    fun on(event: RoomBookedEvent) {
        val booking = BookingEntity(
            id = event.roomBooking.bookingId,
            startDate = event.roomBooking.startDate,
            endDate = event.roomBooking.endDate,
            accountId = event.roomBooking.accountId
        )
        val roomCheckoutSchedule = roomCheckoutScheduleRepository.findByIdOrNull(event.roomNumber)
            ?.apply { bookings.add(booking) }
            ?: RoomCheckoutScheduleEntity(
                roomNumber = event.roomNumber,
                roomStatus = RoomStatusEntity.BOOKED,
                bookings = mutableListOf(booking)
            )

        roomCheckoutSchedule
            .let { roomCheckoutScheduleRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(RoomCheckoutScheduleResponse(it)) { _: FindAllRoomCheckoutSchedulesQuery -> true }
            }
    }

    @EventHandler
    fun on(event: RoomCheckedInEvent) {
        roomCheckoutScheduleRepository.findByIdOrThrow(event.roomNumber)
            .apply { roomStatus = RoomStatusEntity.TAKEN }
            .let { roomCheckoutScheduleRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(RoomCheckoutScheduleResponse(it)) { _: FindAllRoomCheckoutSchedulesQuery -> true }
            }
    }

    @QueryHandler
    fun handle(query: FindAllRoomCheckoutSchedulesQuery): List<RoomCheckoutScheduleResponse> =
        roomCheckoutScheduleRepository.findAll()
            .filter { it.roomStatus == RoomStatusEntity.TAKEN }
            .map { RoomCheckoutScheduleResponse(it) }
}