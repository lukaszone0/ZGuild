package com.gmail.lukaszone0.zguild.listeners;

import com.gmail.lukaszone0.zguild.interfaces.IGuild;
import com.gmail.lukaszone0.zguild.interfaces.IPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.lukaszone0.zguild.ZGuild;

public class ChatListener implements Listener {
    @EventHandler
    public void onEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        final String playername = p.getName();

        IPlayer playerdata = ZGuild.PM.get(playername);
        IGuild playerguild = new IGuild("null", "null");

        if(ZGuild.GM.guildExist(playerdata.guild)){
            playerguild = ZGuild.GM.get(playerdata.guild);
        }

        boolean playerisking = playerguild.king.equalsIgnoreCase(playername);
        boolean playerisoficer = playerguild.oficers.contains(playername);
        boolean playerismember = playerguild.members.contains(playername);
        boolean playerhaveguild = false;
        if(!playerdata.guild.isBlank()){
            playerhaveguild = true;
        }
        ChatColor guildcolor = ChatColor.WHITE;

        guildcolor = ZGuild.GM.getColor(playerguild.color);

        if(playerhaveguild) {
            //e.setMessage(message);
            e.setFormat(guildcolor + playerguild.name + e.getPlayer().getName() + ": " + e.getMessage());
        }
    }
}
