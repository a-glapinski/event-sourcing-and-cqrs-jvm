package pl.poznan.put.hotel.booking.payment.event

import java.util.*

data class PaymentSucceededEvent(
    val paymentId: UUID,
)
