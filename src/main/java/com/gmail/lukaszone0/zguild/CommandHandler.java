package com.gmail.lukaszone0.zguild;

import com.gmail.lukaszone0.zguild.interfaces.IGuild;
import com.gmail.lukaszone0.zguild.interfaces.IPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if (cmd.getName().equalsIgnoreCase("g") || cmd.getName().equalsIgnoreCase("guild") || cmd.getName().equalsIgnoreCase("gildia")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(ZGuild.prefix + "Nie jesteś graczem.");
                return true;
            }

            final Player p = (Player) sender;
            final String playername = p.getName().toLowerCase();

            String[] arg = new String[3];

            arg[0] = "";
            arg[1] = "";
            arg[2] = "";

            int i = 0;
            for (String data : args) {
                if (!data.isBlank()) {
                    arg[i] = data;
                }
                i++;
            }

            String arg1 = arg[0];
            String arg2 = arg[1];
            String arg3 = arg[2];

            if (!p.hasPermission("guild." + arg1) && !arg1.isBlank()) {
                p.sendMessage(ZGuild.prefix + "Brak uprawnień.");
                return false;
            }

            IPlayer playerdata = ZGuild.PM.get(playername);
            IGuild playerguild = new IGuild();

            if(playerdata.haveguild){
                playerguild = ZGuild.GM.get(playerdata.guildname);
            }

        }
        return true;
    }
}
