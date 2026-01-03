package xyz.tangledwires.motdrandomiser.spigot.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import xyz.tangledwires.motdrandomiser.spigot.MOTDRandomiserSpigot;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class PingListener implements Listener {
    @EventHandler
    public void onServerListPingEvent(ServerListPingEvent event) {
        FileConfiguration config = MOTDRandomiserSpigot.getInstance().getConfig();
        LocalDate date = LocalDate.now();
        Random random = new Random();

        List<String> timedMotds = config.getStringList("motds." + date.getMonthValue() + "/" + date.getDayOfMonth());
        List<String> anytimeMotds = config.getStringList("motds.anytime");
        if (timedMotds.isEmpty()) {
            event.setMotd(anytimeMotds.get(random.nextInt(anytimeMotds.size())));
        } else if (anytimeMotds.isEmpty()) {
            event.setMotd(timedMotds.get(random.nextInt(timedMotds.size())));
        }
    }
}