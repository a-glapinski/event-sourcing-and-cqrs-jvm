package pl.poznan.put.hotel.booking.room.model

import java.time.Instant
import java.util.*

data class BookingEntity(
    val id: UUID,
    val startDate: Instant,
    val endDate: Instant,
    val accountId: UUID,
)