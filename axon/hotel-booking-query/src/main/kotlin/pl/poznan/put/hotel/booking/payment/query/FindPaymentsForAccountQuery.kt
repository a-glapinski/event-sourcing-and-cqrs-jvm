package pl.poznan.put.hotel.booking.payment.query

import java.util.*

data class FindPaymentsForAccountQuery(
    val accountId: UUID,
)