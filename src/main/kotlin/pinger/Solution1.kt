package pinger

import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun main() {
  val api = ReportingApi()

  api.report(Ping(42, "foo"))

  val executor = Executors.newFixedThreadPool(6)

  val count = 6

  val jobs = (1..count).map {
    PingJob.newInstance()
  }.map {
    executor.submit(it)
  }

  val maxWaitingTime = 2000

  val timeout = System.currentTimeMillis() + maxWaitingTime

  val results = mutableListOf<Future<Ping>>()

  while (results.size != count && timeout > System.currentTimeMillis()) {
    val toGet = jobs.filter { it !in results }
    println("try on ${toGet.size} jobs")
    toGet.forEach {
      try {
        val ping = it.get(50, TimeUnit.MILLISECONDS)
        println("get")
        results.add(it)
        api.report(ping)
      } catch (e: TimeoutException) {
        // ok
      } catch (e: ExecutionException) {
        api.report(
            object: Ping{
              override val duration = Long.MIN_VALUE
              override val id = e.cause?.message ?: "No info"
            }
        )
      }
    }

    Thread.sleep(100)
  }

  println("Cleanup")

  executor.shutdownNow()

  println("Finished")
}