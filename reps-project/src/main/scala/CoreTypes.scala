import java.time.LocalDateTime

sealed trait EnergySource
case object Solar extends EnergySource
case object Wind extends EnergySource
case object Hydro extends EnergySource

sealed trait EquipmentStatus
case object Normal extends EquipmentStatus
case object Degraded extends EquipmentStatus
case object Failed extends EquipmentStatus

case class EnergyReading(
  timestamp: LocalDateTime,
  source: EnergySource,
  output: Double,
  efficiency: Double,
  status: EquipmentStatus
)

case class Alert(
  timestamp: LocalDateTime,
  source: EnergySource,
  message: String,
  severity: Int
)

sealed trait TimePeriod
case object Hourly extends TimePeriod
case object Daily extends TimePeriod
case object Weekly extends TimePeriod
case object Monthly extends TimePeriod

case class EnergyStats(
  mean: Double,
  median: Double,
  mode: Double,
  range: Double,
  midrange: Double
)
