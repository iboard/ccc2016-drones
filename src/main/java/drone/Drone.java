package drone;

import drone.exceptions.Crashed;

/**
 * <p>
 *    The Drone has an Id, a target, a current position and rotation, and a throttle.
 * </p>
 * <p>
 *    You can conduct the drone by setting a target position and wait for a number of
 *    ticks. <br>
 *    When the drone is at 0.5 or below, you can <code>land()</code> the drone safely.
 *    If you call <em>land()</em> when the drone is at higher altitude, then it will <code>crash()</code>
 * </p>
 *
 *
 * <pre>
 *
 *      - getTargetX(), getTargetY(), getTargetZ()
 *      - getX(), getY(), getZ()
 *      - getThrottle() [0.0..1.0]
 *
 *      USAGE:
 *        drone.hoverAt(x,y,z);   drone.tick(100);
 *        drone.hoverAt(x,y,0.3); drone.tick(100);
 *        drone.land(); drone.tick(1);
 * </pre>
 *
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class Drone {
    static final double LIFT_THROTTLE = 0.4;
    static final double DOWN_THROTTLE = 0.1;
    /**
     * Drone must be at or below that value to be able to land safely.
     */
    private static final double CLEAR_TO_LAND_HEIGHT = 0.5;

    // Mandatory basics
    private final int id;
    private final DronePhysics physics;

    // Current Position
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double throttle = 0.0;

    // Current Rotation
    private double rx = 0;
    private double ry = 0;
    private final double rz = 0;

    // Active Target
    private double targetX = 0.0;
    private double targetY = 0.0;
    private double targetZ = 0.0;
    private double XRotation;

    /**
     * Initialize a Drone with an Id and DronePhysics
     * @param id - The unique id of this drone
     * @param physics - the physics used to calculate actions
     */
    public Drone(int id, DronePhysics physics) {
        this.id = id;
        this.physics = physics;
        this.setTarget( 0,0,0 );
        this.setThrottle( 0.0 );
    }

    // GETTER AND SETTER

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getZ(){
        return z;
    }

    public void setZ(double z){ this.z = z; }

    public double getThrottle(){
        return throttle;
    }

    public void setThrottle(double throttle){
        this.throttle = throttle;
    }

    public double getRx(){
        return rx;
    }

    public double getRy(){
        return ry;
    }

    public double getRz(){
        return rz;
    }

    public int id(){
        return this.id;
    }

    public double getTargetX(){
        return targetX;
    }

    public double getTargetY(){
        return targetY;
    }

    public double getTargetZ(){
        return targetZ;
    }

    @SuppressWarnings("SameReturnValue")
    public double getWeight(){
        return 1500.0;
    }

    // PUBLIC API

    /**
     * PUBLIC API - Execute n ticks, using Drone's physics
     * @param i - Number of ticks to be executed.
     */
    public void tick(int i){
        physics.tick(this, i);
    }

    /**
     * Fly to the given coordinates and stay within this place.
     * @param x - X-position to reach
     * @param y - Y-position to reach
     * @param z - Z-position to reach
     */
    public void hoverAt(double x, double y, double z){
        setTarget(x,y,z);
    }

    /**
     * PUBLIC API (Command) – Land The Damn Thing. <em>Will crash (throw Crashed-exception) if not clear to land</em>
     */
    public void land(){
      if( isClearToLand() ) {
          setZ( 0.0 );
          setTarget( 0,0,0 );
          setThrottle( 0.0 );
      }
      else
        crash();
    }

    /**
     * PUBLIC API (Status)
     * @return true if z-position is 0.0 and throttle is off
     */
    public boolean isLanded(){
        return getZ() == 0.0 && getThrottle() == 0.0;
    }


    // Private Methods

    private void setTarget(double x, double y, double z){
        targetX = x;
        targetY = y;
        targetZ = z;
    }

    private void crash(){
        throw new Crashed( this );
    }

    private boolean isClearToLand(){
        return getZ() < CLEAR_TO_LAND_HEIGHT;
    }

    public void turnLeft(){ setRx( -1.0 ); }
    public void turnRight(){ setRx(  1.0 ); }
    public void turnFront(){ setRy( -1.0 ); }
    public void turnBack(){ setRy( 1.0 ); }
    private void setRx(double rx){ this.rx = rx; }
    private void setRy(double ry){ this.ry = ry; }
    public void centerX(){ setRx(0.0); }
    public void centerY(){ setRy(0.0); }
    public double getXRotation(){
        return 0.1 * getRx();
    }
    public double getYRotation(){
        return 0.1 * getRy();
    }
    public void setY(double y){ this.y = y; }
    public void setX(double x){ this.x = x; }
}

