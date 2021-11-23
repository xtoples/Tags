package me.toples.tags.tags.impl;

import me.toples.tags.Tags;
import me.clip.placeholderapi.PlaceholderAPI;
import me.toples.tags.profiles.Profile;
import me.toples.tags.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TagListener implements Listener {

    @EventHandler
    public void on(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        Profile profile = Profile.getByUUID(player.getUniqueId());
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            String format = Tags.getInstance().getConfig().getString("chat-format");
            e.setFormat(Color.translate(PlaceholderAPI.setPlaceholders(player, format.replace("<player>", player.getName()).replace("<tags>", profile.getTag() != null ? Color.translate(profile.getTag().getPrefix()) : "").replace("<message>", e.getMessage()))));
        }else {
            String format = Tags.getInstance().getConfig().getString("chat-format");
            e.setFormat(Color.translate(format.replace("<player>", player.getName()).replace("<tags>", profile.getTag() != null ? Color.translate(profile.getTag().getPrefix()) : "").replace("<message>", e.getMessage())));
        }
    }
}
