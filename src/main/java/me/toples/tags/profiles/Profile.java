package me.toples.tags.profiles;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import me.toples.tags.Tags;
import lombok.Getter;
import lombok.Setter;
import me.toples.tags.profiles.impl.ProfileHandler;
import me.toples.tags.tags.Tag;
import org.bson.Document;

import java.util.UUID;

@Getter @Setter
public class Profile {

    private UUID player;
    private Tag tag;

    public Profile(UUID player, Tag tag) {
        this.player = player;
        this.tag = tag;
    }

    public Document toBson() {
        return new Document("_id", player.toString())
                .append("tags", tag == null ? "null" : tag.getId().toString());
    }

    public void create() {
        Tags.getProfile().insertOne(toBson());
    }

    public void save() {
        Document doc = Tags.getProfile().find(new Document("_id", player.toString())).first();

        if (doc == null) {
            create();
            return;
        }

        doc.put("tags", tag == null ? "null" : tag.getId().toString());

        Tags.getMongoExecutor().execute(() -> Tags.getProfile().replaceOne(Filters.eq("_id", player.toString()), doc, new ReplaceOptions().upsert(true)));

    }

    public static Profile getByUUID(UUID uuid) {
        return ProfileHandler.getProfiles().stream().filter(profile -> profile.getPlayer().equals(uuid)).findFirst().orElse(null);
    }


}
