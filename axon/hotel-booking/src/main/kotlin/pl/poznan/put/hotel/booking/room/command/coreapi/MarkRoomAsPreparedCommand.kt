package pl.poznan.put.hotel.booking.room.command.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class MarkRoomAsPreparedCommand(
    @TargetAggregateIdentifier
    val roomNumber: Int,
    val roomBookingId: UUID,
)