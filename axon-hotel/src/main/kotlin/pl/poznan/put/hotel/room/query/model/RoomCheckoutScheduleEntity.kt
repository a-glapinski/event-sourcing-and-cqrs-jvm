package pl.poznan.put.hotel.room.query.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RoomCheckoutScheduleEntity(
    @Id
    val roomNumber: Int,
    val roomStatus: RoomStatusEntity,
    val bookings: List<BookingEntity>,
)