package drone;

import drone.exceptions.Crashed;

public class DronePhysics {
    public static final double GRAVITY_CONSTANT = 0.98/5500;

    public void tick(Drone drone, int duration){
        for (int i = 0; i < duration; i++) {
            adjustThrottle( drone, drone.getTargetX(), drone.getTargetY(), drone.getTargetZ() );
            moveDrone(drone );
            applyGravity(drone);
        }
    }

    private void applyGravity(Drone drone){
        double oldZ = drone.getZ();
        double downForce = drone.getWeight() * GRAVITY_CONSTANT;
        double newZ = oldZ - downForce;
        if(newZ < 0.0 && drone.getThrotle() > 0.0)
            throw new Crashed(drone);
        drone.setZ(Math.max(0.0, newZ));
    }

    private void moveDrone(Drone drone){
        double oldZ = drone.getZ();
        double newZ = oldZ + 1 *drone.getThrotle();
        drone.setZ(newZ);
    }

    public void adjustThrottle(Drone drone, double _x, double _y, double z){
        double oldZ = drone.getZ();

        if (z==0.0 && drone.getTargetZ() == 0.0)
            drone.setThrotle(0);
        else if( oldZ < z )
            drone.setThrotle( Drone.LIFT_THROTTLE );
        else
            drone.setThrotle( Drone.DOWN_THROTTLE );
    }
}
