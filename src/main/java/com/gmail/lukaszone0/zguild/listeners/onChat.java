package com.gmail.lukaszone0.zguild.listeners;

import com.gmail.lukaszone0.zguild.interfaces.IGuild;
import com.gmail.lukaszone0.zguild.interfaces.IPlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.lukaszone0.zguild.ZGuild;

public class onChat implements Listener {

    private ZGuild plugin;

    public onChat(ZGuild pl){
        plugin = pl;
    }
    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        final String playername = p.getName();

        IPlayer playerdata = plugin.PM.get(playername);
        IGuild playerguild = new IGuild("null", "null");

        if(plugin.GM.guildExist(playerdata.guild)){
            playerguild = plugin.GM.get(playerdata.guild);
        }

        boolean playerisking = playerguild.king.equalsIgnoreCase(playername);
        boolean playerisoficer = playerguild.oficers.contains(playername);
        boolean playerismember = playerguild.members.contains(playername);
        boolean playerhaveguild = false;
        if(!playerdata.guild.isBlank()){
            playerhaveguild = true;
        }
        ChatColor guildcolor = ChatColor.GREEN;

        //guildcolor = ZGuild.GM.getColor(playerguild.color);

        if(playerhaveguild) {
            //e.setMessage(message);
            e.setFormat(ChatColor.DARK_GRAY + "[" + guildcolor + playerguild.name + ChatColor.DARK_GRAY + "] " + ChatColor.RESET + e.getPlayer().getName() + ": " + e.getMessage());
        }
        else{
            e.setFormat(ChatColor.RESET + e.getPlayer().getName() + ": " + e.getMessage());
        }
    }
}
