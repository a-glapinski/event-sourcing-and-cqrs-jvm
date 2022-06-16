package pl.poznan.put.hotel.booking.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "hotel.booking.command")
@ConstructorBinding
data class CommandProperties(
    val snapshotTriggerThresholdRoom: Int,
    val snapshotTriggerThresholdAccount: Int,
    val snapshotTriggerThresholdPayment: Int,
)