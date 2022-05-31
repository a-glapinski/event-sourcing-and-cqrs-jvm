package pl.poznan.put.hotel.room.query

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RoomCleaningScheduleEntity(
    @Id
    val roomNumber: Int,
    val bookings: List<BookingEntity>,
)
