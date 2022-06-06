package pl.poznan.put.hotel.inventory.room.command.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class MarkRoomAsAddedToBookingSystemCommand(
    @TargetAggregateIdentifier
    val roomId: UUID,
)