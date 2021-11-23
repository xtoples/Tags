package me.toples.tags;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.toples.tags.command.TagCommand;
import me.toples.tags.command.TagsCommand;
import me.toples.tags.profiles.Profile;
import me.toples.tags.profiles.impl.ProfileHandler;
import me.toples.tags.tags.impl.TagHandler;
import me.toples.tags.tags.impl.TagListener;
import me.toples.tags.utils.menu.ButtonListener;
import me.toples.tags.utils.menu.MenuUpdateTask;
import lombok.Getter;
import me.toples.tags.tags.Tag;
import me.toples.tags.utils.Color;
import me.toples.tags.utils.ConfigFile;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Tags extends JavaPlugin {

    public static Random RANDOM;
    @Getter private static Tags instance;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private MongoClient client;
    @Getter private static MongoCollection<Document> profile;
    @Getter private static MongoCollection<Document> tags;
    @Getter private static Executor mongoExecutor;
    @Getter private static TagHandler tagHandler;
    @Getter private static ConfigFile tagFile;


    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

         tagFile = new ConfigFile("tags", this);

        RANDOM = new Random();

        ConsoleCommandSender sender = Bukkit.getConsoleSender();

        try {
            connect();
        } catch (Exception var2) {
            var2.printStackTrace();
            sender.sendMessage(Color.translate("&cFailed to connect to the database &7(MongoDB)"));
        }

        mongoExecutor = Executors.newFixedThreadPool(1);

        tagHandler = new TagHandler();
        tagHandler.getLoader().load();

        register();
        new MenuUpdateTask();

    }

    @Override
    public void onDisable() {
        for (Profile profile : ProfileHandler.getProfiles()) {
            profile.save();
        }

        for (Tag tag : TagHandler.getTags()) {
            tag.save();
        }

        tagHandler.export();

        instance = null;
    }

    private void register() {
        Bukkit.getPluginManager().registerEvents(new ButtonListener(), this);
        Bukkit.getPluginManager().registerEvents(new TagListener(), this);
        Bukkit.getPluginManager().registerEvents(new ProfileHandler(), this);
        getCommand("tags").setExecutor(new TagsCommand());
        getCommand("tags").setExecutor(new TagCommand());
    }

    private void connect() {
        client = new MongoClient(new MongoClientURI(getConfig().getString("mongodb.uri")));
        mongoDatabase = client.getDatabase(getConfig().getString("mongodb.database"));
        profile = mongoDatabase.getCollection("profiles");
        tags = mongoDatabase.getCollection("tags");
    }
}
