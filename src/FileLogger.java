import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileLogger {

    public static void main(String[] args) {

        createLogFiles("EnergyStation1", "EnergySource1");
        createLogFiles("EnergyStation1", "EnergySource2");
        createLogFiles("EnergyStation2", "EnergySource3");


    }

    public static void createLogFiles(String energyStation, String equipment) {
        try {
            String logFileName = createLogFileName(energyStation, equipment);
            
            FileHandler fileHandler = new FileHandler(logFileName);
            Logger logger = Logger.getLogger("log");
            logger.info("This is an information message about" + energyStation + "and" + equipment);
            logger.warning("This is a warning message.");
            logger.severe("This is a severe error message.");
            logger.addHandler(fileHandler);


            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            List<String> lines = Files.readAllLines(Paths.get(logFileName));

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createLogFileName(String energyStation, String equipment) {
        @SuppressWarnings("unused")
		Logger logger = Logger.getLogger("log");

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String timestamp = dateFormat.format(new Date());

        return energyStation + "-" + equipment + "-" + timestamp + ".log";

    }

}
