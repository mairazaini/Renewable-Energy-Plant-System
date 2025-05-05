import java.io.{BufferedWriter, FileWriter, FileNotFoundException}
import scala.io.Source
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DataStorage {
  private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

  def saveToCSV(readings: List[EnergyReading], filename: String): Unit = {
    val bw = new BufferedWriter(new FileWriter(filename))
    bw.write("timestamp,source,output,efficiency,status\n")
    readings.foreach { reading =>
      val line = s"${reading.timestamp.format(formatter)},${reading.source},${reading.output},${reading.efficiency},${reading.status}"
      bw.write(line + "\n")
    }
    bw.close()
  }

  def loadFromCSV(filename: String): List[EnergyReading] = {
    try {
      val lines = Source.fromFile(filename).getLines().drop(1).toList
      lines.map { line =>
        val parts = line.split(",")
        EnergyReading(
          LocalDateTime.parse(parts(0), formatter),
          parseSource(parts(1)),
          parts(2).toDouble,
          parts(3).toDouble,
          parseStatus(parts(4))
        )
      }
    } catch {
      case _: FileNotFoundException => println(s"File not found: $filename"); List.empty
      case ex: Exception => println(s"Error loading CSV: ${ex.getMessage}"); List.empty
    }
  }

  private def parseSource(s: String): EnergySource = s match {
    case "Solar" => Solar
    case "Wind"  => Wind
    case "Hydro" => Hydro
    case _       => throw new IllegalArgumentException(s"Unknown source: $s")
  }

  private def parseStatus(s: String): EquipmentStatus = s match {
    case "Normal"   => Normal
    case "Degraded" => Degraded
    case "Failed"   => Failed
    case _          => throw new IllegalArgumentException(s"Unknown status: $s")
  }
}
