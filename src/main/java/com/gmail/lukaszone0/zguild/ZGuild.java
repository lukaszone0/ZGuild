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

        pm.addPermission(new Permission("guild.zaloz"));
        pm.addPermission(new Permission("guild.top"));
        pm.addPermission(new Permission("guild.info"));
        pm.addPermission(new Permission("guild.usun"));
        pm.addPermission(new Permission("guild.zapros"));
        pm.addPermission(new Permission("guild.zaproszenia"));
        pm.addPermission(new Permission("guild.anulujzapro"));
        pm.addPermission(new Permission("guild.akceptuj"));
        pm.addPermission(new Permission("guild.wyrzuc"));
        pm.addPermission(new Permission("guild.opusc"));
        pm.addPermission(new Permission("guild.ustawdom"));
        pm.addPermission(new Permission("guild.usundom"));
        pm.addPermission(new Permission("guild.awansuj"));
        pm.addPermission(new Permission("guild.degraduj"));
        pm.addPermission(new Permission("guild.nowyprzywodca"));
        pm.addPermission(new Permission("guild.wplac"));

        getServer().getPluginManager().registerEvents(new onJoin(), this);
        getServer().getPluginManager().registerEvents(new onQuit(), this);
        getServer().getPluginManager().registerEvents(new onChat(), this);

        saveDefaultConfig();

        setup();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        GM.saveGuilds();
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
