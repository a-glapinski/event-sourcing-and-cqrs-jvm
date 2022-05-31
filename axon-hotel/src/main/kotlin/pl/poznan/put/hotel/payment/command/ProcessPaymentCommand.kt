package pl.poznan.put.hotel.payment.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class ProcessPaymentCommand(
    @TargetAggregateIdentifier
    val paymentId: UUID,
)