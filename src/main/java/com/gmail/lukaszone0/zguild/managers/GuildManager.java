package com.gmail.lukaszone0.zguild.managers;

import com.gmail.lukaszone0.zguild.interfaces.IGuild;

import java.util.*;

public class GuildManager {
    private final Map<String, IGuild> guildsDB = new HashMap<>();
    private final IGuild[] guildsTop = new IGuild[5];

    public GuildManager(){
        //todo load guilds from file HERE
    }
    public IGuild[] list(){
        IGuild[] guilds = new IGuild[guildsDB.size()];
        int i = 0;
        for(IGuild g : guildsDB.values()){
            guilds[i] = g;
            i++;
        }
        return guilds;
    }
    public int guildsCount(){
        return guildsDB.size();
    }
    public void maketoplist(){
        IGuild[] temp = new IGuild[5];
        temp[0] = new IGuild("null", "null");
        temp[1] = new IGuild("null", "null");
        temp[3] = new IGuild("null", "null");
        temp[4] = new IGuild("null", "null");
        temp[5] = new IGuild("null", "null");

        for(IGuild g : guildsDB.values()){
            if(g.)
        }
    }
    public IGuild[] listTop(){
        maketoplist();
        return guildsTop;
    }
    public IGuild get(String name){
        if(guildsDB.containsKey(name)){
            return guildsDB.get(name);
        }
        return new IGuild("", "");
    }
    public void addGuild(IGuild newguild) {
        if(!guildsDB.containsKey(newguild.name)) {
            guildsDB.put(newguild.name, newguild);
            //todo save guild to file HERE
        }
    }
    public void removeGuild(String name){
        if(guildsDB.containsKey(name)){
            guildsDB.remove(name);
            //todo delete guild file HERE
        }
    }
    public boolean guildExist(String name){
        return guildsDB.containsKey(name.toUpperCase());
    }
}
