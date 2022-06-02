package pl.poznan.put.hotel.room.query.dto

import pl.poznan.put.hotel.room.query.model.RoomCheckoutScheduleEntity

data class RoomCheckoutScheduleResponse(
    val roomNumber: Int,
    val bookings: List<BookingDto>,
) {
    constructor(roomCheckoutScheduleEntity: RoomCheckoutScheduleEntity) : this(
        roomNumber = roomCheckoutScheduleEntity.roomNumber,
        bookings = roomCheckoutScheduleEntity.bookings.map { BookingDto(it) }
    )
}