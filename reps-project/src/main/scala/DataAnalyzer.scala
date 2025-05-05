object DataAnalyzer {
  def analyze(readings: List[EnergyReading], period: TimePeriod): EnergyStats = {
    val outputs = readings.map(_.output)
    val mean = outputs.sum / outputs.length
    val sorted = outputs.sorted
    val median = if (sorted.size % 2 == 1) sorted(sorted.size / 2)
                 else (sorted(sorted.size / 2) + sorted(sorted.size / 2 - 1)) / 2
    val mode = outputs.groupBy(identity).mapValues(_.size).maxBy(_._2)._1
    val range = outputs.max - outputs.min
    val midrange = (outputs.max + outputs.min) / 2
    EnergyStats(mean, median, mode, range, midrange)
  }

  def filterBy(predicate: EnergyReading => Boolean)(readings: List[EnergyReading]) =
    readings.filter(predicate)
}
