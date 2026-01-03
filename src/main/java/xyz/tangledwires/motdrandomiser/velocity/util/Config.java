package xyz.tangledwires.motdrandomiser.velocity.util;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import xyz.tangledwires.motdrandomiser.velocity.MOTDRandomiserVelocity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private final Path configPath;
    private ConfigurationNode root;

    public Config(Path configPath) {
        this.configPath = configPath;
    }

    public void load() throws IOException {
        if (!Files.exists(this.configPath)) {
            MOTDRandomiserVelocity.getInstance().saveDefaultConfig();
        }

        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .path(this.configPath)
                .build();

        this.root = loader.load();
    }

    public ConfigurationNode getRoot() {
        return this.root;
    }
}
