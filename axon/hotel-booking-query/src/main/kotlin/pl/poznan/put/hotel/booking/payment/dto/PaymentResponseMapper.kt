package pl.poznan.put.hotel.booking.payment.dto

import pl.poznan.put.hotel.booking.payment.model.PaymentEntity

fun PaymentEntity.toResponse() = PaymentResponse(
    paymentId = paymentId,
    accountId = accountId,
    totalAmount = totalAmount,
    paymentStatus = paymentStatus.toDto()
)