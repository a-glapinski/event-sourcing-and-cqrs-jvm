package pl.poznan.put.hotel.booking.room.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RoomCheckoutScheduleEntity(
    @Id
    val roomNumber: Int,
    var roomStatus: RoomStatusEntity,
    val bookings: MutableList<BookingEntity>,
)