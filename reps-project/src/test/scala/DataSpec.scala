import org.scalatest.funsuite.AnyFunSuite
import java.time.LocalDateTime

class DataSpec extends AnyFunSuite {
  test("Analyze statistics") {
    val sample = List(
      EnergyReading(LocalDateTime.now, Solar, 100, 0.8, Normal),
      EnergyReading(LocalDateTime.now, Solar, 200, 0.9, Normal),
      EnergyReading(LocalDateTime.now, Solar, 300, 0.85, Normal)
    )
    val stats = DataAnalyzer.analyze(sample, Daily)
    assert(stats.mean == 200)
    assert(stats.range == 200)
  }

  test("Check monitor alerts") {
    val reading = EnergyReading(LocalDateTime.now, Wind, 120, 0.65, Failed)
    val alerts = Monitor.checkStatus(List(reading))
    assert(alerts.length == 2)
  }
}
