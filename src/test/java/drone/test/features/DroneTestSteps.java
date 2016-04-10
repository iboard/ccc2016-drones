package drone.test.features;

import cucumber.api.java8.En;
import drone.Drone;
import drone.DronePhysics;
import drone.DroneRepo;
import drone.exceptions.Crashed;
import drone.setup.DroneLogger;

import java.text.DecimalFormat;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@SuppressWarnings({"unused,WeakerAccess", "unused"})
public class DroneTestSteps implements En {

    private Drone subject;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.###");

    public DroneTestSteps() {

        Before( () -> {
            DroneLogger.log( Level.INFO, "Reset Drones" );
            DroneRepo.getInstance().removeAll();
        });

        Then( "^the Truth is true$", () -> {
            assertTrue( true );
        } );

        Given( "^The Truth$", () -> {
            assertTrue( true );
        } );

        Given( "^A landed drone with id (\\d+) at (\\d+), (\\d+), (\\d+) \\(x,y,z\\)$",
                (Integer droneId, Integer x, Integer y, Integer z) -> {
                    subject = new Drone(droneId,new DronePhysics());
                    subject.setState(x,y,0,0);
                    DroneRepo.upsert( subject );
                }
        );

        Given( "^A flying drone with id (\\d+) at (\\d+), (\\d+), (\\d+) \\(x,y,z\\)$", (Integer droneId, Integer x, Integer y, Integer z) -> {
            subject = new Drone(droneId,new DronePhysics());
            subject.setState( x,y,z, 0.0 );
            DroneRepo.upsert(subject);
        } );

        When( "^Tell drone (\\d+) to get to (\\d+),(\\d+),(\\d+)$",
                (Integer droneId, Double x, Double y, Double z) -> {
                    subject = DroneRepo.getById(droneId);
                    subject.hoverAt(x,y,z);
                } );

        And( "^wait for (\\d+) ticks$", this::tickAllDrones );

        Then( "^the drone (\\d+) should be at altitude (\\d+)\\.(\\d+) or above$", (Integer droneId, Integer altInt, Integer altDec) -> {
            double expected = altInt + altDec/10.0;
            subject = DroneRepo.getById( droneId );
            logDroneStatus(subject);
            assertTrue( subject.getZ() >= expected );
        } );

        When( "^Tell drone (\\d+) to hover at (\\d+),(\\d+),(\\d+)$", (Integer droneId, Double x, Double y, Double z) -> {
            subject = DroneRepo.getById(droneId);
            subject.hoverAt(x,y,z);
        } );

        Then( "^drone (\\d+) should be between altitude (\\d+)\\.(\\d+) and (\\d+)\\.(\\d+)$",
            (Integer droneId, Integer min1, Integer minD, Integer max1, Integer maxD) -> {
            subject = DroneRepo.getById( droneId );
            double z = subject.getZ();
            double expectedMin = min1 + minD/10.0;
            double expectedMax = max1 + maxD/10.0;
            logDroneStatus( subject );
            assertTrue( decimalFormat.format( z ) + " in range " + expectedMin + " and " + expectedMax, expectedMin <= z && z <= expectedMax );
        } );

        When( "^Tell drone (\\d+) to hover at (\\d+),(\\d+),(\\d+)\\.(\\d+)$", (Integer drone, Integer x, Integer y, Integer z1, Integer zd) -> {
            subject = DroneRepo.getById( drone );
           subject.hoverAt(x,y, z1*1.0 + zd/10.0);
        } );
        And( "^Tell drone (\\d+) to land$", (Integer droneId) -> {
           subject = DroneRepo.getById( droneId );
           subject.land();
        } );

        Then( "^drone (\\d+) is landed$", (Integer droneId) -> {
            Drone drone = DroneRepo.getById(droneId);
            assertTrue( drone.isLanded() );
        } );

        Then( "^expect to crash when trying to land drone (\\d+)$", (Integer droneId) -> {
            boolean crashed = false;
            subject = DroneRepo.getById( droneId );
            try {
                subject.land();
                subject.tick( 1 );
            } catch (Crashed e) {
                crashed = true;
            }
            assertTrue( "Expected to crash when landing from above 0.5", crashed );
        } );

        Then( "^drone (\\d+) should be at (\\d+),(\\d+),(\\d+)$", (Integer droneId, Double x, Double y, Double z) -> {
            subject = DroneRepo.getById(droneId);
            assertPosition(subject, x,y,z);
        } );

    }

    private void tickAllDrones(Integer ticks){
        DroneRepo.idStream()
                .sequential()
                .forEach( (i) -> {
                    DroneLogger.log(Level.INFO, "Tick drone " + i);
                    DroneRepo.getById(i).tick( ticks );
                } );
    }

    private void assertPosition(Drone drone, double x, double y, double z){
        DroneLogger.log(Level.INFO, "Xexp=" + x + ", Xact=" + drone.getX() );
        DroneLogger.log(Level.INFO, "Yexp=" + y + ", Yact=" + drone.getY() );
        DroneLogger.log(Level.INFO, "Zexp=" + z + ", Zact=" + drone.getZ() );
        assertEquals( x, drone.getX(), 0.1 );
        assertEquals( y, drone.getY(), 0.1 );
        assertEquals( z, drone.getZ(), 0.2 );
    }

    private void logDroneStatus(Drone subject){
        String msg = "DRONE" + ": " +
                subject.id() + " Position: " +
                "X:" + decimalFormat.format( subject.getX() ) + " | " +
                "Y:" + decimalFormat.format( subject.getY() ) + " | " +
                "Z:" + decimalFormat.format( subject.getZ() ) + " | " +
                "Status: " +
                "throttle:" + decimalFormat.format( subject.getThrottle() );

        DroneLogger.log(Level.INFO, getClass().getSimpleName(), msg );

    }
}
