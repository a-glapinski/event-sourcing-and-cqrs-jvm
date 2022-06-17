package pl.poznan.put.hotel.booking.room.dto

import java.time.Instant

data class FailedBookingDto(
    val startDate: Instant?,
    val endDate: Instant?,
    val reason: String,
)