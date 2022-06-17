package pl.poznan.put.hotel.booking.room.dto

import java.time.Instant
import java.util.*

data class BookingDto(
    val id: UUID,
    val startDate: Instant,
    val endDate: Instant,
)