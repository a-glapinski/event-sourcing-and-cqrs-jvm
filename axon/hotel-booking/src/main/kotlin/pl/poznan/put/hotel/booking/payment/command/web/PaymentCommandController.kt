package pl.poznan.put.hotel.booking.payment.command.web

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.booking.payment.command.dto.PayRequest
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/payments")
class PaymentCommandController(
    private val paymentCommandService: PaymentCommandService,
) {
    @PostMapping
    fun pay(@RequestBody payRequest: PayRequest): Mono<UUID> =
        paymentCommandService.pay(payRequest)

    @PostMapping("/payments/{paymentId}/processed")
    fun process(@PathVariable paymentId: UUID): Mono<UUID> =
        paymentCommandService.process(paymentId)
}