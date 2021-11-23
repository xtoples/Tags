package me.toples.tags.tags.impl;

import me.toples.tags.Tags;
import me.toples.tags.handler.Loader;
import me.toples.tags.tags.Tag;
import me.toples.tags.utils.Color;
import org.bson.Document;

import java.util.UUID;

public class TagLoader implements Loader {

    @Override
    public void load() {
        if (Tags.getTags().find().first() == null) {
            return;
        }

        for (Document doc : Tags.getTags().find()) {
            Tag tag = new Tag(UUID.fromString(doc.getString("_id")), doc.getString("name"), Color.translate(doc.getString("prefix")), doc.getString("permission"));
            TagHandler.getTags().add(tag);
        }
    }
}
