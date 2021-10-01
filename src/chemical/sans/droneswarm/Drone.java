package chemical.sans.droneswarm;


import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class Drone extends BukkitRunnable {
    public Location location;
    public DroneGoal goal;
    public DroneSwarm swarm;
    public boolean occupied;

    public Drone(Location location, DroneSwarm swarm) {
        this.location = location;
        this.swarm = swarm;
        this.swarm.drones.add(this);
        this.swarm.freeDrones.add(this);
        this.occupied = false;


    }

    public void delete() {
        this.swarm.drones.remove(this);
        this.swarm.freeDrones.remove(this);
        goal.setOccupied(false,null);
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
        if(occupied) {
            swarm.freeDrones.remove(this);
        } else {
            swarm.freeDrones.add(this);
        }
    }

    public void setDroneGoal(DroneGoal droneGoal) {
        try {
            DroneGoal old = this.goal;
            this.goal = droneGoal;
            this.goal.setOccupied(true,this);
            old.setOccupied(false,null);
        } catch (Exception e) {
            //MainPlugin.plugin.broadcast("Drone: setDroneGoal: OLD DRONE GOAL WAS NULL");
        }
    }

    @Override
    public void run() {
        Location diff = goal.getLocation().clone().subtract(location);
        diff.multiply(0.1);
        location.add(diff);

        //if (goal.getLocation().distance(location)<0.1) {goal.delete();}

        //MainPlugin.plugin.broadcast("Drone[] Pos: " + location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isOccupied() {
        return occupied;
    }
}
