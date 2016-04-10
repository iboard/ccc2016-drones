package drone;

import drone.setup.DroneLogger;

import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.stream.Stream;

/**
 * The repository
 */
public class DroneRepo {
    private final WeakHashMap<Integer, Drone> drones = new WeakHashMap<>();

    private static final DroneRepo ourInstance = new DroneRepo();

    public static DroneRepo getInstance(){
        return ourInstance;
    }

    private DroneRepo(){
        DroneLogger.log( Level.INFO, "START " + this.toString() );
    }

    private WeakHashMap<Integer, Drone> drones(){
        return drones;
    }

    public static Drone getById(Integer droneId){
        return getInstance().drones().get( droneId );
    }

    public static void upsert(Drone subject){
        if(getInstance().drones().containsKey( subject.id() ))
            getInstance().drones().replace( subject.id(), subject );
        else
            getInstance().drones().put( subject.id(), subject );
    }

    public void removeAll(){
        getInstance().drones().clear();
    }

    public static <S> Stream<Integer> idStream(){
        return getInstance().drones().keySet().stream();
    }
}
