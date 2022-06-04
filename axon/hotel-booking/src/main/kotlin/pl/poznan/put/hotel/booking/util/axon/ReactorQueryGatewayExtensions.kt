package pl.poznan.put.hotel.booking.util.axon

import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

inline fun <reified R, Q> ReactorQueryGateway.query(query: Q): Mono<R> =
    query(query, ResponseTypes.instanceOf(R::class.java))

inline fun <reified R, Q> ReactorQueryGateway.queryMany(query: Q): Mono<List<R>> =
    query(query, ResponseTypes.multipleInstancesOf(R::class.java))

inline fun <reified R, Q> ReactorQueryGateway.queryUpdates(query: Q): Flux<R> =
    queryUpdates(query, ResponseTypes.instanceOf(R::class.java))

inline fun <reified R, Q> ReactorQueryGateway.queryManyUpdates(query: Q): Flux<List<R>> =
    queryUpdates(query, ResponseTypes.multipleInstancesOf(R::class.java))