package pl.poznan.put.hotel.payment.event.coreapi

import java.math.BigDecimal
import java.util.*

data class PaymentRequestedEvent(
    val paymentId: UUID,
    val accountId: UUID,
    val totalAmount: BigDecimal,
)