package pl.poznan.put.hotel.payment.query.dto

import java.math.BigDecimal
import java.util.*

data class PaymentResponse(
    val paymentId: String,
    val accountId: UUID,
    val totalAmount: BigDecimal,
    val paymentStatus: PaymentStatusDto,
)