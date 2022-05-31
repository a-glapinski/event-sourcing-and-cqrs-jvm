package pl.poznan.put.hotel.room.query

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class RoomAvailabilityEntity(
    @Id
    val roomNumber: Int,
    val roomDescription: String,
    val roomStatus: RoomStatusEntity,
    val bookings: List<BookingEntity>,
    val failedBookings: List<FailedBookingEntity>,
)