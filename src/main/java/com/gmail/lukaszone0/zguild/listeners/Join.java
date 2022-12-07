package com.gmail.lukaszone0.zguild.listeners;

import com.gmail.lukaszone0.zguild.ZGuild;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        ZGuild.PM.joinPlayer(p.getName().toLowerCase());
    }
}
