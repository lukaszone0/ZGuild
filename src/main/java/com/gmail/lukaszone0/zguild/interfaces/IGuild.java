package com.gmail.lukaszone0.zguild.interfaces;

import java.util.ArrayList;
import java.util.List;

public class IGuild {
    public String name = "";
    public String desc = "";
    public String color = "WHITE";
    public String king = "";
    public List<String> oficers = new ArrayList<>();
    public List<String> members = new ArrayList<>();
    public long homex = 0;
    public long homey = 0;
    public int gold = 0;
    public int slot = 10;

    public IGuild(String name, String kingname){
        this.name = name;
        this.king = kingname;
    }
}
