package drone;

import drone.exceptions.Crashed;
import drone.setup.DroneConfig;

/**
 * DronePhysics are responsible for
 * <pre>
 *
 *     - Moving the drone towards it's target coordinates
 *     - Applying gravity
 *     - Adjusting the throttle and rotation of the drone
 * </pre>
 *
 * <p>
 *     The one and only public api-call is <code>tick(drone,n)</code>. Call the number of ticks
 *     on the given drone.
 * </p>
 * <p><code>physics.tick()</code> get's called by a drone.</p>
 *
 * <pre>Usage: drone.tick(n)</pre>
 */
public class DronePhysics {


    /**
     * PUBLIC API – Execute given number of ticks on a given drone
     * @param drone id of the drone to be ticked.
     * @param ticks number of ticks to be executed
     */
    public void tick(Drone drone, int ticks){
        for (int i = 0; i < ticks; i++) {
            adjustThrottle( drone, drone.getTargetX(), drone.getTargetY(), drone.getTargetZ() );
            moveDrone(drone );
            applyGravity(drone);
        }
    }

    // Private methods

    private void applyGravity(Drone drone){
        double oldZ = drone.getZ();
        double downForce = drone.getWeight() * DroneConfig.gravityConstant();
        double newZ = oldZ - downForce;
        if(newZ < 0.0 && drone.getThrottle() > 0.0)
            throw new Crashed(drone);
        drone.setZ(Math.max(0.0, newZ));
    }

    private void moveDrone(Drone drone){
        double oldZ = drone.getZ();
        double newZ = oldZ + 1 *drone.getThrottle();
        drone.setZ(newZ);
    }

    private void adjustThrottle(Drone drone, double _x, double _y, double z){
        double oldZ = drone.getZ();

        if (z==0.0 && drone.getTargetZ() == 0.0)
            drone.setThrottle(0);
        else if( oldZ < z )
            drone.setThrottle( Drone.LIFT_THROTTLE );
        else
            drone.setThrottle( Drone.DOWN_THROTTLE );
    }
}
