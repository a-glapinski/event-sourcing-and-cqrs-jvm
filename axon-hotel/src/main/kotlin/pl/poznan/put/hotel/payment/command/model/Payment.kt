package pl.poznan.put.hotel.payment.command.model

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.extensions.kotlin.applyEvent
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import pl.poznan.put.hotel.payment.command.coreapi.PayCommand
import pl.poznan.put.hotel.payment.command.coreapi.ProcessPaymentCommand
import pl.poznan.put.hotel.payment.event.coreapi.PaymentRequestedEvent
import pl.poznan.put.hotel.payment.event.coreapi.PaymentSucceededEvent
import java.util.*

@Aggregate(snapshotTriggerDefinition = "paymentSnapshotTriggerDefinition", cache = "cache")
class Payment {
    @AggregateIdentifier
    private lateinit var id: UUID

    private lateinit var status: PaymentStatus

    private constructor()

    constructor(command: PayCommand) {
        applyEvent(PaymentRequestedEvent(command.paymentId, command.accountId, command.totalAmount))
    }

    @CommandHandler
    fun handle(command: ProcessPaymentCommand) {
        require(status == PaymentStatus.PROCESSING) { "Payment is not in PROCESSING state" }
        applyEvent(PaymentSucceededEvent(command.paymentId))
    }

    @EventSourcingHandler
    fun on(event: PaymentRequestedEvent) {
        id = event.paymentId
        status = PaymentStatus.PROCESSING
    }

    @EventSourcingHandler
    fun on(event: PaymentSucceededEvent) {
        status = PaymentStatus.SUCCEEDED
    }
}