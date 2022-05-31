package pl.poznan.put.hotel.room.query.dto

import java.time.Instant

data class FailedBookingDto(
    val startDate: Instant?,
    val endDate: Instant?,
    val reason: String,
)