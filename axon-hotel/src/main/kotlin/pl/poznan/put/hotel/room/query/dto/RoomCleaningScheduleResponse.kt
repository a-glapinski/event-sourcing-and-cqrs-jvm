package pl.poznan.put.hotel.room.query.dto

import pl.poznan.put.hotel.room.query.model.RoomCleaningScheduleEntity
import java.time.Instant

data class RoomCleaningScheduleResponse(
    val roomNumber: Int,
    val deadlines: List<Instant>,
) {
    constructor(roomCleaningScheduleEntity: RoomCleaningScheduleEntity) : this(
        roomNumber = roomCleaningScheduleEntity.roomNumber,
        deadlines = roomCleaningScheduleEntity.bookings.map { it.startDate }
    )
}