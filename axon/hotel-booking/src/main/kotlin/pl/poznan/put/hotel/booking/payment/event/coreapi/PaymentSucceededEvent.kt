package pl.poznan.put.hotel.booking.payment.event.coreapi

import java.util.*

data class PaymentSucceededEvent(
    val paymentId: UUID,
)
