package com.gmail.lukaszone0.zguild.managers;

import com.gmail.lukaszone0.zguild.ZGuild;
import com.gmail.lukaszone0.zguild.interfaces.IGuild;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class GuildManager extends YamlConfiguration {
    private final Map<String, IGuild> guildsDB = new HashMap<>();
    private final IGuild[] guildsTop = new IGuild[5];
    private ZGuild plugin;
    public GuildManager(ZGuild pl){
        this.plugin = pl;
        //todo load guilds from file HERE
        File folder = new File(plugin.getDataFolder() + "/guilds");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                try {
                    Yaml guildyaml = new Yaml(new Constructor(IGuild.class));
                    IGuild guild = (IGuild) guildyaml.load(new FileInputStream(new File("guilds/" + listOfFiles[i].getName() + ".yml")));
                    guildsDB.put(guild.name, guild);
                }
                catch (IOException ie){
                    Bukkit.getLogger().info("cant read guild file...");
                }
            }
        }
    }

    public void saveGuilds(){
        for(IGuild g : guildsDB.values()){
            File path = new File("/guilds" + g.name.toLowerCase() + ".yml");
            YamlConfiguration guild = new YamlConfiguration();
            guild.set("/guilds", g);
            try {
                guild.save(path);
            }
            catch (IOException e){
                Bukkit.getLogger().info("Cant save guild: " + g.name);
            }
        }
    }

    public int guildsCount(){
        return guildsDB.size();
    }
    public IGuild[] topList(String type){
        IGuild[] temp = new IGuild[5];
        temp[0] = new IGuild("null", "null");
        temp[1] = new IGuild("null", "null");
        temp[3] = new IGuild("null", "null");
        temp[4] = new IGuild("null", "null");
        temp[5] = new IGuild("null", "null");

        for(IGuild g : guildsDB.values()){
            if(type == "money"){
                if(g.money > temp[0].money){
                    temp[1] = temp[0];
                    temp[0] = g;
                }
                else if(g.money > temp[1].money){
                    temp[2] = temp[1];
                    temp[1] = g;
                }
                else if(g.money > temp[2].money){
                    temp[2] = temp[3];
                    temp[2] = g;
                }
                else if(g.money > temp[3].money){
                    temp[3] = temp[4];
                    temp[3] = g;
                }
                else if(g.money > temp[4].money){
                    temp[4] = g;
                }
            }
            else if(type == "members"){
                if(g.members.size() > temp[0].members.size()){
                    temp[0] = g;
                }
                else if(g.members.size() > temp[1].members.size()){
                    temp[1] = g;
                }
                else if(g.members.size() > temp[2].members.size()){
                    temp[2] = g;
                }
                else if(g.members.size() > temp[3].members.size()){
                    temp[3] = g;
                }
                else if(g.members.size() > temp[4].members.size()){
                    temp[4] = g;
                }
            }
        }

        return temp;
    }

    public boolean colorCorrect(String colorstring){

        switch(colorstring.toUpperCase()){
            case "RED":
            case "DARK_RED":
            case "DARK_PURPLE":
            case "LIGHT_PURPLE":
            case "AQUA":
            case "DARK_AQUA":
            case "BLUE":
            case "DARK_BLUE":
            case "GRAY":
            case "DARK_GRAY":
            case "GREEN":
            case "DARK_GREEN":
            case "YELLOW":
            case "GOLD":
                return true;
        }
        return false;
    }
    public IGuild get(String name){
        name = name.toUpperCase();
        if(guildsDB.containsKey(name)){
            return guildsDB.get(name);
        }
        return new IGuild("", "");
    }
    public void addGuild(IGuild newguild) {
        newguild.name = newguild.name.toUpperCase();
        if(!guildsDB.containsKey(newguild.name)) {
            guildsDB.put(newguild.name, newguild);
            //todo save guild to file HERE
        }
    }
    public void removeGuild(String name){
        name = name.toUpperCase();
        if(guildsDB.containsKey(name)){
            guildsDB.remove(name);
            //todo delete guild file HERE
        }
    }
    public boolean guildExist(String name){
        return guildsDB.containsKey(name.toUpperCase());
    }
}
