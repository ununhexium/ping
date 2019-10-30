package pinger

import java.time.Duration

interface Ping {
  val duration: Long
  val id: String

  companion object {
    operator fun invoke(duration: Long, id:String) = object: Ping{
      override val duration = duration
      override val id = id
    }
  }
}