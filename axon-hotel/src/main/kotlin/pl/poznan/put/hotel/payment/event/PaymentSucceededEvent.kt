package pl.poznan.put.hotel.payment.event

import java.util.*

data class PaymentSucceededEvent(
    val paymentId: UUID,
)
