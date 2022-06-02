package pl.poznan.put.hotel.room.query.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RoomCheckoutScheduleEntity(
    @Id
    var roomNumber: Int,
    var roomStatus: RoomStatusEntity,
    val bookings: MutableList<BookingEntity>,
)