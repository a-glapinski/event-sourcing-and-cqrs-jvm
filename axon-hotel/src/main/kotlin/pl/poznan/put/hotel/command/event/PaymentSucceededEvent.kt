package pl.poznan.put.hotel.command.event

import java.util.*

data class PaymentSucceededEvent(
    val paymentId: UUID,
)
