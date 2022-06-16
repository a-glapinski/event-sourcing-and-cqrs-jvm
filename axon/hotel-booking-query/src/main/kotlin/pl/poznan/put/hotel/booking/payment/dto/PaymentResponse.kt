package pl.poznan.put.hotel.booking.payment.dto

import pl.poznan.put.hotel.booking.payment.model.PaymentEntity
import java.math.BigDecimal
import java.util.*

data class PaymentResponse(
    val paymentId: UUID,
    val accountId: UUID,
    val totalAmount: BigDecimal,
    val paymentStatus: PaymentStatusDto,
) {
    constructor(paymentEntity: PaymentEntity) : this(
        paymentId = paymentEntity.paymentId,
        accountId = paymentEntity.accountId,
        totalAmount = paymentEntity.totalAmount,
        paymentStatus = PaymentStatusDto(paymentEntity.paymentStatus)
    )
}