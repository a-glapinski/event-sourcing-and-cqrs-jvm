package pl.poznan.put.hotel.room.query.dto

import java.time.Instant

data class BookingDto(
    val id: String,
    val startDate: Instant,
    val endDate: Instant,
)