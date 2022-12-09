package com.gmail.lukaszone0.zguild.managers;

import com.gmail.lukaszone0.zguild.ZGuild;
import com.gmail.lukaszone0.zguild.interfaces.IPlayer;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private ZGuild plugin;
    private final Map<String, IPlayer> playersDB = new HashMap<>();

    public PlayerManager(ZGuild pl){
        plugin = pl;
    }
    public IPlayer get(String playername){
        if(!playersDB.containsKey(playername.toLowerCase())){
            joinPlayer(playername);
        }
        return playersDB.get(playername.toLowerCase());
    }
    public void joinPlayer(String playername) {
        if(!playersDB.containsKey(playername.toLowerCase())){
            playersDB.put(playername.toLowerCase(), new IPlayer(playername));
            Bukkit.getLogger().info("Dodano gracza " + playername + " do bazy");
        }
        else{
            Bukkit.getLogger().info("Gracz " + playername + " juz istnieje w bazie");
        }
    }
    public void quitPlayer(String playername){
        if(playersDB.containsKey(playername.toLowerCase())){
            //todo update player data from file
            playersDB.remove(playername.toLowerCase());
            Bukkit.getLogger().info("Usunieti gracza " + playername + " z bazy");
        }
        else{
            Bukkit.getLogger().info("Gracz " + playername.toLowerCase() + " juz istnieje w bazie");
        }
    }
}
