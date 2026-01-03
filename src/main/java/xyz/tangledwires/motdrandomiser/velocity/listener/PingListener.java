package xyz.tangledwires.motdrandomiser.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import net.kyori.adventure.text.Component;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import xyz.tangledwires.motdrandomiser.velocity.MOTDRandomiserVelocity;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class PingListener {
    @Subscribe
    public void onProxyPing(ProxyPingEvent event) {
        MOTDRandomiserVelocity plugin = MOTDRandomiserVelocity.getInstance();
        ConfigurationNode config = plugin.getConfig().getRoot();
        LocalDate date = LocalDate.now();
        Random random = new Random();

        List<String> timedMotds;
        List<String> anytimeMotds;
        try {
            timedMotds = config
                    .node("motds", date.getMonthValue() + "/" + date.getDayOfMonth())
                    .getList(String.class);
            anytimeMotds = config
                    .node("motds", "anytime")
                    .getList(String.class);
        } catch (SerializationException e) {
            throw new RuntimeException(e);
        }

        ServerPing original = event.getPing();
        if ((timedMotds == null || timedMotds.isEmpty()) && anytimeMotds != null && !anytimeMotds.isEmpty()) {
            event.setPing(original.asBuilder().description(Component.text(anytimeMotds.get(random.nextInt(anytimeMotds.size())))).build());
        } else if ((anytimeMotds == null || anytimeMotds.isEmpty()) && timedMotds != null && !timedMotds.isEmpty()) {
            event.setPing(original.asBuilder().description(Component.text(timedMotds.get(random.nextInt(timedMotds.size())))).build());
        }
    }
}
