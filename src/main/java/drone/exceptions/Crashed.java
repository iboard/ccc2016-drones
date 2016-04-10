package drone.exceptions;

import drone.Drone;

/**
 * Thrown when medication.execute would cause an overdose
 */
public class Crashed extends RuntimeException {
    private final Drone drone;

    public Crashed(Drone drone){
        this.drone = drone;
    }

    @Override
    public String getMessage() {
        return "Drone crashed " + drone.id() + " from z=" + drone.getZ() + " with thdrottle " + drone.getThrotle();
    }

}
