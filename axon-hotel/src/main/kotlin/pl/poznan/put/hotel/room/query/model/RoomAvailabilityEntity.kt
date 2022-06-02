package pl.poznan.put.hotel.room.query.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RoomAvailabilityEntity(
    @Id
    var roomNumber: Int,
    var roomDescription: String,
    var roomStatus: RoomStatusEntity,
    val bookings: MutableList<BookingEntity> = mutableListOf(),
    val failedBookings: MutableList<FailedBookingEntity> = mutableListOf(),
)