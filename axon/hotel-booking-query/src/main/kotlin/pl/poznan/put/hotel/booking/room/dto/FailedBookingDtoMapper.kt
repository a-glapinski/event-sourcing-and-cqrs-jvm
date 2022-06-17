package pl.poznan.put.hotel.booking.room.dto

import pl.poznan.put.hotel.booking.room.model.FailedBookingEntity

fun FailedBookingEntity.toDto() = FailedBookingDto(
    startDate = startDate,
    endDate = endDate,
    reason = reason
)