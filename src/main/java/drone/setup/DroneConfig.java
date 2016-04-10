package drone.setup;

import java.text.DecimalFormat;
import java.util.logging.Level;

public class DroneConfig {

    // LOGGING

    private static final Level LEVEL = Level.WARNING;

    /**
     * Setup the log level to one of <code>Level.*</code>
     * @return the level used at runtime
     */
    static Level logLevel() { return LEVEL; }

    // FORMAT

    public static final DecimalFormat decimalFormat = new DecimalFormat("#.###");

    // PHYSICS
    private static final double GRAVITY_CONSTANT = 0.98/5500;

    /**
     * The Gravity Constant used here is nothing but perfect, it's figured out by
     * trail and error ;-) ... feel free to improve the entire gravity calculation.
     * @return a factor representing the earth's gravity on each move.
     */
    public static double gravityConstant() { return GRAVITY_CONSTANT; }

}
