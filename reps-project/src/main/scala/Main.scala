import java.time.LocalDate
import scala.io.StdIn
import scala.util.{Try, Success, Failure}

object REPSSystem extends App {
  println("Welcome to REPS - Renewable Energy Plant System\n")

  var currentReadings: List[EnergyReading] = List.empty
  var currentSource: EnergySource = Solar
  var currentDate: LocalDate = LocalDate.now

  // Main menu loop
  def showMenu(): Unit = {
    println("\n=== Welcome to REPS Menu ===")
    println("1. Retrieve energy data")
    println("2. View current data stats")
    println("3. Check for alerts")
    println("4. Change energy source (Current: " + currentSource + ")")
    println("5. Change date (Current: " + currentDate + ")")
    println("6. Save data to CSV")
    println("7. Load data from CSV")
    println("8. Exit")
    print("\nSelect an option:\n")

    Try(StdIn.readInt()) match {
      case Success(choice) => handleChoice(choice)
      case Failure(_) => println("Invalid input! Please enter a number.")
    }
  }

  // Handle menu choices
  def handleChoice(choice: Int): Unit = choice match {
    case 1 => fetchData()
    case 2 => showStats()
    case 3 => showAlerts()
    case 4 => changeSource()
    case 5 => changeDate()
    case 6 => saveToCSV()
    case 7 => loadFromCSV()
    case 8 => println("Exiting...")
    case _ => println("Invalid option!")
  }

  // Fetch data for current source/date
  def fetchData(): Unit = {
    DataCollector.fetchReadings(currentSource, currentDate) match {
      case Right(data) =>
        currentReadings = data
        println(s"\nFetched ${data.length} readings for $currentSource on $currentDate.")
      case Left(err) =>
        println(s"\nError: $err")
    }
    showMenu()
  }

  // Show statistics for current data
  def showStats(): Unit = {
    if (currentReadings.isEmpty) {
      println("\nNo data available. Fetch data first.")
    } else {
      val stats = DataAnalyzer.analyze(currentReadings, Daily)
      println("\n=== Energy Statistics ===")
      println(s"Mean: ${stats.mean} kWh")
      println(s"Median: ${stats.median} kWh")
      println(s"Mode: ${stats.mode} kWh")
      println(s"Range: ${stats.range} kWh")
      println(s"Midrange: ${stats.midrange} kWh")
    }
    showMenu()
  }

  // Show alerts for current data
  def showAlerts(): Unit = {
    if (currentReadings.isEmpty) {
      println("\nNo data available. Fetch data first.")
    } else {
      val alerts = Monitor.checkStatus(currentReadings)
      if (alerts.isEmpty) {
        println("\nNo alerts detected. All systems normal.")
      } else {
        println("\n=== Alerts ===")
        alerts.foreach(a => println(s"[${a.severity}] ${a.timestamp}: ${a.message}"))
      }
    }
    showMenu()
  }

  // Change energy source
  def changeSource(): Unit = {
    println("\nSelect energy source:")
    println("1. Solar")
    println("2. Wind")
    println("3. Hydro")
    print("Enter choice:\n")

    Try(StdIn.readInt()) match {
      case Success(1) => currentSource = Solar
      case Success(2) => currentSource = Wind
      case Success(3) => currentSource = Hydro
      case _ => println("Invalid choice! Keeping current source.")
    }
    println(s"Source set to: $currentSource")
    showMenu()
  }

  // Change date
  def changeDate(): Unit = {
    print("\nEnter date (DD/MM/YYYY):\n")
    val input = StdIn.readLine()
    Try(LocalDate.parse(input, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))) match {
      case Success(date) =>
        currentDate = date
        println(s"Date set to: $currentDate")
      case Failure(_) =>
        println("Invalid date format! Please use DD/MM/YYYY (e.g., 05/05/2025).")
    }
    showMenu()
  }

  // Save data to CSV
  def saveToCSV(): Unit = {
    if (currentReadings.isEmpty) {
      println("\nNo data to save. Fetch data first.")
    } else {
      print("\nEnter filename:\n")
      val filename = StdIn.readLine()
      DataStorage.saveToCSV(currentReadings, filename)
      println(s"Data saved to $filename")
    }
    showMenu()
  }

  // Load data from CSV
  def loadFromCSV(): Unit = {
    print("\nEnter filename to load:\n")
    val filename = StdIn.readLine()
    currentReadings = DataStorage.loadFromCSV(filename)
    if (currentReadings.nonEmpty) {
      println(s"Loaded ${currentReadings.length} records from $filename")
    }
    showMenu()
  }

  // Start the menu
  showMenu()
}