import java.time.LocalDate;

public class LogManager {
    public static void main(String[] args) {
        LogFileManager logFileManager = new LogFileManager();

        // Creating log entries
        logFileManager.createLogEntry(LogType.SYSTEM, "Main System - Initialization complete");
        logFileManager.createLogEntry(LogType.CHARGING_STATION, "CS001 - New charging session started");
        logFileManager.createLogEntry(LogType.ENERGY_MANAGEMENT, "EM - Energy threshold reached");

        // Parsing string to LocalDate
        LocalDate date = LocalDate.parse("2023-04-01");

        // Simulating opening of a file
        logFileManager.openLogFile(LogType.SYSTEM, null, date);
    }
}