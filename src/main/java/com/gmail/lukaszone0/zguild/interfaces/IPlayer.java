package com.gmail.lukaszone0.zguild.interfaces;

import java.util.ArrayList;
import java.util.List;

public class IPlayer {
    public String guild = "";
    public String name = "";
    public String realname = "";
    public boolean isonline = false;
    public List<String> invites = new ArrayList<>();

    public IPlayer(String realname){
        this.realname = realname;
        this.isonline = true;
        this.name = realname.toLowerCase();
    }
}
