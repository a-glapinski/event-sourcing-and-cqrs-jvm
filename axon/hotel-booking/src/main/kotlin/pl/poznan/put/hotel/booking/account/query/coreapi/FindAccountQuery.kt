package pl.poznan.put.hotel.booking.account.query.coreapi

import java.util.*

data class FindAccountQuery(
    val accountId: UUID,
)