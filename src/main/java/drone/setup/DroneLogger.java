package drone.setup;


import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <blockquote>
 *     Really Java?! Why we have to implement this in every simple stupid program?
 * </blockquote>
 *
 * <h1>The Logger</h1>
 *
 * <p>
 *     ...Is implemented using the singleton-pattern. So, you have not to care about initialization.
 * </p>
 *
 * <strong>Usage:</strong>
 * <pre>
 *   DroneLogger.log( Level, Message)
 *   DroneLogger.log( Level, Prefix, Message )
 * </pre>
 *
 * <strong>Config</strong>
 * <p>Config the log-level in <code>drone.setup.DroneConfig</code></p>
 */
@SuppressWarnings("SameParameterValue")
public class DroneLogger {

    // private members
    private static final DroneLogger droneLogger = new DroneLogger();
    private static Logger logger;

    // Singleton getter
    @SuppressWarnings("WeakerAccess")
    public static DroneLogger getInstance( ) {
        return droneLogger;
    }

    /**
     * Constructed by singleton pattern
     * <strong>– don't call new DroneLogger yourself</strong>
     */
    private DroneLogger() {
        System.setProperty( "java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$-6s %2$s %5$s%n" );
        System.setProperty( "java.util.logging.ConsoleHandler.formatter",
                "java.util.logging.SimpleFormatter" );

        logger = Logger.getLogger(DroneLogger.class.getName());
        logger.setLevel(DroneConfig.logLevel());
        logger.addHandler(new ConsoleHandler());
    }

    // Public API

    public static void log(Level l, String prefix, String message){
        log(l,prefix + ": " + message);
    }
    @SuppressWarnings("WeakerAccess")
    public static void log(Level l, String message) {
        getInstance().getLogger().log(l,message);
    }

    // Private

    private Logger getLogger(){
        return logger;
    }
}
