package pl.poznan.put.hotel.payment.query.coreapi

import java.util.*

data class FindPaymentQuery(
    val paymentId: UUID,
)