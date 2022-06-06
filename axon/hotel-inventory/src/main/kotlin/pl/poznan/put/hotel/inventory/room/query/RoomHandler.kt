package pl.poznan.put.hotel.inventory.room.query

import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component
import pl.poznan.put.hotel.inventory.room.event.coreapi.RoomAddedToBookingSystemEvent
import pl.poznan.put.hotel.inventory.room.event.coreapi.RoomAddedToInventoryEvent
import pl.poznan.put.hotel.inventory.room.event.coreapi.RoomCreatedEvent
import pl.poznan.put.hotel.inventory.room.query.coreapi.FindRooms
import pl.poznan.put.hotel.inventory.room.query.dto.RoomOverviewResponse
import pl.poznan.put.hotel.inventory.room.query.model.RoomEntity
import pl.poznan.put.util.repository.findByIdOrThrow

@Component
@ProcessingGroup("room")
class RoomHandler(
    private val roomEntityRepository: RoomEntityRepository,
) {
    @EventHandler
    fun on(event: RoomCreatedEvent) {
        RoomEntity(
            roomId = event.roomId,
            roomNumber = event.roomNumber,
            description = event.roomDescription,
            addedToInventory = false,
            addedToBooking = false
        )
            .let { roomEntityRepository.save(it) }
    }

    @EventHandler
    fun on(event: RoomAddedToInventoryEvent) {
        roomEntityRepository.findByIdOrThrow(event.roomId)
            .apply { addedToInventory = true }
            .let { roomEntityRepository.save(it) }
    }

    @EventHandler
    fun on(event: RoomAddedToBookingSystemEvent) {
        roomEntityRepository.findByIdOrThrow(event.roomId)
            .apply { addedToBooking = true }
            .let { roomEntityRepository.save(it) }
    }

    @QueryHandler
    fun on(query: FindRooms): List<RoomOverviewResponse> =
        roomEntityRepository
            .findAll()
            .map { RoomOverviewResponse(it) }
}