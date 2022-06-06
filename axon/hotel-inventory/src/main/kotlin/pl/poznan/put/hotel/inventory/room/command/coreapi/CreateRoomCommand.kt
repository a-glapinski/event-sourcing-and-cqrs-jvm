package pl.poznan.put.hotel.inventory.room.command.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateRoomCommand(
    @TargetAggregateIdentifier
    val roomId: UUID,
    val roomNumber: Int,
    val roomDescription: String,
)