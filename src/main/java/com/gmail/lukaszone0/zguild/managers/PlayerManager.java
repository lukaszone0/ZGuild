package com.gmail.lukaszone0.zguild.managers;

import com.gmail.lukaszone0.zguild.ZGuild;
import com.gmail.lukaszone0.zguild.interfaces.IGuild;
import com.gmail.lukaszone0.zguild.interfaces.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
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
        //todo load players from file HERE
        File folder = new File(plugin.getDataFolder() + "/players");
        if(!folder.exists()){
            folder.mkdir();
        }
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    Yaml guildyaml = new Yaml(new Constructor(IPlayer.class));
                    IPlayer player = (IPlayer) guildyaml.load(new FileInputStream(new File("players/" + listOfFiles[i].getName() + ".yml")));
                    playersDB.put(player.name.toLowerCase(), player);
                }
                catch (IOException ie){
                    Bukkit.getLogger().info("cant read guild file...");
                }
            }
        }
    }
    public IPlayer get(String playername){
        if(!playersDB.containsKey(playername.toLowerCase())){
            joinPlayer(playername);
        }
        return playersDB.get(playername.toLowerCase());
    }
    public void joinPlayer(String playername) {
        if(!playersDB.containsKey(playername.toLowerCase())){

            Yaml playeryaml = new Yaml(new Constructor(IPlayer.class));

            try {
                File playerfile = new File(plugin.getDataFolder() + "/players/" + playername + ".yml");
                if(!playerfile.exists()){
                    playerfile.createNewFile();
                }

                FileInputStream fis = new FileInputStream(plugin.getDataFolder() + "/players/" + playername + ".yml");
                IPlayer player = (IPlayer) playeryaml.load(fis);
                playersDB.put(player.name.toLowerCase(), player);
            }
            catch (IOException ie){
                Bukkit.getLogger().info("Cant create playerfile: " + ie.getMessage());
                playersDB.put(playername.toLowerCase(), new IPlayer(playername));
            }
        }
    }
    public void quitPlayer(String playername){
        if(playersDB.containsKey(playername.toLowerCase())){
            //todo update player data from file
            //playersDB.remove(playername.toLowerCase());

        }
    }

    public void savePlayers(){
        for(IPlayer p : playersDB.values()){

            File playerfile = new File("plugins/ZGuild/players/" + p.realname + ".yml");
            if(!playerfile.exists()){
                try {
                    playerfile.createNewFile();
                }
                catch (IOException ie){
                    Bukkit.getLogger().info("Cant create playerfile: " + ie.getMessage());
                }
            }
            YamlConfiguration playerdata = new YamlConfiguration();
            playerdata.set(playerfile.toString(), p);
            try {
                playerdata.save(playerfile);
            }
            catch (IOException e){
                Bukkit.getLogger().info("Cant save playerfile: " + p.realname);
            }
        }
    }
}
