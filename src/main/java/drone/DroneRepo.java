package drone;

import java.util.WeakHashMap;

/**
 * The repository
 */
public class DroneRepo {
    private final WeakHashMap<Integer, Drone> drones = new WeakHashMap<>();

    private static DroneRepo ourInstance = new DroneRepo();

    public static DroneRepo getInstance(){
        return ourInstance;
    }

    private DroneRepo(){
    }

    public WeakHashMap<Integer, Drone> getDrones(){
        return drones;
    }

    public static Drone getById(Integer droneId){
        return getInstance().getDrones().get( droneId );
    }

    public static void upsert(Drone subject){
        if(getInstance().getDrones().containsKey( subject.id() ))
            getInstance().getDrones().replace( subject.id(), subject );
        else
            getInstance().getDrones().put( subject.id(), subject );
    }
}
