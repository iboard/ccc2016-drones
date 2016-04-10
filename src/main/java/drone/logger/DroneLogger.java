package drone.logger;


import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DroneLogger {

    private static Level LOGLEVEL = Level.INFO;

    private static DroneLogger droneLogger = new DroneLogger();

    private static Logger logger;
    private static ConsoleHandler consoleHandler;
    public static DroneLogger getInstance( ) {
        return droneLogger;
    }
    
    private DroneLogger() {
        logger = Logger.getLogger(DroneLogger.class.getName());
        consoleHandler =  new ConsoleHandler();
        System.setProperty( "java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %n\n" );
        System.setProperty( "java.util.logging.ConsoleHandler.formatter",
                "java.util.logging.SimpleFormatter" );
        logger.setLevel(LOGLEVEL);
        logger.addHandler(consoleHandler);
    }

    public static void log(Level l, String message) {
        getInstance().getLogger().log(l,message);
    }

    public Logger getLogger(){
        return logger;
    }
}
