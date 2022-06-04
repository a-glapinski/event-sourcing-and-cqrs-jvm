package pl.poznan.put.hotel.booking.room.event.coreapi

data class RoomAddedEvent(
    val roomNumber: Int,
    val roomDescription: String,
)