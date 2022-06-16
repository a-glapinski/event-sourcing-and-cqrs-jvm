package pl.poznan.put.hotel.booking.room

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.booking.room.dto.RoomCleaningScheduleResponse
import pl.poznan.put.hotel.booking.room.event.RoomBookedEvent
import pl.poznan.put.hotel.booking.room.event.RoomPreparedEvent
import pl.poznan.put.hotel.booking.room.model.BookingEntity
import pl.poznan.put.hotel.booking.room.model.RoomCleaningScheduleEntity
import pl.poznan.put.hotel.booking.room.query.FindAllRoomCleaningSchedulesQuery
import pl.poznan.put.util.repository.findByIdOrThrow

@Component
@ProcessingGroup("room-cleaning")
class RoomCleaningScheduleHandler(
    private val queryUpdateEmitter: QueryUpdateEmitter,
    private val roomCleaningScheduleRepository: RoomCleaningScheduleRepository,
) {
    @EventHandler
    fun on(event: RoomBookedEvent) {
        val booking = BookingEntity(
            id = event.roomBooking.bookingId,
            startDate = event.roomBooking.startDate,
            endDate = event.roomBooking.endDate,
            accountId = event.roomBooking.accountId
        )
        val roomCleaningSchedule = roomCleaningScheduleRepository.findByIdOrNull(event.roomNumber)
            ?.apply { bookings.add(booking) }
            ?: RoomCleaningScheduleEntity(
                roomNumber = event.roomNumber,
                bookings = mutableListOf(booking)
            )

        roomCleaningSchedule
            .let { roomCleaningScheduleRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(RoomCleaningScheduleResponse(it)) { _: FindAllRoomCleaningSchedulesQuery -> true }
            }
    }

    @EventHandler
    fun on(event: RoomPreparedEvent) {
        roomCleaningScheduleRepository.findByIdOrThrow(event.roomNumber)
            .apply {
                bookings.removeIf { it.id == event.roomBooking.bookingId }
            }
            .let { roomCleaningScheduleRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(RoomCleaningScheduleResponse(it)) { _: FindAllRoomCleaningSchedulesQuery -> true }
            }
    }

    @QueryHandler
    fun handle(query: FindAllRoomCleaningSchedulesQuery): List<RoomCleaningScheduleResponse> =
        roomCleaningScheduleRepository.findAll()
            .map { RoomCleaningScheduleResponse(it) }
}