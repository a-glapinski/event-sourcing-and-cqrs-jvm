package pl.poznan.put.hotel.booking.account

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.booking.account.dto.AccountResponse
import pl.poznan.put.hotel.booking.account.event.AccountRegisteredEvent
import pl.poznan.put.hotel.booking.account.model.AccountEntity
import pl.poznan.put.hotel.booking.account.query.FindAccountQuery
import pl.poznan.put.hotel.booking.account.query.FindAccountsQuery
import pl.poznan.put.util.repository.findByIdOrThrow

@Component
@ProcessingGroup("account")
class AccountHandler(
    private val queryUpdateEmitter: QueryUpdateEmitter,
    private val accountEntityRepository: AccountEntityRepository,
) {
    @EventHandler
    fun on(event: AccountRegisteredEvent) {
        AccountEntity(
            accountId = event.accountId,
            username = event.username,
            password = event.password
        )
            .let { accountEntityRepository.save(it) }
            .also {
                queryUpdateEmitter.emit(AccountResponse(it)) { query: FindAccountQuery ->
                    query.accountId == event.accountId
                }
            }
            .also {
                queryUpdateEmitter.emit(AccountResponse(it)) { _: FindAccountsQuery -> true }
            }
    }

    @QueryHandler
    fun handle(query: FindAccountQuery): AccountResponse =
        accountEntityRepository
            .findByIdOrThrow(query.accountId)
            .let { AccountResponse(it) }

    @QueryHandler
    fun handle(query: FindAccountsQuery): List<AccountResponse> =
        accountEntityRepository.findAll()
            .map { AccountResponse(it) }
}