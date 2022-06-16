package pl.poznan.put.hotel.booking.payment.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class ProcessPaymentCommand(
    @TargetAggregateIdentifier
    val paymentId: UUID,
)