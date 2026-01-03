package xyz.tangledwires.motdrandomiser.bungeecord.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import xyz.tangledwires.motdrandomiser.spigot.MOTDRandomiserSpigot;

public class MOTDRandomiserCommand extends Command {
    public MOTDRandomiserCommand() {
        super("motdrandomiser", "motdrandomiser.admin", "motdrandomizer");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                MOTDRandomiserSpigot.getInstance().reloadConfig();
                sender.sendMessage(new TextComponent(ChatColor.GREEN + "Config reloaded!"));
                return;
            }
        }
        sender.sendMessage(new TextComponent(ChatColor.RED + "Usage: motdrandomiser reload"));
    }
}
