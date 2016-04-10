package drone.test.units;

import drone.Drone;
import drone.DronePhysics;
import drone.exceptions.Crashed;
import drone.setup.DroneConfig;
import drone.setup.DroneLogger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Level;

import static org.junit.Assert.assertTrue;

public class DroneUnitTest {

    private Drone drone;
    private DronePhysics physics = new DronePhysics();

    @BeforeMethod
    public void setUp() {
        drone = new Drone(0,physics);
    }

    @Test
    public void instantiateADrone() {
        Assert.assertEquals( drone.id(), 0);
    }

    @Test
    public void droneParameters() {
        Assert.assertEquals( drone.getX(), 0.0 );
        Assert.assertEquals( drone.getY(), 0.0 );
        Assert.assertEquals( drone.getZ(), 0.0 );
        Assert.assertEquals( drone.getThrottle(), 0.0 );
        Assert.assertEquals( drone.getRx(), 0.0 );
        Assert.assertEquals( drone.getRy(), 0.0 );
        Assert.assertEquals( drone.getRz(), 0.0 );
    }

    @Test
    public void dontMoveOnTickWhenThrottleIsZero() {
        drone.tick(1);
        Assert.assertEquals( drone.getX(), 0.0 );
        Assert.assertEquals( drone.getY(), 0.0 );
        Assert.assertEquals( drone.getZ(), 0.0 );
        Assert.assertEquals( drone.getThrottle(), 0.0 );
        Assert.assertEquals( drone.getRx(), 0.0 );
        Assert.assertEquals( drone.getRy(), 0.0 );
        Assert.assertEquals( drone.getRz(), 0.0 );
    }

    @Test
    public void liftUp(){

        assertPosition( drone, 0.0, 0.0, 0.0, "liftUp" );

        // Up
        drone.hoverAt( 0, 0, 20.0 );
        drone.tick( 1 );
        assertPosition( drone, 0.0, 0.0, 0.13, "liftUp" );
        drone.tick( 1 );
        assertPosition( drone, 0.0, 0.0, 0.27, "liftUp" );
        drone.tick( 10 );
        assertPosition( drone, 0.0, 0.0, 1.59, "liftUp" );
    }

    @Test
    public void down() {
        drone.setZ(2);
        drone.hoverAt( 0,0,0 );
        assertPosition( drone, 0,0,2, "down" );

        drone.tick( 1 );
        assertPosition( drone, 0.0,0.0,1.73, "down");
        drone.tick( 1 );
        assertPosition( drone, 0.0,0.0,1.47, "down");
        drone.tick( 1 );
        assertPosition( drone, 0.0,0.0,1.2, "down");
        drone.tick( 1 );
        assertPosition( drone, 0.0,0.0,0.93, "down");
        drone.tick( 10 );
        assertPosition( drone, 0.0,0.0,0.0, "down");
    }

    @Test(expectedExceptions = Crashed.class)
    public void canCrash() {
        drone.setZ(0.1);
        drone.hoverAt( 0,0,0.1 );
        drone.tick(1);
        assertPosition( drone, 0,0,0, "canCrash" );
    }

    @Test
    public void land() {
        drone.hoverAt( 0,0,0.3 );
        drone.tick( 10 );
        drone.land();
        drone.tick( 1 );
        assertTrue( "Drone should be landed", drone.isLanded());
    }


    // Local Helpers

    private void assertPosition(Drone drone, double x, double y, double z, String prefix){
        DroneLogger.log( Level.INFO, prefix , "Expected/Actual x,y,z  0/0, 0/0, " + z + "/" + DroneConfig.decimalFormat.format(drone.getZ()) );

        Assert.assertEquals( Math.round(drone.getX()*100)/100.0, x );
        Assert.assertEquals( Math.round(drone.getY()*100)/100.0, y );
        Assert.assertEquals( Math.round(drone.getZ()*100)/100.0, z );
    }

}
