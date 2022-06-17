package pl.poznan.put.hotel.booking.room.dto

import pl.poznan.put.hotel.booking.room.model.RoomCheckoutScheduleEntity

data class RoomCheckoutScheduleResponse(
    val roomNumber: Int,
    val bookings: List<BookingDto>,
) {
    constructor(roomCheckoutScheduleEntity: RoomCheckoutScheduleEntity) : this(
        roomNumber = roomCheckoutScheduleEntity.roomNumber,
        bookings = roomCheckoutScheduleEntity.bookings.map { it.toDto() }
    )
}