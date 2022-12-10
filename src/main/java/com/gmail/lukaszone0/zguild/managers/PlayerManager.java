package com.gmail.lukaszone0.zguild.managers;

import com.gmail.lukaszone0.zguild.ZGuild;
import com.gmail.lukaszone0.zguild.interfaces.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private ZGuild plugin;
    private final Map<String, IPlayer> playersDB = new HashMap<>();

    public PlayerManager(ZGuild pl){
        this.plugin = pl;

        File folder = new File(plugin.getDataFolder() + "/players");
        if(!folder.exists()){
            folder.mkdir();
        }
    }
    public boolean joinPlayer(String playername) {
        File playerfile = new File(plugin.getDataFolder() + "/players/" + playername + ".yml");
        Yaml playeryaml = new Yaml(new Constructor(IPlayer.class));

        if(playerfile.exists()){
            try {
                IPlayer player = (IPlayer) playeryaml.load(new FileInputStream(playerfile));
                playersDB.put(player.name, player);
                return true;
            }
            catch (IOException ie){
                Bukkit.getLogger().info("cant read player file...");
                return false;
            }
        }
        playersDB.put(playername.toLowerCase(), new IPlayer(playername));
        return true;
    }
    public boolean quitPlayer(String playername){
        if(playersDB.containsKey(playername.toLowerCase())){
            if(!playersDB.get(playername).guild.isBlank()){
                File playerfile = new File(plugin.getDataFolder() + "/players/" + playername.toLowerCase() + ".yml");
                if(!playerfile.exists()){
                    try {
                        playerfile.createNewFile();
                    }
                    catch (IOException ie){
                        Bukkit.getLogger().info("Cant create playerfile: " + ie.getMessage());
                        return false;
                    }
                }
                YamlConfiguration playerdata = new YamlConfiguration();
                playerdata.set(playerfile.toString(), playersDB.get(playername.toLowerCase()));
                try {
                    playerdata.save(playerfile);
                }
                catch (IOException e){
                    Bukkit.getLogger().info("Cant save playerfile: " + e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
    public IPlayer get(String playername){
        if(!playersDB.containsKey(playername.toLowerCase())){
            playersDB.put(playername.toLowerCase(), new IPlayer(playername));
        }
        return playersDB.get(playername.toLowerCase());
    }
    public void savePlayers(){
        for(IPlayer p : playersDB.values()){
            if(!p.guild.isBlank()){
                File playerfile = new File("plugins/ZGuild/players/" + p.name + ".yml");
                if(!playerfile.exists()){
                    try {
                        playerfile.createNewFile();
                    }
                    catch (IOException ie){
                        Bukkit.getLogger().info("Cant create playerfile: " + ie.getMessage());
                    }
                }
                YamlConfiguration playerdata = new YamlConfiguration();
                playerdata.set(playersDB.get(p.name).toString(), p);
                try {
                    playerdata.save(playerfile);
                }
                catch (IOException e){
                    Bukkit.getLogger().info("Cant save playerfile: " + e.getMessage());
                }
            }
        }
    }
}
