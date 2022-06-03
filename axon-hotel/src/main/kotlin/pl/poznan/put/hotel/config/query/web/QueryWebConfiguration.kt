package pl.poznan.put.hotel.config.query.web

import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import pl.poznan.put.hotel.util.logging.LoggingReactorMessageDispatchInterceptor

@Configuration
class QueryWebConfiguration {
    @Autowired
    fun reactiveQueryGatewayConfiguration(reactorQueryGateway: ReactorQueryGateway) {
        reactorQueryGateway.registerDispatchInterceptor(LoggingReactorMessageDispatchInterceptor())
    }
}