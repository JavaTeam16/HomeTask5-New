import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class LogFileManagerTest {

    //FarazChahybakhsh Part
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
