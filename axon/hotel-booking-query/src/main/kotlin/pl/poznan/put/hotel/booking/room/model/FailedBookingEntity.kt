package pl.poznan.put.hotel.booking.room.model

import java.time.Instant
import java.util.*

data class FailedBookingEntity(
    val id: UUID,
    val startDate: Instant?,
    val endDate: Instant?,
    val accountId: UUID,
    val reason: String,
)
