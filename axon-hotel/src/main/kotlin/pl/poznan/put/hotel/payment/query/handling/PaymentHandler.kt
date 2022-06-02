package pl.poznan.put.hotel.payment.query.handling

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.payment.event.PaymentRequestedEvent
import pl.poznan.put.hotel.payment.event.PaymentSucceededEvent
import pl.poznan.put.hotel.payment.query.FindPayment
import pl.poznan.put.hotel.payment.query.FindPayments
import pl.poznan.put.hotel.payment.query.FindPaymentsForAccount
import pl.poznan.put.hotel.payment.query.dto.PaymentResponse
import pl.poznan.put.hotel.payment.query.model.PaymentEntity
import pl.poznan.put.hotel.payment.query.model.PaymentStatusEntity
import pl.poznan.put.hotel.util.findByIdOrThrow

@Component
@ProcessingGroup("payment")
class PaymentHandler(
    private val queryUpdateEmitter: QueryUpdateEmitter,
    private val paymentEntityRepository: PaymentEntityRepository,
) {
    @EventHandler
    fun on(event: PaymentRequestedEvent) {
        PaymentEntity(
            paymentId = event.paymentId,
            accountId = event.accountId,
            totalAmount = event.totalAmount,
            paymentStatus = PaymentStatusEntity.PROCESSING
        )
            .let { paymentEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit<FindPayment, PaymentResponse>(
                    PaymentResponse(it)
                ) { query ->
                    query.paymentId == event.paymentId
                }
            }
            .also {
                queryUpdateEmitter.emit<FindPaymentsForAccount, PaymentResponse>(
                    PaymentResponse(it)
                ) { query ->
                    query.accountId == event.accountId
                }
            }
            .also {
                queryUpdateEmitter.emit<FindPayments, PaymentResponse>(PaymentResponse(it)) { true }
            }
    }

    @EventHandler
    fun on(event: PaymentSucceededEvent) {
        paymentEntityRepository.findByIdOrThrow(event.paymentId)
            .apply {
                paymentStatus = PaymentStatusEntity.SUCCEEDED
            }
            .let { paymentEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit<FindPayment, PaymentResponse>(
                    PaymentResponse(it)
                ) { query ->
                    query.paymentId == event.paymentId
                }
            }
            .also {
                queryUpdateEmitter.emit<FindPaymentsForAccount, PaymentResponse>(
                    PaymentResponse(it)
                ) { query ->
                    query.accountId == it.accountId
                }
            }
            .also {
                queryUpdateEmitter.emit<FindPayments, PaymentResponse>(PaymentResponse(it)) { true }
            }
    }

    @QueryHandler
    fun handle(query: FindPayment): PaymentResponse =
        PaymentResponse(paymentEntityRepository.findByIdOrThrow(query.paymentId))

    @QueryHandler
    fun handle(query: FindPayments): List<PaymentResponse> =
        paymentEntityRepository.findAll()
            .map { PaymentResponse(it) }

    @QueryHandler
    fun handle(query: FindPaymentsForAccount): List<PaymentResponse> =
        paymentEntityRepository.findByAccountId(query.accountId)
            .map { PaymentResponse(it) }
}