package xyz.tangledwires.motdrandomiser.bungeecord.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import xyz.tangledwires.motdrandomiser.bungeecord.MOTDRandomiserBungeeCord;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class PingListener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        Configuration config = MOTDRandomiserBungeeCord.getInstance().getConfig();
        LocalDate date = LocalDate.now();
        Random random = new Random();

        List<String> timedMotds = config.getStringList("motds." + date.getMonthValue() + "/" + date.getDayOfMonth());
        List<String> anytimeMotds = config.getStringList("motds.anytime");
        if (timedMotds.isEmpty()) {
            event.getResponse().setDescriptionComponent(new TextComponent(anytimeMotds.get(random.nextInt(anytimeMotds.size()))));
        } else if (anytimeMotds.isEmpty()) {
            event.getResponse().setDescriptionComponent(new TextComponent(timedMotds.get(random.nextInt(timedMotds.size()))));
        }
    }
}
