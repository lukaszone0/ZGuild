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
        Map<Integer, String> guilds = new HashMap<>();

        for(IGuild g : guildsDB.values()){
            guilds.put(g.members.size(), g.name);
        }
        List<IGuild> guldbymembers = new ArrayList<>(guildsDB.values());
        Collections.sort(guldbymembers, Comparator.comparing(IGuild::getSize));
        int i =0;
        for (IGuild p : guldbymembers) {
            if(i < 5){
                guildsTop[i] = p;
                i++;
            }
        }
    }
    public IGuild[] listTop(){
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
        return guildsDB.containsKey(name);
    }
}
