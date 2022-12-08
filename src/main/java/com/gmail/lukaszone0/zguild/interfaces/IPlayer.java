package com.gmail.lukaszone0.zguild.interfaces;

import java.util.ArrayList;
import java.util.List;

public class IPlayer {
    public String guildname = "";
    public String realname = "";
    public boolean haveguild = false;
    public List<String> invites = new ArrayList<>();

    public IPlayer(String realname){
        this.realname = realname;
    }
}
