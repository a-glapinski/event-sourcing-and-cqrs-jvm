package pl.poznan.put.hotel.booking.room.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RoomAvailabilityEntity(
    @Id
    val roomNumber: Int,
    val roomDescription: String,
    var roomStatus: RoomStatusEntity,
    val bookings: MutableList<BookingEntity> = mutableListOf(),
    val failedBookings: MutableList<FailedBookingEntity> = mutableListOf(),
)