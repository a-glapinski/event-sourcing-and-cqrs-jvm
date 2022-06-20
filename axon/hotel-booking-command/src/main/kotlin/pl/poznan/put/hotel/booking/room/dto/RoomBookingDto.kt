package pl.poznan.put.hotel.booking.room.dto

import pl.poznan.put.hotel.booking.room.valueobject.RoomBooking
import java.time.Instant
import java.util.*

data class RoomBookingDto(
    val roomBookingId: UUID,
    val startDate: Instant,
    val endDate: Instant,
    val accountId: UUID,
) {
    fun toDomain() = RoomBooking(
        bookingId = roomBookingId,
        startDate = startDate,
        endDate = endDate,
        accountId = accountId
    )
}