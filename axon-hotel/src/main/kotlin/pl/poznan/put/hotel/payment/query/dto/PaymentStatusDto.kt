package pl.poznan.put.hotel.payment.query.dto

import pl.poznan.put.hotel.payment.query.model.PaymentStatusEntity

enum class PaymentStatusDto {
    PROCESSING,
    SUCCEEDED,
    FAILED;

    companion object {
        operator fun invoke(paymentStatusEntity: PaymentStatusEntity) =
            when (paymentStatusEntity) {
                PaymentStatusEntity.PROCESSING -> PROCESSING
                PaymentStatusEntity.SUCCEEDED -> SUCCEEDED
                PaymentStatusEntity.FAILED -> FAILED
            }
    }
}
