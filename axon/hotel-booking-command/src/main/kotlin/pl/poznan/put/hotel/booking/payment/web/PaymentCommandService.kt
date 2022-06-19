package pl.poznan.put.hotel.booking.payment.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.payment.command.PayCommand
import pl.poznan.put.hotel.booking.payment.command.ProcessPaymentCommand
import pl.poznan.put.hotel.booking.payment.dto.PayRequest
import pl.poznan.put.util.axon.sendAny
import reactor.core.publisher.Mono
import java.util.*

@Service
class PaymentCommandService(
    private val reactorCommandGateway: ReactorCommandGateway,
) {
    fun pay(payRequest: PayRequest): Mono<Void> =
        reactorCommandGateway.sendAny(
            PayCommand(
                paymentId = payRequest.paymentId,
                accountId = payRequest.accountId,
                totalAmount = payRequest.totalAmount
            )
        )

    fun process(paymentId: UUID): Mono<Void> =
        reactorCommandGateway.sendAny(
            ProcessPaymentCommand(paymentId = paymentId)
        )
}