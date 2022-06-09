package pl.poznan.put.util.axon

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import reactor.core.publisher.Mono

fun ReactorCommandGateway.sendAny(command: Any): Mono<Any> =
    send(command)