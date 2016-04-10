package drone.exceptions;

import drone.Drone;

/**
 * Thrown when a Drone lands from above 0.5
 */
public class Crashed extends RuntimeException {
    private final Drone drone;

    public Crashed(Drone drone){
        this.drone = drone;
    }

    @Override
    public String getMessage() {
        return "Drone crashed " + drone.id() + " from z=" + drone.getZ() + " with throttle " + drone.getThrottle();
    }

}
