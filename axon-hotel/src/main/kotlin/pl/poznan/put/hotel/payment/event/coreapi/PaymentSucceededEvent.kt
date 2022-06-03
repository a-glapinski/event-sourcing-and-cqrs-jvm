package pl.poznan.put.hotel.payment.event.coreapi

import java.util.*

data class PaymentSucceededEvent(
    val paymentId: UUID,
)
