package me.toples.tags.profiles.impl;

import me.toples.tags.Tags;
import me.toples.tags.profiles.Profile;
import lombok.Getter;
import me.toples.tags.tags.Tag;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.List;

public class ProfileHandler implements Listener {

    @Getter
    private static final List<Profile> profiles = new ArrayList<>();

    @EventHandler
    public void on(PlayerLoginEvent e) {
        Player player = e.getPlayer();

        if (Tags.getProfile().find(new Document("_id", player.getUniqueId().toString())).first() == null) {
            Profile profile = new Profile(player.getUniqueId(), null);
            profile.create();
            profiles.add(profile);
        }else {
            Document doc = Tags.getProfile().find(new Document("_id", player.getUniqueId().toString())).first();
            if (Profile.getByUUID(player.getUniqueId()) == null) {
                Profile profile = new Profile(player.getUniqueId(), doc.getString("tags").equalsIgnoreCase("null") ? null : Tag.getById(doc.getString("tags")));
                profiles.add(profile);
            }
        }
    }

}
