package drone.test.features;

import cucumber.api.java8.En;
import drone.Drone;
import drone.DronePhysics;
import drone.exceptions.Crashed;
import drone.logger.DroneLogger;

import java.text.DecimalFormat;
import java.util.logging.Level;

import static org.junit.Assert.assertTrue;


@SuppressWarnings("unused,WeakerAccess")
public class DroneTestSteps implements En {

    private Drone subject;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.###");

    public DroneTestSteps() {

        Then( "^the Truth is true$", () -> {
            assertTrue( true );
        } );
        Given( "^The Truth$", () -> {
            assertTrue( true );
        } );
        Given( "^A landed drone with id (\\d+) at (\\d+), (\\d+), (\\d+) \\(x,y,z\\)$",
           (Integer droneId, Integer x, Integer y, Integer z) -> {
             subject = new Drone(droneId,new DronePhysics());
             subject.setZ(z);
           }
        );
        When( "^Tell drone (\\d+) to get to (\\d+),(\\d+),(\\d+)$",
           (Integer droneId, Integer x, Integer y, Integer z) -> {
             subject.hoverAt(x,y,z);
        } );
        And( "^wait for (\\d+) ticks$", (Integer ticks) -> {
            subject.tick( ticks );
        } );
        Then( "^the drone should be at altitude (\\d+)\\.(\\d+) or above$", (Integer altInt, Integer altDec) -> {
            double expected = altInt + altDec/10.0;
            logDroneStatus(subject);
            assertTrue( subject.getZ() >= expected );
        } );
        When( "^Tell drone (\\d+) to hover at (\\d+),(\\d+),(\\d+)$", (Integer _droneId, Integer x, Integer y, Integer z) -> {
            subject.hoverAt(x,y,z);
        } );
        Then( "^the drone should be between altitude (\\d+)\\.(\\d+) and (\\d+)\\.(\\d+)$",
            (Integer min1, Integer minD, Integer max1, Integer maxD) -> {
            double z = subject.getZ();
            double expectedMin = min1 + minD/10.0;
            double expectedMax = max1 + maxD/10.0;
            logDroneStatus( subject );
            assertTrue( decimalFormat.format( z ) + " in range " + expectedMin + " and " + expectedMax, expectedMin <= z && z <= expectedMax );
        } );
        When( "^Tell drone (\\d+) to hover at (\\d+),(\\d+),(\\d+)\\.(\\d+)$", (Integer drone, Integer x, Integer y, Integer z1, Integer zd) -> {
           subject.setTarget(x,y, z1 + zd/10.0);
        } );
        Given( "^A flying drone with id (\\d+) at (\\d+), (\\d+), (\\d+) \\(x,y,z\\)$", (Integer droneId, Integer x, Integer y, Integer z) -> {
            subject = new Drone(droneId,new DronePhysics());
            subject.setZ(z);
        } );
        And( "^Tell drone (\\d+) to land$", (Integer droneId) -> {
           subject.land();
        } );
        Then( "^drone (\\d+) is landed$", (Integer droneId) -> {
            assertTrue( subject.isLanded() );
        } );
        Then( "^expect to crash when trying to land drone (\\d+)$", (Integer droneId) -> {
            boolean crashed = false;
            try {
                subject.land();
                subject.tick( 1 );
            } catch (Crashed e) {
                crashed = true;
            }
            assertTrue( "Expected to crash when landing from above 0.5", crashed );
        } );

    }

    private void logDroneStatus(Drone subject){
        StringBuilder msg = new StringBuilder( "DRONE" ).append( ": " )
                .append( subject.id() ).append( " Position: " )
                .append( "X:" ).append( decimalFormat.format(subject.getX())).append( " | " )
                .append( "Y:" ).append( decimalFormat.format(subject.getY())).append( " | " )
                .append( "Z:" ).append( decimalFormat.format(subject.getZ())).append( " | " )
                .append( "Status: ")
                .append( "throttle:" ).append( decimalFormat.format(subject.getThrotle()));

        DroneLogger.log(Level.INFO, getClass().getSimpleName(), msg.toString() );

    }
}
