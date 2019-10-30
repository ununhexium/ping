package pinger

import java.sql.DriverManager.println

class ReportingApi {
  fun report(ping: Ping) {
    println("report")
    println("Id ${ping.id} took ${ping.duration}")
  }
}
