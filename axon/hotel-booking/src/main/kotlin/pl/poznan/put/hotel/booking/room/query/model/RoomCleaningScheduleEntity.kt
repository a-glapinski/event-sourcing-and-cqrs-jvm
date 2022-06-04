package pl.poznan.put.hotel.booking.room.query.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RoomCleaningScheduleEntity(
    @Id
    var roomNumber: Int,
    val bookings: MutableList<BookingEntity>,
)
