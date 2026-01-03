package xyz.tangledwires.motdrandomiser.bungeecord;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.bstats.bungeecord.Metrics;
import xyz.tangledwires.motdrandomiser.MetricsIds;
import xyz.tangledwires.motdrandomiser.bungeecord.command.MOTDRandomiserCommand;
import xyz.tangledwires.motdrandomiser.bungeecord.listener.PingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MOTDRandomiserBungeeCord extends Plugin {
    private static MOTDRandomiserBungeeCord instance;

    private Configuration configuration;

    @Override
    public void onEnable() {
        @SuppressWarnings("unused")
        Metrics metrics = new Metrics(this, MetricsIds.BUNGEECORD);

        try {
            this.saveDefaultConfig();
            this.reloadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getProxy().getPluginManager().registerCommand(this, new MOTDRandomiserCommand());
        getProxy().getPluginManager().registerListener(this, new PingListener());

        instance = this;
    }

    public void saveDefaultConfig() throws IOException {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(configFile);
            try (InputStream in = getResourceAsStream("config.yml")) {
                in.transferTo(outputStream);
            }
        }
    }

    public void reloadConfig() throws IOException {
        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
    }

    public Configuration getConfig() {
        return configuration;
    }

    public static MOTDRandomiserBungeeCord getInstance() {
        return instance;
    }
}
