package xyz.tangledwires.motdrandomiser.spigot;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tangledwires.motdrandomiser.MetricsIds;
import xyz.tangledwires.motdrandomiser.spigot.command.MOTDRandomiserCommand;
import xyz.tangledwires.motdrandomiser.spigot.listener.PingListener;

public final class MOTDRandomiserSpigot extends JavaPlugin {
    private static MOTDRandomiserSpigot instance;

    @Override
    public void onEnable() {
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this, MetricsIds.BUKKIT);

        this.saveDefaultConfig();

        this.getCommand("motdrandomiser").setExecutor(new MOTDRandomiserCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new PingListener(), this);

        instance = this;
        this.getLogger().info("Enabled " + this.getDescription().getFullName());
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Disabled " + this.getDescription().getFullName());
    }

    public static MOTDRandomiserSpigot getInstance() {
        return instance;
    }
}
