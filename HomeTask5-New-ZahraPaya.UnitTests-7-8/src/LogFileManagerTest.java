import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LogFileManagerTest {

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