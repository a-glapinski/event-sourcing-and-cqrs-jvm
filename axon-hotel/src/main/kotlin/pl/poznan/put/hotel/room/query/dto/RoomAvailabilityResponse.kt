package pl.poznan.put.hotel.room.query.dto

import pl.poznan.put.hotel.room.query.model.RoomAvailabilityEntity

data class RoomAvailabilityResponse(
    val roomNumber: Int,
    val roomDescription: String,
    val roomStatus: RoomStatusDto,
    val bookings: List<BookingDto>,
    val myFailedBookings: List<FailedBookingDto>,
) {
    constructor(roomAvailabilityEntity: RoomAvailabilityEntity) : this(
        roomNumber = roomAvailabilityEntity.roomNumber,
        roomDescription = roomAvailabilityEntity.roomDescription,
        roomStatus = RoomStatusDto(roomAvailabilityEntity.roomStatus),
        bookings = roomAvailabilityEntity.bookings.map { BookingDto(it) },
        myFailedBookings = roomAvailabilityEntity.failedBookings.map { FailedBookingDto(it) }
    )
}