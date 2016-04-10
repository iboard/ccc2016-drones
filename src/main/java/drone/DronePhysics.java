package drone;

import drone.exceptions.Crashed;
import drone.setup.DroneConfig;
import drone.setup.DroneLogger;

import java.util.logging.Level;

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
     * PUBLIC API – Execute given number of ticks on a given drone. Rotation and Throttle will
     * be adjusted on after each tick.
     *
     * @param drone id of the drone to be ticked.
     * @param ticks number of ticks to be executed
     */
    public void tick(Drone drone, int ticks){
        for (int i = 0; i < ticks; i++) flyForOneTick( drone );
    }

    // Private methods

    private void flyForOneTick(Drone drone){
        adjustRotation( drone, drone.getTargetX(), drone.getTargetY());
        adjustThrottle( drone, drone.getTargetX(), drone.getTargetY(), drone.getTargetZ() );
        moveDrone(drone );
    }

    private void moveDrone(Drone drone){
        double newX = drone.getX() + drone.getXRotation();
        double newY = drone.getY() + drone.getYRotation();
        double newZ = drone.getZ() + drone.getThrottle();
        DroneLogger.log( Level.INFO, String.format(
                "MOVE DRONE %dX(c/n)=%s/%s, Y(c/t)=%s/%s, Z(c/t)=%s/%s, Rx=%s, Ry=%s.",
                drone.id(), drone.getX(), drone.getTargetX(),
                drone.getY(), drone.getTargetY(),
                drone.getZ(), drone.getTargetZ(),
                drone.getRx(), drone.getRy() )
        );
        drone.setPosition(newX, newY, newZ);
        applyGravity(drone);
    }

    private void applyGravity(Drone drone){
        double oldZ = drone.getZ();
        double downForce = drone.getWeight() * DroneConfig.gravityConstant();
        double newZ = oldZ - downForce;
        if(newZ < 0.0 && drone.getThrottle() > 0.0)
            throw new Crashed(drone);
        drone.setZ(Math.max(0.0, newZ));
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

    private void adjustRotation(Drone drone, double targetX, double targetY){
        double currentX = drone.getX();
        double currentY = drone.getY();

        adjustXAxis( drone, targetX, currentX );
        adjustYAxis( drone, targetY, currentY );
    }

    private void adjustYAxis(Drone drone, double targetY, double currentY){
        if(targetY < currentY)
            drone.turnFront();
        else if (targetY > currentY)
            drone.turnBack();
        else
            drone.centerY();
    }

    private void adjustXAxis(Drone drone, double targetX, double currentX){
        if(targetX < currentX)
            drone.turnLeft();
        else if (targetX > currentX)
            drone.turnRight();
        else
            drone.centerX();
    }

}
