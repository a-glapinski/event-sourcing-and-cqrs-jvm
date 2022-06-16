package pl.poznan.put.hotel.booking.payment.query

import java.util.*

data class FindPaymentQuery(
    val paymentId: UUID,
)