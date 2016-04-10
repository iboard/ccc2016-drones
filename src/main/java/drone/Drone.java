package drone;

import drone.exceptions.Crashed;

/**
 * For simplicity, a Drone is just a class with two fields
 * `firstName` and `lastName`
 */

public class Drone {
    static final double LIFT_THROTTLE = 0.4;
    static final double DOWN_THROTTLE = 0.1;
    private static final double CLEAR_TO_LAND_HEIGHT = 0.5;

    private final DronePhysics physics;
    private int id;

    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double throtle = 0.0;

    private double rx = 0;
    private double ry = 0;
    private double rz = 0;

    private double targetX = 0.0;
    private double targetY = 0.0;

    private double targetZ = 0.0;
    private double weight  = 1500;

    public Drone(int id, DronePhysics physics) {
        this.id = id;
        this.physics = physics;
        this.setTarget( 0,0,0 );
        this.setThrotle( 0.0 );
    }


    public double getX(){
        return x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY(){
        return y;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getZ(){
        return z;
    }

    public void setZ(double z){
        this.z = z;
    }

    public double getThrotle(){
        return throtle;
    }

    public void setThrotle(double throtle){
        this.throtle = throtle;
    }

    public double getRx(){
        return rx;
    }

    public void setRx(double rx){
        this.rx = rx;
    }

    public double getRy(){
        return ry;
    }

    public void setRy(double ry){
        this.ry = ry;
    }

    public double getRz(){
        return rz;
    }

    public void setRz(double rz){
        this.rz = rz;
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

    // PUBLIC API

    public void tick(int i){
        physics.tick(this, i);
    }


    public void hoverAt(Integer x, Integer y, Integer z){
        setTarget(x,y,z);
    }

    public void setTarget(double x, double y, double z){
        targetX = x;
        targetY = y;
        targetZ = z;
    }

    public double getWeight(){
        return weight;
    }

    public void land(){
      if( clearToLand() ) {
          setZ( 0.0 );
          setTarget( 0,0,0 );
          setThrotle( 0.0 );
      }
      else
        crash();
    }

    private void crash(){
        throw new Crashed( this );
    }

    private boolean clearToLand(){
        return getZ() < CLEAR_TO_LAND_HEIGHT;
    }

    public boolean isLanded(){
        return getZ() == 0.0 && getThrotle() == 0.0;
    }
}

