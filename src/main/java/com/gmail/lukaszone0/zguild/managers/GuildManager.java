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
