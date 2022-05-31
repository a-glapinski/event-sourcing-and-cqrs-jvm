package pl.poznan.put.hotel.payment.query

import java.util.*

data class FindPaymentsForAccount(
    val accountId: UUID,
)