package pl.poznan.put.hotel.command.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "hotel.command")
@ConstructorBinding
data class CommandProperties(
    val snapshotTriggerThresholdRoom: Int,
    val snapshotTriggerThresholdAccount: Int,
    val snapshotTriggerThresholdPayment: Int,
)