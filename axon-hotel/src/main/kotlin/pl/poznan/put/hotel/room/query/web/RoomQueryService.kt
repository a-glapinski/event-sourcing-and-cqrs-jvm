package pl.poznan.put.hotel.room.query.web

import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.room.query.coreapi.FindAllRoomCheckoutSchedulesQuery
import pl.poznan.put.hotel.room.query.coreapi.FindAllRoomCleaningSchedulesQuery
import pl.poznan.put.hotel.room.query.coreapi.FindRoomAvailabilityForAccountQuery
import pl.poznan.put.hotel.room.query.coreapi.FindRoomAvailabilityQuery
import pl.poznan.put.hotel.room.query.dto.RoomAvailabilityResponse
import pl.poznan.put.hotel.room.query.dto.RoomCheckoutScheduleResponse
import pl.poznan.put.hotel.room.query.dto.RoomCleaningScheduleResponse
import pl.poznan.put.hotel.util.axon.query
import reactor.core.publisher.Mono
import java.util.*

@Service
class RoomQueryService(
    private val reactorQueryGateway: ReactorQueryGateway,
) {
    fun getAvailability(roomId: Int): Mono<RoomAvailabilityResponse> =
        reactorQueryGateway.query(FindRoomAvailabilityQuery(roomId))

    fun getAvailability(roomId: Int, accountId: UUID): Mono<RoomAvailabilityResponse> =
        reactorQueryGateway.query(FindRoomAvailabilityForAccountQuery(roomId, accountId))

    fun getAllCleaningSchedules(): Mono<List<RoomCleaningScheduleResponse>> =
        reactorQueryGateway.query(FindAllRoomCleaningSchedulesQuery())

    fun getAllCheckoutSchedules(): Mono<List<RoomCheckoutScheduleResponse>> =
        reactorQueryGateway.query(FindAllRoomCheckoutSchedulesQuery())
}