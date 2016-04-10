package drone.logger;


import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DroneLogger {

    private static Level LOGLEVEL = Level.WARNING;

    private static DroneLogger droneLogger = new DroneLogger();

    private static Logger logger;
    public static DroneLogger getInstance( ) {
        return droneLogger;
    }
    
    private DroneLogger() {
        System.setProperty( "java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%n" );
        System.setProperty( "java.util.logging.ConsoleHandler.formatter",
                "java.util.logging.SimpleFormatter" );

        logger = Logger.getLogger(DroneLogger.class.getName());
        logger.setLevel(LOGLEVEL);
        logger.addHandler(new ConsoleHandler());
    }

    public static void log(Level l, String prefix, String message){
        getInstance().log(l,prefix + ": " + message);
    }
    public static void log(Level l, String message) {
        getInstance().getLogger().log(l,message);
    }

    public Logger getLogger(){
        return logger;
    }
}
