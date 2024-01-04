import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LogFileManagerTest {
	 @Test
	    void openLogFile_SpecificEquipmentAndDate_Success() {
	        LogFileManager logManager = new LogFileManager();
	        logManager.createLogEntry(LogType.CHARGING_STATION, "Charger1 - Started charging process.");
	        logManager.openLogFile(LogType.CHARGING_STATION, "Charger1", LocalDate.now());
	        
	        // Create a log entry for a specific equipment
	        logManager.createLogEntry(LogType.CHARGING_STATION, "Charger1 - Started charging process.");

	        // Mock the current date
	        LocalDate currentDate = LocalDate.now();

	        // Spy on the system out
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));

	        logManager.openLogFile(LogType.CHARGING_STATION, "Charger1", currentDate);

	        // Assert that the log file name for the specific equipment and date is printed
	        assertEquals("Would open log file: CHARGING_STATION-Charger1-" + currentDate.format(DateTimeFormatter.BASIC_ISO_DATE) + ".log\n", outContent.toString());

	        // Clean up
	        System.setOut(System.out);
	    }

	    @Test
	    void openLogFile_SpecificEquipmentCurrentDate_Success() {
	        LogFileManager logManager = new LogFileManager();
	        logManager.createLogEntry(LogType.CHARGING_STATION, "Charger1 - Started charging process.");
	        logManager.openLogFile(LogType.CHARGING_STATION, "Charger1", null);
	        
	        // Mock the current date
	        LocalDate currentDate = LocalDate.now();

	        // Spy on the system out
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));

	        logManager.openLogFile(LogType.CHARGING_STATION, "Charger1", currentDate);

	        // Assert that the log file name for the specific equipment and date is printed
	        assertEquals("Would open log file: CHARGING_STATION-Charger1-" + currentDate.format(DateTimeFormatter.BASIC_ISO_DATE) + ".log\n", outContent.toString());

	        // Clean up
	        System.setOut(System.out);
	    }

	    @Test
	    void openLogFile_SystemLogsSpecificDate_Success() {
	        LogFileManager logManager = new LogFileManager();
	        logManager.createLogEntry(LogType.SYSTEM, "Main System - Initialization complete");
	        logManager.openLogFile(LogType.SYSTEM, null, LocalDate.of(2023, 4, 1));
	        
	        // Mock the specific date
	        LocalDate specificDate = LocalDate.of(2023, 4, 1);

	        // Spy on the system out
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));

	        logManager.openLogFile(LogType.SYSTEM, null, specificDate);

	        // Assert that the log file name for the specific date is printed
	        assertEquals("Would open log file: SYSTEM-system-" + specificDate.format(DateTimeFormatter.BASIC_ISO_DATE) + ".log\n", outContent.toString());

	        // Clean up
	        System.setOut(System.out);
	    }

}
