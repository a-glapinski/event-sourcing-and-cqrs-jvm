package pl.poznan.put.hotel.booking.room.dto

import pl.poznan.put.hotel.booking.room.model.RoomStatusEntity

fun RoomStatusEntity.toDto() = when (this) {
    RoomStatusEntity.READY -> RoomStatusDto.READY
    RoomStatusEntity.BOOKED -> RoomStatusDto.BOOKED
    RoomStatusEntity.TAKEN -> RoomStatusDto.TAKEN
    RoomStatusEntity.EMPTY -> RoomStatusDto.EMPTY
}