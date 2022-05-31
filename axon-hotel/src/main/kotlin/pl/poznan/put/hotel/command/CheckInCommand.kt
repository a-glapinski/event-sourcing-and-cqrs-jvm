package pl.poznan.put.hotel.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CheckInCommand(
    @TargetAggregateIdentifier
    val roomNumber: Int,
    val roomBookingId: UUID,
)