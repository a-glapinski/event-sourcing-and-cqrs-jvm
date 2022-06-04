package pl.poznan.put.hotel.booking.room.query.dto

import pl.poznan.put.hotel.booking.room.query.model.BookingEntity
import java.time.Instant
import java.util.*

data class BookingDto(
    val id: UUID,
    val startDate: Instant,
    val endDate: Instant,
) {
    constructor(bookingEntity: BookingEntity) : this(
        id = bookingEntity.id,
        startDate = bookingEntity.startDate,
        endDate = bookingEntity.endDate
    )
}