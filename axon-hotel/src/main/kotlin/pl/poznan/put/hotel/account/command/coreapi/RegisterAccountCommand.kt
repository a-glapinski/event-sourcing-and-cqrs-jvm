package pl.poznan.put.hotel.account.command.coreapi

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class RegisterAccountCommand(
    @TargetAggregateIdentifier
    val accountId: UUID,
    val userName: String,
    val password: String,
)