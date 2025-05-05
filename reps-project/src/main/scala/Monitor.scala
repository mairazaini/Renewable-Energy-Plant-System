object Monitor {
  def checkStatus(readings: List[EnergyReading]): List[Alert] = {
    readings.flatMap { r =>
      val alerts = scala.collection.mutable.ListBuffer[Alert]()

      if (r.efficiency < 0.7)
        alerts += Alert(r.timestamp, r.source, "Low efficiency", 2)
      if (r.status == Failed)
        alerts += Alert(r.timestamp, r.source, "Equipment failure", 3)

      alerts.toList
    }
  }
}
