package pl.poznan.put.hotel.room.query.handling

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.room.event.RoomBookedEvent
import pl.poznan.put.hotel.room.event.RoomPreparedEvent
import pl.poznan.put.hotel.room.query.FindAllRoomCleaningSchedules
import pl.poznan.put.hotel.room.query.dto.RoomCleaningScheduleResponse
import pl.poznan.put.hotel.room.query.model.BookingEntity
import pl.poznan.put.hotel.room.query.model.RoomCleaningScheduleEntity
import pl.poznan.put.hotel.util.findByIdOrThrow

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
                queryUpdateEmitter.emit(RoomCleaningScheduleResponse(it)) { _: FindAllRoomCleaningSchedules -> true }
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
                queryUpdateEmitter.emit(RoomCleaningScheduleResponse(it)) { _: FindAllRoomCleaningSchedules -> true }
            }
    }

    @QueryHandler
    fun handle(query: FindAllRoomCleaningSchedules): List<RoomCleaningScheduleResponse> =
        roomCleaningScheduleRepository.findAll()
            .map { RoomCleaningScheduleResponse(it) }
}