package pinger

import java.util.concurrent.Callable

class PingJob(val scenario: PingOutcome) : Callable<Ping> {
  companion object {
    private var index = 0

    fun newInstance(): PingJob {
      val scenario = PingOutcome.values()[index++ % PingOutcome.values().size]
      println("Create a $scenario")
      return PingJob(scenario)
    }
  }

  var start: Long = -1
  var stop: Long = -1

  override fun call(): Ping {
    start = System.currentTimeMillis()
    when (scenario) {
      PingOutcome.FAILURE -> throw IllegalStateException(this.toString())
      PingOutcome.OK -> Thread.sleep((Math.random() * 100).toLong())
      PingOutcome.TIMEOUT -> Thread.sleep(Long.MAX_VALUE)
    }
    stop = System.currentTimeMillis()

    return object: Ping{
      override val duration = stop - start
      override val id = this.toString()
    }
  }
}