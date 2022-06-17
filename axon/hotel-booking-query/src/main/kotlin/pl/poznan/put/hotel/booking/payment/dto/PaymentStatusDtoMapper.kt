package pl.poznan.put.hotel.booking.payment.dto

import pl.poznan.put.hotel.booking.payment.model.PaymentStatusEntity

fun PaymentStatusEntity.toDto() = when (this) {
    PaymentStatusEntity.PROCESSING -> PaymentStatusDto.PROCESSING
    PaymentStatusEntity.SUCCEEDED -> PaymentStatusDto.SUCCEEDED
    PaymentStatusEntity.FAILED -> PaymentStatusDto.FAILED
}