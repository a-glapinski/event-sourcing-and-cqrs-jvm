package pl.poznan.put.hotel.booking.payment.command.dto

import java.math.BigDecimal
import java.util.*

data class PayRequest(
    val accountId: UUID,
    val totalAmount: BigDecimal,
)
