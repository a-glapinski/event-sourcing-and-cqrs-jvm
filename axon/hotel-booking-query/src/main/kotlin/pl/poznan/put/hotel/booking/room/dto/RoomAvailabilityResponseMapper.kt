package pl.poznan.put.hotel.booking.room.dto

import pl.poznan.put.hotel.booking.room.model.RoomAvailabilityEntity
import java.util.*

fun RoomAvailabilityEntity.toResponse(accountId: UUID? = null) = RoomAvailabilityResponse(
    roomNumber = roomNumber,
    roomDescription = roomDescription,
    roomStatus = roomStatus.toDto(),
    bookings = if (accountId == null) bookings.map { it.toDto() }
    else bookings.filter { it.accountId == accountId }.map { it.toDto() },
    myFailedBookings = if (accountId == null) emptyList()
    else failedBookings.filter { it.accountId == accountId }.map { it.toDto() }
)