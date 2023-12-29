import java.util.*;
import java.util.regex.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

enum LogType {
    SYSTEM, CHARGING_STATION, ENERGY_MANAGEMENT
}

class LogEntry {
    private String timestamp;
    private LogType logType;
    private String message;

    public LogEntry(String timestamp, LogType logType, String message) {
        this.timestamp = timestamp;
        this.logType = logType;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public LogType getLogType() {
        return logType;
    }

    public String getMessage() {
        return message;
    }
}

class LogFileManager {
    private Map<LogType, Map<String, List<LogEntry>>> logFiles;

    public LogFileManager() {
        logFiles = new HashMap<>();
        for (LogType logType : LogType.values()) {
            logFiles.put(logType, new HashMap<>());
        }
    }

    private String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public void createLogEntry(LogType logType, String message) {
        String timestamp = getCurrentTimestamp();
        LogEntry logEntry = new LogEntry(timestamp, logType, message);

        Matcher matcher = Pattern.compile("(.*) - ").matcher(message);
        if (matcher.find()) {
            String equipmentName = matcher.group(1);

            List<LogEntry> equipmentLogs = logFiles.get(logType).computeIfAbsent(equipmentName, k -> new ArrayList<>());
            equipmentLogs.add(logEntry);
        } else {
            List<LogEntry> systemLogs = logFiles.get(logType).computeIfAbsent("system", k -> new ArrayList<>());
            systemLogs.add(logEntry);
        }
    }

    public void openLogFile(LogType logType, String equipmentName, LocalDate date) {
        if (equipmentName != null && date != null) {
            String fileName = getLogFileName(logType, equipmentName, date);
            printLogFileName(fileName);
        } else if (equipmentName != null) {
            String fileName = getLogFileName(logType, equipmentName);
            printLogFileName(fileName);
        } else if (date != null) {
            String fileName = getLogFileName(logType, date);
            printLogFileName(fileName);
        } else {
            for (String en : logFiles.get(logType).keySet()) {
                String fileName = getLogFileName(logType, en, LocalDate.now());
                printLogFileName(fileName);
            }
            String fileName = getLogFileName(logType, "system", LocalDate.now());
            printLogFileName(fileName);
        }
    }

    String getLogFileName(LogType logType, String equipmentName, LocalDate date) {
        String datePart = formatDate(date);
        String fileName = logType.toString() + "-" + (equipmentName == null ? "system" : equipmentName) + "-" + datePart + ".log";
        return fileName;
    }

    // Overloaded method with equipmentName
    private String getLogFileName(LogType logType, String equipmentName) {
        return getLogFileName(logType, equipmentName, LocalDate.now());
    }

    // Overloaded method with date
    private String getLogFileName(LogType logType, LocalDate date) {
        return getLogFileName(logType, "system", date);
    }

    // Helper method to format LocalDate
    String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return date.format(formatter);
    }

    private void printLogFileName(String fileName) {
        System.out.println("Would open log file: " + fileName);
    }

	public LocalDate getLogFiles() {
		// TODO Auto-generated method stub
		return null;
	}
}

// An example main class to demonstrate object creation and method usage
public class Main {
    public static void main(String[] args) {
        LogFileManager logManager = new LogFileManager();

        // Example usage of the log manager
        logManager.createLogEntry(LogType.CHARGING_STATION, "Charger1 - Started charging process.");

        // Open log files with different scenarios
        logManager.openLogFile(LogType.CHARGING_STATION, "Charger1", LocalDate.now()); // Specific equipment and date
        logManager.openLogFile(LogType.CHARGING_STATION, "Charger1", null); // Specific equipment, current date
        logManager.openLogFile(LogType.SYSTEM, null, LocalDate.of(2023, 4, 1)); // System logs of a specific date
        logManager.openLogFile(LogType.ENERGY_MANAGEMENT, null, null); // All energy management logs of current date
    }
}