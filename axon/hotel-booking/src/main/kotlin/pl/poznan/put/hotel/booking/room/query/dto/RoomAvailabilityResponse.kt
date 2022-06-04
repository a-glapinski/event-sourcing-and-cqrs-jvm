package pl.poznan.put.hotel.booking.room.query.dto

import pl.poznan.put.hotel.booking.room.query.model.RoomAvailabilityEntity
import java.util.*

data class RoomAvailabilityResponse(
    val roomNumber: Int,
    val roomDescription: String,
    val roomStatus: RoomStatusDto,
    val bookings: List<BookingDto>,
    val myFailedBookings: List<FailedBookingDto>,
) {
    constructor(roomAvailabilityEntity: RoomAvailabilityEntity, accountId: UUID? = null) : this(
        roomNumber = roomAvailabilityEntity.roomNumber,
        roomDescription = roomAvailabilityEntity.roomDescription,
        roomStatus = RoomStatusDto(roomAvailabilityEntity.roomStatus),
        bookings =
        if (accountId == null) roomAvailabilityEntity.bookings.map { BookingDto(it) }
        else roomAvailabilityEntity.bookings.filter { it.accountId == accountId }.map { BookingDto(it) },
        myFailedBookings =
        if (accountId == null) emptyList()
        else roomAvailabilityEntity.failedBookings.filter { it.accountId == accountId }.map { FailedBookingDto(it) }
    )
}