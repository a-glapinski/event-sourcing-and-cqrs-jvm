package util

import io.gatling.core.feeder.Feeder

import java.util.UUID
import scala.util.Random

object Feeders {
  val uuidFeeder: Feeder[String] = Iterator.continually(Map("uuid" -> UUID.randomUUID.toString))

  val positiveIntFeeder: Feeder[Int] = Iterator.continually(Map("positiveInt" -> Random.between(1, Int.MaxValue)))
}