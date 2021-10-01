package chemical.sans.droneswarm;

import chemical.sans.MainPlugin;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Vector;

public class DroneSwarm extends BukkitRunnable {
    Vector<DroneGoal> droneGoals = new Vector<>();
    Vector<Drone> drones = new Vector<>();

    Vector<Drone> freeDrones = new Vector<>();
    Vector<DroneGoal> freeDroneGoals = new Vector<>();

    Location origin;
    long droneAmount;

    Vector<DroneShieldPlayer> droneShieldPlayers = new Vector<>();

    public DroneSwarm(Location origin, long droneAmount) {
        this.origin = origin;
        this.droneAmount = droneAmount;

        for(int i = 0; i < droneAmount; i++) {
            new Drone(new Location(origin.getWorld(),origin.getX()+Math.random()*4-2,origin.getY()+Math.random()*4-2,origin.getZ()+Math.random()*4-2),this);
        }

        //MainPlugin.plugin.broadcast("DroneSwarm[] New DroneSwarm at " + origin.toVector().toBlockVector().toString() + " amount: " + droneAmount);

        this.runTaskTimer(MainPlugin.plugin,0,1);
    }


    public void setOrigin(Location origin) {
        this.origin = origin;
    }



    @Override
    public void run() {
        /*
        try {
            if(droneAmount < droneAmountCounter) {
                new Drone(new Location(origin.getWorld(),origin.getX()+Math.random()*4-2,origin.getY()+Math.random()*4-2,origin.getZ()+Math.random()*4-2),this);
                droneAmountCounter++;
            }
        } catch (Exception e) {
            MainPlugin.plugin.printStackTrace(e);
        }

         */

        for(Drone drone : drones) {
            try {
                drone.run();
            } catch (Exception e) {}
        }

        for(DroneShieldPlayer droneShieldPlayer : droneShieldPlayers) {
            try {
                droneShieldPlayer.run();
            } catch (Exception e) {}
        }

        /*
        if(this.player != null) {
            origin = player.getLocation();

            playerPosNew = player.getLocation();
            playerPosDif = playerPosNew.clone().subtract(playerPosOld).toVector();
            playerPosOld = player.getLocation();

            for(DroneGoal goal : playerShieldGoals) {
                goal.getLocation().add(playerPosDif);
            }
        }
         */


        for(Drone drone : freeDrones) {
            //MainPlugin.plugin.broadcast("FreeDrone[] searching for new goal");
            if(freeDroneGoals.size()==0) {
                //MainPlugin.plugin.broadcast("FreeDrone[] No free goal creating...");
                new DroneGoal(new Location(origin.getWorld(),origin.getX()+Math.random()*4-2,origin.getY()+Math.random()*4-2,origin.getZ()+Math.random()*4-2),this);
            }

            //MainPlugin.plugin.broadcast("FreeDrone[] Found new goal");
            drone.setDroneGoal(freeDroneGoals.get(0));
            drone.setOccupied(true);
        }

        //DRONE / DRONE_GOALS render
        for (Drone drone : drones) {
            Color color;

            if(drone.isOccupied()) {
                color = Color.GREEN;
            } else {
                color = Color.RED;
            }
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 0.4f);
            drone.getLocation().getWorld().spawnParticle(Particle.REDSTONE, drone.getLocation(), 2, dustOptions);
        }

        for (DroneGoal droneGoal : droneGoals) {
            Color color;

            if(droneGoal.isOccupied()) {
                color = Color.BLUE;
            } else {
                color = Color.PURPLE;
            }
            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 0.3f);
            droneGoal.getLocation().getWorld().spawnParticle(Particle.REDSTONE, droneGoal.getLocation(), 2, dustOptions);
        }

    }



}
