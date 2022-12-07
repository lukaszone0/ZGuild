package com.gmail.lukaszone0.zguild;

import com.gmail.lukaszone0.zguild.managers.ConfigManager;
import com.gmail.lukaszone0.zguild.managers.GuildManager;
import com.gmail.lukaszone0.zguild.managers.PlayerManager;
import com.gmail.lukaszone0.zguild.listeners.*;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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

        getCommand("g").setExecutor(new CommandHandler());
        getCommand("gildia").setExecutor(new CommandHandler());
        getCommand("guild").setExecutor(new CommandHandler());

        PluginManager pm = Bukkit.getPluginManager();

        pm.addPermission(new Permission("guild.create"));
        pm.addPermission(new Permission("guild.list"));
        pm.addPermission(new Permission("guild.delete"));
        pm.addPermission(new Permission("guild.add"));
        pm.addPermission(new Permission("guild.kick"));
        pm.addPermission(new Permission("guild.leave"));
        pm.addPermission(new Permission("guild.sethome"));
        pm.addPermission(new Permission("guild.delhome"));
        pm.addPermission(new Permission("guild.addoficer"));
        pm.addPermission(new Permission("guild.removeoficer"));
        pm.addPermission(new Permission("guild.king"));
        pm.addPermission(new Permission("guild.pay"));

        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Quit(), this);

        saveDefaultConfig();

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
