package xyz.tangledwires.motdrandomiser.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import xyz.tangledwires.motdrandomiser.velocity.MOTDRandomiserVelocity;

import java.io.IOException;

public class MOTDRandomiserCommand implements SimpleCommand {
    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        CommandSource sender = invocation.source();

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                try {
                    MOTDRandomiserVelocity.getInstance().getConfig().load();
                    sender.sendMessage(Component.text("Config reloaded!", NamedTextColor.GREEN));
                } catch (IOException e) {
                    sender.sendMessage(Component.text("An error occurred when reloading the config", NamedTextColor.RED));
                    throw new RuntimeException(e);
                }
                return;
            }
        }
        sender.sendMessage(Component.text("Usage: motdrandomiser reload", NamedTextColor.RED));
    }
}
