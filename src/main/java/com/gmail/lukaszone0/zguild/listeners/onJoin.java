package com.gmail.lukaszone0.zguild.listeners;

import com.gmail.lukaszone0.zguild.ZGuild;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.gmail.lukaszone0.zguild.ZGuild;

public class onJoin implements Listener {

    private ZGuild plugin;

    public onJoin(ZGuild pl){
        plugin = pl;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        plugin.PM.joinPlayer(p.getName());
    }
}
