package pl.poznan.put.hotel.booking.account.command.model

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.extensions.kotlin.applyEvent
import org.axonframework.extensions.kotlin.createNew
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import pl.poznan.put.hotel.booking.account.command.coreapi.RegisterAccountCommand
import pl.poznan.put.hotel.booking.account.event.coreapi.AccountRegisteredEvent
import pl.poznan.put.hotel.booking.payment.command.coreapi.PayCommand
import pl.poznan.put.hotel.booking.payment.command.model.Payment
import java.util.*

@Aggregate(snapshotTriggerDefinition = "accountSnapshotTriggerDefinition", cache = "cache")
class Account {
    @AggregateIdentifier
    lateinit var id: UUID

    private constructor()

    @CommandHandler
    constructor(command: RegisterAccountCommand) {
        applyEvent(AccountRegisteredEvent(command.accountId, command.userName, command.password))
    }

    @CommandHandler
    fun on(command: PayCommand) {
        createNew { Payment(command) }
    }

    @EventSourcingHandler
    fun on(event: AccountRegisteredEvent) {
        id = event.accountId
    }
}