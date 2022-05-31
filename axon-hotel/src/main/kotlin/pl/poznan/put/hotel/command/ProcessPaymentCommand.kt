package pl.poznan.put.hotel.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class ProcessPaymentCommand(
    @TargetAggregateIdentifier
    val paymentId: UUID,
)