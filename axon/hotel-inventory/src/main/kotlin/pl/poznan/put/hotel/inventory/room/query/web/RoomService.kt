package pl.poznan.put.hotel.inventory.room.query.web

import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.inventory.room.query.coreapi.FindRooms
import pl.poznan.put.hotel.inventory.room.query.dto.RoomOverviewResponse
import pl.poznan.put.util.axon.queryMany
import reactor.core.publisher.Mono

@Service
class RoomService(
    private val reactorQueryGateway: ReactorQueryGateway,
) {
    fun getAll(): Mono<List<RoomOverviewResponse>> =
        reactorQueryGateway.queryMany(FindRooms())
}