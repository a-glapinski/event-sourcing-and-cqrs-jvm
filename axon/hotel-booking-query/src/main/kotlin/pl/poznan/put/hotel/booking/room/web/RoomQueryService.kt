package pl.poznan.put.hotel.booking.room.web

import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.stereotype.Service
import pl.poznan.put.hotel.booking.room.dto.RoomAvailabilityResponse
import pl.poznan.put.hotel.booking.room.dto.RoomCheckoutScheduleResponse
import pl.poznan.put.hotel.booking.room.dto.RoomCleaningScheduleResponse
import pl.poznan.put.hotel.booking.room.query.FindAllRoomCheckoutSchedulesQuery
import pl.poznan.put.hotel.booking.room.query.FindAllRoomCleaningSchedulesQuery
import pl.poznan.put.hotel.booking.room.query.FindRoomAvailabilityForAccountQuery
import pl.poznan.put.hotel.booking.room.query.FindRoomAvailabilityQuery
import pl.poznan.put.util.axon.query
import pl.poznan.put.util.axon.queryMany
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
        reactorQueryGateway.queryMany(FindAllRoomCleaningSchedulesQuery)

    fun getAllCheckoutSchedules(): Mono<List<RoomCheckoutScheduleResponse>> =
        reactorQueryGateway.queryMany(FindAllRoomCheckoutSchedulesQuery)
}