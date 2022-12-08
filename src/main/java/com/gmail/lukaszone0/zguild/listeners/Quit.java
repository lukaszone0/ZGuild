package com.gmail.lukaszone0.zguild.listeners;

import com.gmail.lukaszone0.zguild.ZGuild;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        ZGuild.PM.quitPlayer(p.getName());
    }
}
