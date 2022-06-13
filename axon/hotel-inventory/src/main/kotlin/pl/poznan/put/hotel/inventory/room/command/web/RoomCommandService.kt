package pl.poznan.put.hotel.inventory.room.command.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.inventory.room.command.coreapi.AddRoomToInventoryCommand
import pl.poznan.put.hotel.inventory.room.command.coreapi.CreateRoomCommand
import pl.poznan.put.hotel.inventory.room.query.dto.RoomRequest
import reactor.core.publisher.Mono
import java.util.*

@Service
class RoomCommandService(
    private val reactorCommandGateway: ReactorCommandGateway,
) {
    fun create(roomRequest: RoomRequest): Mono<UUID> =
        reactorCommandGateway.send(
            CreateRoomCommand(
                roomId = UUID.randomUUID(),
                roomNumber = roomRequest.roomNumber,
                roomDescription = roomRequest.description
            )
        )

    fun addToInventory(roomId: UUID): Mono<UUID> =
        reactorCommandGateway.send(AddRoomToInventoryCommand(roomId = roomId))
}