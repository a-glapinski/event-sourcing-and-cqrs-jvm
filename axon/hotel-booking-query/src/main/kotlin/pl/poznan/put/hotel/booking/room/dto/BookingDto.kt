package pl.poznan.put.hotel.booking.room.dto

import pl.poznan.put.hotel.booking.room.model.BookingEntity
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