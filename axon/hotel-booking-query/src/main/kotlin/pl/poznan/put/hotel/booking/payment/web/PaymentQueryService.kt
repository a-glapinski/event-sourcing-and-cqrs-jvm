package pl.poznan.put.hotel.booking.payment.web

import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.payment.dto.PaymentResponse
import pl.poznan.put.hotel.booking.payment.query.FindPaymentQuery
import pl.poznan.put.hotel.booking.payment.query.FindPaymentsForAccountQuery
import pl.poznan.put.hotel.booking.payment.query.FindPaymentsQuery
import pl.poznan.put.util.axon.query
import pl.poznan.put.util.axon.queryMany
import reactor.core.publisher.Mono
import java.util.*

@Service
class PaymentQueryService(
    private val reactorQueryGateway: ReactorQueryGateway,
) {
    fun get(paymentId: UUID): Mono<PaymentResponse> =
        reactorQueryGateway.query(FindPaymentQuery(paymentId))

    fun getAll(accountId: UUID): Mono<List<PaymentResponse>> =
        reactorQueryGateway.queryMany(FindPaymentsForAccountQuery(accountId))

    fun getAll(): Mono<List<PaymentResponse>> =
        reactorQueryGateway.queryMany(FindPaymentsQuery())
}