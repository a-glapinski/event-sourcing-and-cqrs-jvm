package pl.poznan.put.hotel.room.query.dto

import pl.poznan.put.hotel.room.query.model.RoomStatusEntity

enum class RoomStatusDto {
    READY,
    BOOKED,
    TAKEN,
    EMPTY;

    companion object {
        operator fun invoke(roomStatus: RoomStatusEntity) = when (roomStatus) {
            RoomStatusEntity.READY -> READY
            RoomStatusEntity.BOOKED -> BOOKED
            RoomStatusEntity.TAKEN -> TAKEN
            RoomStatusEntity.EMPTY -> EMPTY
        }
    }
}