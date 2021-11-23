package me.toples.tags.tags.impl;

import com.mongodb.client.model.Filters;
import lombok.Getter;
import me.toples.tags.Tags;
import me.toples.tags.handler.Handler;
import me.toples.tags.handler.Loader;
import me.toples.tags.tags.Tag;
import me.toples.tags.utils.ConfigFile;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TagHandler implements Handler {

    private TagLoader tagLoader;
    @Getter private static List<Tag> tags = new ArrayList<>();

    public TagHandler() {
        tagLoader = new TagLoader();
    }

    @Override
    public Loader getLoader() {
        return tagLoader;
    }

    public void export() {
        ConfigFile file = Tags.getTagFile();
        YamlConfiguration config = file.getConfig();
        tags.forEach(tag -> {
            String path = "tags." + tag.getId().toString();
            config.set(path + ".name", tag.getName());
            config.set(path + ".prefix", tag.getPrefix());
            config.set(path + ".permission", tag.getPermission());
        });
        file.save();
    }

    public void importTags() {
        ConfigFile file = Tags.getTagFile();
        if (file.getConfig().getConfigurationSection("tags") == null || file.getConfig().getConfigurationSection("tags").getKeys(false).isEmpty()) return;
        for (String tag : file.getConfig().getConfigurationSection("tags").getKeys(false)) {
            ConfigurationSection cs = file.getConfig().getConfigurationSection("tags");
            Tag tag1 = new Tag(UUID.fromString(tag), cs.getString("name"), cs.getString("prefix"), cs.getString("permission"));
            if (Tags.getTags().find(Filters.eq("_id", tag)) == null) {
                tag1.create();
            }
            if (Tag.getById(tag) == null) {
                tags.add(tag1);
            }
        }
    }


}
