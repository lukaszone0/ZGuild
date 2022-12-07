package com.gmail.lukaszone0.zguild.managers;

import com.gmail.lukaszone0.zguild.interfaces.IPlayer;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private final Map<String, IPlayer> playersDB = new HashMap<>();

    public IPlayer get(String playername){
        if(playersDB.containsKey(playername)){
            return playersDB.get(playername);
        }
        return new IPlayer();
    }
    public void joinPlayer(String playername) {
        if(!playersDB.containsKey(playername)){
            //todo load player data from file
            playersDB.put(playername, new IPlayer());
        }
    }
    public void quitPlayer(String playername){
        if(playersDB.containsKey(playername)){
            //todo update player data from file
            playersDB.remove(playername);
        }
    }
}
