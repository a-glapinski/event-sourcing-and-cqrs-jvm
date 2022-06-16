package pl.poznan.put.hotel.booking.room.event

data class RoomAddedEvent(
    val roomNumber: Int,
    val roomDescription: String,
)