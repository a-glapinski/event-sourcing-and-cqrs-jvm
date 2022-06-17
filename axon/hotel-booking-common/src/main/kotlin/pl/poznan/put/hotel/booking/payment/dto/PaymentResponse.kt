package pl.poznan.put.hotel.booking.payment.dto

import java.math.BigDecimal
import java.util.*

data class PaymentResponse(
    val paymentId: UUID,
    val accountId: UUID,
    val totalAmount: BigDecimal,
    val paymentStatus: PaymentStatusDto,
)