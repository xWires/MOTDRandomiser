package xyz.tangledwires.motdrandomiser.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.bstats.velocity.Metrics;
import org.slf4j.Logger;
import xyz.tangledwires.motdrandomiser.MetricsIds;
import xyz.tangledwires.motdrandomiser.velocity.command.MOTDRandomiserCommand;
import xyz.tangledwires.motdrandomiser.velocity.listener.PingListener;
import xyz.tangledwires.motdrandomiser.velocity.util.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(
        id = "motdrandomiser",
        name = "MOTDRandomiser",
        version = "1.0-SNAPSHOT",
        authors = "xWires"
)
public class MOTDRandomiserVelocity {
    private static MOTDRandomiserVelocity instance;

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private final Metrics.Factory metricsFactory;

    private Config config;

    @Inject
    public MOTDRandomiserVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.metricsFactory = metricsFactory;

        try {
            this.saveDefaultConfig();
            this.config = new Config(dataDirectory.resolve("config.yml"));
            this.config.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        instance = this;

        logger.info("Enabled MOTDRandomiser");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        @SuppressWarnings("unused")
        Metrics metrics = metricsFactory.make(this, MetricsIds.VELOCITY);

        CommandManager cm = server.getCommandManager();
        cm.register(cm.metaBuilder("motdrandomiser").aliases("motdrandomizer").plugin(this).build(), new MOTDRandomiserCommand());

        server.getEventManager().register(this, new PingListener());
    }

    public void saveDefaultConfig() throws IOException {
        if (!Files.exists(dataDirectory)) {
            Files.createDirectory(dataDirectory);
        }

        File configFile = dataDirectory.resolve("config.yml").toFile();

        if (!configFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(configFile);
            try (InputStream in = getClass().getResourceAsStream("/config.yml")) {
                in.transferTo(outputStream);
            }
        }
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Config getConfig() {
        return this.config;
    }

    public static MOTDRandomiserVelocity getInstance() {
        return instance;
    }
}
