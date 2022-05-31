package pl.poznan.put.hotel.payment.model.event

import java.util.*

data class PaymentSucceededEvent(
    val paymentId: UUID,
)
