package pl.poznan.put.hotel.booking.account.query

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.extensions.kotlin.emit
import org.axonframework.queryhandling.QueryHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.booking.account.event.coreapi.AccountRegisteredEvent
import pl.poznan.put.hotel.booking.account.query.coreapi.FindAccountQuery
import pl.poznan.put.hotel.booking.account.query.coreapi.FindAccountsQuery
import pl.poznan.put.hotel.booking.account.query.dto.AccountResponse
import pl.poznan.put.hotel.booking.account.query.model.AccountEntity
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
        AccountResponse(accountEntityRepository.findByIdOrThrow(query.accountId))

    @QueryHandler
    fun handle(query: FindAccountsQuery): List<AccountResponse> =
        accountEntityRepository.findAll()
            .map { AccountResponse(it) }
}