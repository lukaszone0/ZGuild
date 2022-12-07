package com.gmail.lukaszone0.zguild;

import com.gmail.lukaszone0.zguild.managers.ConfigManager;
import com.gmail.lukaszone0.zguild.managers.GuildManager;
import com.gmail.lukaszone0.zguild.managers.PlayerManager;
import java.util.logging.Level;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ZGuild extends JavaPlugin implements Listener {
    public static ZGuild instance;
    public static String prefix;

    public static PlayerManager PM = new PlayerManager();
    public static GuildManager GM = new GuildManager();

    public static ConfigManager config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        setup();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setup(){
        try {
            config = new ConfigManager(this, "config.yml");
            this.prefix = config.getString("plugin_prefix");
        } catch (Exception ex) {
            getLogger().log(Level.SEVERE, "Couldn't load groups.yml", ex);
        }
    }
    public static ZGuild getInstance() {
        return instance;
    }
}
