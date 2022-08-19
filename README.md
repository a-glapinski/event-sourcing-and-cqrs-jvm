# Event Sourcing and CQRS on the JVM
The objective of this project was to evaluate one of the available solutions in the JVM ecosystem for implementing an application using the Event Sourcing and CQRS patterns. It consists of two modules - `axon` and `gatling`. The former is the implementation of aforementioned application using Axon Framework, the latter contains Gatling load tests, created to assess the scalability of the system.
The `axon` module contains the implementation of the main components of the application - the command model (`hotel-booking-command` submodule), the query model (`hotel-booking-query` submodule) and the API gateway (`gateway` submodule).

The domain of the system is based on the example used in the following blog post: https://eventmodeling.org/posts/what-is-event-modeling/.

## How to run the application
Firstly, to start all infrastructural components of the system, execute `docker compose up -d` command on the `docker-compose.yml` file located in `axon` directory. You can also use `docker compose --profile 2-instances up -d` or `docker compose --profile 3-instances up -d` to run 2 or 3 instances of read model database respectively.

The project contains preconfigured run configurations, located in the `.run` directory, which should be automatically detected by your IDE. They enable to run 1, 2 or 3 instances of command and query side Spring Boot applications. Alternatively, you can use following Gradle Wrapper commands:

- Gateway
  ```
  ./gradlew :axon:gateway:bootRun
  ```
- Command side
  ```
  ./gradlew :axon:hotel-booking-command:bootRun
  ```
  For second instance:
  ```
  ./gradlew :axon:hotel-booking-command:bootRun --args='--server.port=8082'
  ```
- Query side
  ```
  ./gradlew :axon:hotel-booking-query:bootRun
  ```
  For second instance:
  ```
  ./gradlew :axon:hotel-booking-query:bootRun --args='--server.port=8182 --spring.data.mongodb.port=27018'
  ```

## How to run the load tests
After starting the application, you can run the load tests by executing one of the following Gradle Wrapper commands:
- For command side
  ```
  ./gradlew :gatling:gatlingRun-simulations.HotelBookingCommandSimulation
  ```
- For query side

  Firstly (to populate the event store):
  ```
  ./gradlew :gatling:gatlingRun-simulations.GenerateAggregatesSimulation
  ```
  Then:
  ```
  ./gradlew :gatling:gatlingRun-simulations.HotelBookingQuerySimulation
  ```