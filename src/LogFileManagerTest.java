import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LogFileManagerTest {

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

}
