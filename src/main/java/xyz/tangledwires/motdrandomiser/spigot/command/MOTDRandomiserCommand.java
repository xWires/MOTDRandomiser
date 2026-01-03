package xyz.tangledwires.motdrandomiser.spigot.command;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.tangledwires.motdrandomiser.spigot.MOTDRandomiserSpigot;

public class MOTDRandomiserCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                MOTDRandomiserSpigot.getInstance().reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
                return true;
            }
            return false;
        }
        return false;
    }
}