package pl.poznan.put.hotel.booking.payment.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.math.BigDecimal
import java.util.*

data class PayCommand(
    val paymentId: UUID,
    @TargetAggregateIdentifier
    val accountId: UUID,
    val totalAmount: BigDecimal,
)