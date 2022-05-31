package pl.poznan.put.hotel.room.query

import java.time.Instant

data class FailedBookingEntity(
    val id: String,
    val startDate: Instant?,
    val endDate: Instant?,
    val accountId: String,
    val reason: String,
)
