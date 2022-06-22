rootProject.name = "event-sourcing-and-cqrs-jvm"

include(
    "axon",
    "axon:util",
    "axon:gateway",
    "axon:hotel-booking-common",
    "axon:hotel-booking-command",
    "axon:hotel-booking-query",
    "gatling",
)