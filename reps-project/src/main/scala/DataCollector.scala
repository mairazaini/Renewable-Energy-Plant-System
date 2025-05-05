import java.time.{LocalDate, LocalDateTime}
import scala.util.{Try, Success, Failure}

object DataCollector {
  def fetchReadings(source: EnergySource, date: LocalDate): Either[String, List[EnergyReading]] = {
    if (date.isAfter(LocalDate.now)) {
      Left("Date cannot be in the future")
    } else {
      // Simulated data
      Right(List(
        EnergyReading(LocalDateTime.now.minusHours(2), source, 200.0, 0.78, Normal),
        EnergyReading(LocalDateTime.now.minusHours(1), source, 180.0, 0.72, Degraded),
        EnergyReading(LocalDateTime.now, source, 160.0, 0.65, Failed)
      ))
    }
  }
}
