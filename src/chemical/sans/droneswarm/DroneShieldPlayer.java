package chemical.sans.droneswarm;

import chemical.sans.TreeUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Vector;

public class DroneShieldPlayer extends BukkitRunnable {
    DroneSwarm swarm;
    Player player;
    double radius;
    int amount;
    Vector<org.bukkit.util.Vector> offsets;
    Vector<DroneGoal> shieldGoals = new Vector<>();


    public DroneShieldPlayer(Player player,int amount,double radius, DroneSwarm droneSwarm) {
        this.swarm = droneSwarm;
        this.player = player;
        this.radius = radius;
        this.amount = amount;
        this.offsets = TreeUtils.pointsOnASphere(amount,radius);
        this.swarm.droneShieldPlayers.add(this);

        for(org.bukkit.util.Vector vector : offsets) {
            DroneGoal droneGoal = new DroneGoal(player.getLocation().clone().add(vector),droneSwarm);
            shieldGoals.add(droneGoal);
            Drone drone = new Drone(player.getLocation(),droneSwarm);
            drone.setDroneGoal(droneGoal);
        }
    }

    public void addPower(int Amount) {
        this.amount += Amount;

        this.offsets = TreeUtils.pointsOnASphere(amount,radius);

        for(int i = 0; i < offsets.size(); i++) {
            org.bukkit.util.Vector vector = offsets.get(i);
            DroneGoal droneGoal;
            try {
                droneGoal = shieldGoals.get(i);
                droneGoal.setPos(player.getLocation().clone().add(vector));
            } catch (Exception e) {
                droneGoal = new DroneGoal(player.getLocation().clone().add(vector),swarm);
                shieldGoals.add(droneGoal);
                Drone drone = new Drone(player.getLocation(),swarm);
                drone.setDroneGoal(droneGoal);
            }
        }
    }

    public void delete() {
        this.swarm.droneShieldPlayers.remove(this);
        for(DroneGoal droneGoal : shieldGoals) {
            droneGoal.delete();
        }
    }


    @Override
    public void run() {

        for(int i = 0; i < offsets.size(); i++) {
            org.bukkit.util.Vector vector = offsets.get(i);
            DroneGoal droneGoal = shieldGoals.get(i);
            droneGoal.setPos(player.getLocation().clone().add(vector));
        }
    }
}
