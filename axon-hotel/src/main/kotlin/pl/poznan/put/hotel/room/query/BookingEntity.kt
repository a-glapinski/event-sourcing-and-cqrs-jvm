package pl.poznan.put.hotel.room.query

import java.time.Instant

data class BookingEntity(
    val id: String,
    val startDate: Instant,
    val endDate: Instant,
    val accountId: String,
)