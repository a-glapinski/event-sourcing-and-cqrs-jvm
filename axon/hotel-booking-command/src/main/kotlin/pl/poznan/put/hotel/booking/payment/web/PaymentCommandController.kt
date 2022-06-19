package pl.poznan.put.hotel.booking.payment.web

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.poznan.put.hotel.booking.payment.dto.PayRequest
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/payments")
class PaymentCommandController(
    private val paymentCommandService: PaymentCommandService,
) {
    @PostMapping
    fun pay(@RequestBody payRequest: PayRequest): Mono<Void> =
        paymentCommandService.pay(payRequest)

    @PostMapping("/{paymentId}/processed")
    fun process(@PathVariable paymentId: UUID): Mono<Void> =
        paymentCommandService.process(paymentId)
}