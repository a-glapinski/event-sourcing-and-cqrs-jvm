package pl.poznan.put.hotel.room.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class MarkRoomAsPreparedCommand(
    @TargetAggregateIdentifier
    val roomNumber: Int,
    val roomBookingId: UUID,
)