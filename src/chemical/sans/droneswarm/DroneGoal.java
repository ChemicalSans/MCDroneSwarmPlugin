package chemical.sans.droneswarm;


import org.bukkit.Location;

public class DroneGoal {
    public Location pos;
    public Drone drone;
    public DroneSwarm swarm;
    public boolean occupied;

    public DroneGoal(Location pos,DroneSwarm swarm) {
        this.pos = pos;
        this.swarm = swarm;
        this.occupied = false;
        this.swarm.droneGoals.add(this);
        this.swarm.freeDroneGoals.add(this);
    }

    public void delete() {
        this.swarm.droneGoals.remove(this);
        this.swarm.freeDroneGoals.remove(this);
        this.drone.setOccupied(false);
        this.drone.setDroneGoal(null);
    }

    public Location getLocation() {
        return pos;
    }

    public void setOccupied(boolean occupied, Drone drone) {
        this.occupied = occupied;
        this.drone = drone;

        if(occupied) {
            swarm.freeDroneGoals.remove(this);
        } else {
            swarm.freeDroneGoals.add(this);
        }
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setPos(Location position) {
        this.pos = position;
    }
}
