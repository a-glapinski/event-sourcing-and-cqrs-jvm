package pl.poznan.put.hotel.booking.room.dto

import pl.poznan.put.hotel.booking.room.model.BookingEntity

fun BookingEntity.toDto() = BookingDto(
    id = id,
    startDate = startDate,
    endDate = endDate
)