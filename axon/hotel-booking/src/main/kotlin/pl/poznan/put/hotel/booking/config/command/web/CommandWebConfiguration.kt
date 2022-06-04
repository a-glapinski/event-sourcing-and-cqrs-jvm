package pl.poznan.put.hotel.booking.config.command.web

import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import pl.poznan.put.hotel.booking.util.logging.LoggingReactorMessageDispatchInterceptor

@Configuration
class CommandWebConfiguration {
    @Autowired
    fun reactiveCommandGatewayConfiguration(reactorCommandGateway: ReactorCommandGateway) {
        reactorCommandGateway.registerDispatchInterceptor(LoggingReactorMessageDispatchInterceptor())
    }
}