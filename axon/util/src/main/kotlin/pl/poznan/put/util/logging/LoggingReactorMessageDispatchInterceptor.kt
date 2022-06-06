package pl.poznan.put.util.logging

import mu.KotlinLogging
import org.axonframework.extensions.reactor.messaging.ReactorMessageDispatchInterceptor
import org.axonframework.messaging.Message
import reactor.core.publisher.Mono

class LoggingReactorMessageDispatchInterceptor<M : Message<*>> : ReactorMessageDispatchInterceptor<M> {
    private val logger = KotlinLogging.logger {}

    override fun intercept(monoMessage: Mono<M>): Mono<M> =
        monoMessage.doOnNext { message ->
            logger.info("Dispatched message: [{}]", message.payloadType.simpleName)
        }
}