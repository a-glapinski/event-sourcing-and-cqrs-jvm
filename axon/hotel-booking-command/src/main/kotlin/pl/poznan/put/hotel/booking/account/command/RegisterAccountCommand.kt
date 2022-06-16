package pl.poznan.put.hotel.booking.account.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class RegisterAccountCommand(
    @TargetAggregateIdentifier
    val accountId: UUID,
    val username: String,
    val password: String,
)