package pl.poznan.put.hotel.booking.room.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import pl.poznan.put.hotel.booking.room.valueobject.RoomBooking

data class BookRoomCommand(
    @TargetAggregateIdentifier
    val roomNumber: Int,
    val roomBooking: RoomBooking,
)