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

    @Test
    void openLogFile_AllEnergyManagementLogsCurrentDate_Success() {
        LogFileManager logManager = new LogFileManager();
        logManager.createLogEntry(LogType.ENERGY_MANAGEMENT, "EM - Energy threshold reached");
        logManager.openLogFile(LogType.ENERGY_MANAGEMENT, null, null);
        
        // Mock the current date
        LocalDate currentDate = LocalDate.now();

        // Spy on the system out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        logManager.openLogFile(LogType.ENERGY_MANAGEMENT, null, currentDate);

        // Assert that the log file name for the current date is printed
        assertEquals("Would open log file: ENERGY_MANAGEMENT-system-" + currentDate.format(DateTimeFormatter.BASIC_ISO_DATE) + ".log\n", outContent.toString());

        // Clean up
        System.setOut(System.out);
    }

    @Test
    void getLogFileName_SpecificEquipmentAndDate_Success() {
        LogFileManager logManager = new LogFileManager();
        String fileName = logManager.getLogFileName(LogType.CHARGING_STATION, "Charger1", LocalDate.now());
        assertEquals("CHARGING_STATION-Charger1-" + logManager.formatDate(LocalDate.now()) + ".log", fileName);
    }


    @Test
    void getLogFileName_SystemLogsSpecificDate_Success() {
        LogFileManager logManager = new LogFileManager();
        String fileName = logManager.getLogFileName(LogType.SYSTEM, null, LocalDate.of(2023, 4, 1));
        assertEquals("SYSTEM-system-20230401.log", fileName);
    }

    @Test
    void openLogFile_NoParameters_Success() {
        LogFileManager logManager = new LogFileManager();
        
        logManager.openLogFile(LogType.CHARGING_STATION, null, null);
        // Spy on the system out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        logManager.openLogFile(LogType.CHARGING_STATION, null, null);

        // Assert that no log file should be opened when no specific parameters are provided
        assertEquals("No log file parameters provided\n", outContent.toString());

        // Clean up
        System.setOut(System.out);
    }

    @Test
    void openLogFile_InvalidEquipment_Success() {
        LogFileManager logManager = new LogFileManager();
        logManager.createLogEntry(LogType.CHARGING_STATION, "Charger2 - Test log entry");
        logManager.createLogEntry(LogType.CHARGING_STATION, "Charger3 - Another log entry");

        LocalDate currentDate = LocalDate.now();

        // Spy on the system out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        logManager.openLogFile(LogType.CHARGING_STATION, "InvalidCharger", currentDate);

        // Assert that the expected log file for the invalid equipment name is not opened
        assertEquals("No log file found for equipment: InvalidCharger on " + currentDate + "\n", outContent.toString());

        // Clean up
        System.setOut(System.out);
    
    }

    @Test
    void getLogFileName_SystemLogSpecificDate_Success() {
        LogFileManager logManager = new LogFileManager();
        String fileName = logManager.getLogFileName(LogType.SYSTEM, null, LocalDate.of(2022, 12, 31));
        assertEquals("SYSTEM-system-20221231.log", fileName);
    }
    @Test
    void openLogFile_EmptyLogFilesMap_Success() {
        LogFileManager logManager = new LogFileManager();
        logManager.openLogFile(LogType.CHARGING_STATION, "Charger1", LocalDate.of(2022, 12, 31));
        
    }
}
