package chemical.sans;

import chemical.sans.droneswarm.DroneShieldPlayer;
import chemical.sans.droneswarm.DroneSwarm;
import chemical.sans.events.EventListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPlugin extends JavaPlugin implements Listener {
    public static MainPlugin plugin;
    public static DroneSwarm mainDroneSwarm;
    public static DroneShieldPlayer mainDroneShieldPlayer;


    @Override
    public void onEnable() {
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new EventListener(),this);

    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equals("test")) {
            Player p = (Player) sender;
            //new ActiveBluePrint(BluePrint.debugBluePrint(), p.getLocation());

            if(mainDroneSwarm == null) {
                mainDroneSwarm = new DroneSwarm(p.getLocation(),0);
                mainDroneShieldPlayer = new DroneShieldPlayer(p,60,4,mainDroneSwarm);
            } else {
                mainDroneShieldPlayer.addPower(Integer.valueOf(args[0]));
            }

        }

        return false;
    }


    public void broadcast(String massage) {
        for(Player p : getServer().getOnlinePlayers()) {
            p.sendMessage(massage);
        }
    }

    public void printStackTrace(Exception e) {
        try {
            getLogger().info("§cCause: " + e.getCause().toString());
        } catch (Exception exception) { }
        for(StackTraceElement traceElement : e.getStackTrace()) {
            try {
                getLogger().info("§c       " + traceElement.toString());
            } catch (Exception exception) { }
        }

    }


}
