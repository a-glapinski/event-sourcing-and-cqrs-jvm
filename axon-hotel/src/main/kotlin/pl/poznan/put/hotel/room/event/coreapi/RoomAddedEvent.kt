package pl.poznan.put.hotel.room.event.coreapi

data class RoomAddedEvent(
    val roomNumber: Int,
    val roomDescription: String,
)