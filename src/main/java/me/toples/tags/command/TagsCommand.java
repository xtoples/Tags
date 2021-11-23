package me.toples.tags.command;

import me.toples.tags.tags.menu.TagsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tags")) {
            if (sender instanceof Player) {
                Player player = ((Player) sender).getPlayer();
                new TagsMenu().openMenu(player);
            }
        }


        return false;
    }
}
