package pl.poznan.put.hotel.booking.payment.query

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.booking.payment.event.coreapi.PaymentRequestedEvent
import pl.poznan.put.hotel.booking.payment.event.coreapi.PaymentSucceededEvent
import pl.poznan.put.hotel.booking.payment.query.coreapi.FindPaymentQuery
import pl.poznan.put.hotel.booking.payment.query.coreapi.FindPaymentsForAccountQuery
import pl.poznan.put.hotel.booking.payment.query.coreapi.FindPaymentsQuery
import pl.poznan.put.hotel.booking.payment.query.dto.PaymentResponse
import pl.poznan.put.hotel.booking.payment.query.model.PaymentEntity
import pl.poznan.put.hotel.booking.payment.query.model.PaymentStatusEntity
import pl.poznan.put.hotel.booking.util.repository.findByIdOrThrow

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
                queryUpdateEmitter.emit(PaymentResponse(it)) { query: FindPaymentQuery ->
                    query.paymentId == event.paymentId
                }
            }
            .also {
                queryUpdateEmitter.emit(PaymentResponse(it)) { query: FindPaymentsForAccountQuery ->
                    query.accountId == event.accountId
                }
            }
            .also {
                queryUpdateEmitter.emit(PaymentResponse(it)) { _: FindPaymentsQuery -> true }
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
                queryUpdateEmitter.emit(PaymentResponse(it)) { query: FindPaymentQuery ->
                    query.paymentId == event.paymentId
                }
            }
            .also {
                queryUpdateEmitter.emit(PaymentResponse(it)) { query: FindPaymentsForAccountQuery ->
                    query.accountId == it.accountId
                }
            }
            .also {
                queryUpdateEmitter.emit(PaymentResponse(it)) { _: FindPaymentsQuery -> true }
            }
    }

    @QueryHandler
    fun handle(query: FindPaymentQuery): PaymentResponse =
        PaymentResponse(paymentEntityRepository.findByIdOrThrow(query.paymentId))

    @QueryHandler
    fun handle(query: FindPaymentsQuery): List<PaymentResponse> =
        paymentEntityRepository.findAll()
            .map { PaymentResponse(it) }

    @QueryHandler
    fun handle(query: FindPaymentsForAccountQuery): List<PaymentResponse> =
        paymentEntityRepository.findByAccountId(query.accountId)
            .map { PaymentResponse(it) }
}