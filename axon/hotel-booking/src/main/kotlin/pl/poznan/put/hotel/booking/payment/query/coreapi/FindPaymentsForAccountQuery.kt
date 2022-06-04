package pl.poznan.put.hotel.booking.payment.query.coreapi

import java.util.*

data class FindPaymentsForAccountQuery(
    val accountId: UUID,
)