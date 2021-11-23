package me.toples.tags.command;

import me.toples.tags.Tags;
import me.toples.tags.tags.impl.TagHandler;
import me.toples.tags.utils.menu.CC;
import me.toples.tags.tags.Tag;
import me.toples.tags.utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TagCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("tags")) {
            
            if (sender instanceof Player && !((Player) sender).hasPermission("tags.admin")) {
                 Player player = ((Player) sender);
                 player.sendMessage(CC.RED + "Permission denied!");
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(CC.PRIMARY + "Tags");
                sender.sendMessage(Color.translate("&7&m------------------------"));
                sender.sendMessage(CC.PRIMARY + "/tags create <tags> :" + " creates a new tags");
                sender.sendMessage(CC.PRIMARY + "/tags delete <tags> :" + " deleted a tags");
                sender.sendMessage(CC.PRIMARY + "/tags prefix <tags> <prefix> :" + " sets a prefix of a tags");
                sender.sendMessage(CC.PRIMARY + "/tags import : adds all tags that are in tags.yml");
                sender.sendMessage(CC.PRIMARY + "/tags export : saves all tags to tags.yml");
                sender.sendMessage(Color.translate("&7&m------------------------"));
            }else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("import")) {
                    Tags.getTagHandler().importTags();
                    sender.sendMessage(CC.GREEN + "Successfully imported tags from tags.yml!");
                }else if (args[0].equalsIgnoreCase("export")) {
                    Tags.getTagHandler().export();
                    sender.sendMessage(CC.GREEN + "Successfully exported tags to tags.yml");
                }
            }else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (Tag.getByName(args[1]) != null) {
                        sender.sendMessage(CC.RED + "this tags already exists!");
                        return false;
                    }
                    String tag = args[1];
                    Tag t = new Tag(UUID.randomUUID(), tag, " ", "tags." + tag);
                    t.create();
                    TagHandler.getTags().add(t);
                    sender.sendMessage(CC.PRIMARY + "Successfully created " + CC.SECONDARY + tag + CC.PRIMARY +  " tags!");
                }else if (args[0].equalsIgnoreCase("delete")) {
                    if (Tag.getByName(args[1]) == null) {
                        sender.sendMessage(CC.RED + "this tags does not exist!");
                        return false;
                    }

                    Tag tag = Tag.getByName(args[1]);
                    tag.delete();
                    TagHandler.getTags().remove(tag);
                    sender.sendMessage(CC.PRIMARY + "Successfully deleted " + CC.SECONDARY + tag.getName() + CC.PRIMARY + " tags!");
                }else {
                    sender.sendMessage(CC.PRIMARY + "Tags");
                    sender.sendMessage(Color.translate("&7&m------------------------"));
                    sender.sendMessage(CC.PRIMARY + "/tags create <tags> :" + " creates a new tags");
                    sender.sendMessage(CC.PRIMARY + "/tags delete <tags> :" + " deleted a tags");
                    sender.sendMessage(CC.PRIMARY + "/tags prefix <tags> <prefix> :" + " sets a prefix of a tags");
                    sender.sendMessage(CC.PRIMARY + "/tags import : adds all tags that are in tags.yml");
                    sender.sendMessage(CC.PRIMARY + "/tags export : saves all tags to tags.yml");
                    sender.sendMessage(Color.translate("&7&m------------------------"));
                }
            }else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("prefix")) {
                    if (Tag.getByName(args[1]) == null) {
                        sender.sendMessage(CC.RED + "this tags does not exist!");
                        return false;
                    }

                    Tag tag = Tag.getByName(args[1]);
                    tag.setPrefix(Color.translate(args[2]));
                    tag.save();
                    sender.sendMessage(CC.PRIMARY + "Successfully set " + CC.SECONDARY + tag.getName() + "`s" + CC.PRIMARY + " to " + Color.translate(args[2]));
                }else {
                    sender.sendMessage(CC.PRIMARY + "Tags");
                    sender.sendMessage(Color.translate("&7&m------------------------"));
                    sender.sendMessage(CC.PRIMARY + "/tags create <tags> :" + " creates a new tags");
                    sender.sendMessage(CC.PRIMARY + "/tags delete <tags> :" + " deleted a tags");
                    sender.sendMessage(CC.PRIMARY + "/tags prefix <tags> <prefix> :" + " sets a prefix of a tags");
                    sender.sendMessage(CC.PRIMARY + "/tags import : adds all tags that are in tags.yml");
                    sender.sendMessage(CC.PRIMARY + "/tags export : saves all tags to tags.yml");
                    sender.sendMessage(Color.translate("&7&m------------------------"));
                }
            }else {
                sender.sendMessage(CC.PRIMARY + "Tags");
                sender.sendMessage(Color.translate("&7&m------------------------"));
                sender.sendMessage(CC.PRIMARY + "/tags create <tags> :" + " creates a new tags");
                sender.sendMessage(CC.PRIMARY + "/tags delete <tags> :" + " deleted a tags");
                sender.sendMessage(CC.PRIMARY + "/tags prefix <tags> <prefix> :" + " sets a prefix of a tags");
                sender.sendMessage(CC.PRIMARY + "/tags import : adds all tags that are in tags.yml");
                sender.sendMessage(CC.PRIMARY + "/tags export : saves all tags to tags.yml");
                sender.sendMessage(Color.translate("&7&m------------------------"));
            }
        }

        return false;
    }
}
