package pl.poznan.put.hotel.room.query.dto

import java.time.Instant

data class RoomCleaningScheduleDto(
    val roomNumber: Int,
    val deadlines: List<Instant>,
)