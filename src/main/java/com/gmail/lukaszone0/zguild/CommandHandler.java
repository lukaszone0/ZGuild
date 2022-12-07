package com.gmail.lukaszone0.zguild;

import com.gmail.lukaszone0.zguild.interfaces.IGuild;
import com.gmail.lukaszone0.zguild.interfaces.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        String prefix = ZGuild.prefix;

        if (cmd.getName().equalsIgnoreCase("g") || cmd.getName().equalsIgnoreCase("guild") || cmd.getName().equalsIgnoreCase("gildia")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(prefix + "Nie jesteś graczem.");
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
            IGuild playerguild = new IGuild("", "");

            if(playerdata.haveguild){
                playerguild = ZGuild.GM.get(playerdata.guildname);
            }

            switch(arg1.toLowerCase()) {
                default:
                case "help":
                    p.sendMessage(prefix + "TODO HELP");
                    break;
                case "list":
                    break;
                case "info":
                    if(!playerdata.haveguild){
                        p.sendMessage(prefix + "Nie należysz do żadnej gildi.");
                        break;
                    }

                    if(playerguild.king.equals(playername)){
                        p.sendMessage(prefix + "Jesteś przywódcą gildi " + ChatColor.WHITE + playerdata.guildname);
                        break;
                    }
                    else if(playerguild.oficers.contains(playername)){
                        p.sendMessage(prefix + "Jesteś oficerem gildi " + ChatColor.WHITE + playerdata.guildname);
                        break;
                    }
                    else if(playerguild.members.contains(playername)){
                        p.sendMessage(prefix + "Jesteś członkiem gildi " + ChatColor.WHITE + playerdata.guildname);
                        break;
                    }
                    else{
                        p.sendMessage(prefix + "error: guild-info:undefined-guild-rank");
                    }
                    break;
                case "create":
                    if(playerdata.haveguild){
                        p.sendMessage(prefix + "Jesteś już członkiem innej gildi!");
                        break;
                    }
                    if(arg2.isBlank() || ZGuild.GM.guildExist(arg2)){
                        p.sendMessage(prefix + "Ta nazwa gildi nie jest dostępna.");
                        break;
                    }
                    if(arg2.length() < ZGuild.config.getInt("guildnamemin") || arg2.length() > ZGuild.config.getInt("guildnamemax")){
                        p.sendMessage(prefix + "Nazwa gildi musi zawierać od " + ChatColor.YELLOW + ZGuild.config.getInt("guildnamemin") + ChatColor.GRAY + " do " + ChatColor.YELLOW + ZGuild.config.getInt("guildnamemax") + ChatColor.GRAY + " znaków.");
                        break;
                    }

                    boolean namevalid = true;
                    for(String bannedname : ZGuild.config.getStringList("guild_name_banned")){
                        if(arg2.equalsIgnoreCase(bannedname)){
                            p.sendMessage(prefix + "Nazwa gildi zawiera " + ChatColor.YELLOW + "niedozwolone " + ChatColor.GRAY + "słowa lub znaki.");
                            namevalid = false;
                        }
                    }
                    if(!namevalid){
                        break;
                    }
                    int PlayerMoney = 100;
                    int cost = ZGuild.config.getInt("guild_create_cost");
                    if(PlayerMoney < cost){
                        //TODO vault guild create cost
                        p.sendMessage(prefix + "Nie posiadasz wystarczająco pieniędzy.");
                        p.sendMessage(prefix + "Koszt założenia gildi to " + ChatColor.GOLD + cost);
                        break;
                    }

                    IGuild newguild = new IGuild(arg2, playername);

                    ZGuild.GM.addGuild(newguild);
                    ZGuild.PM.get(playername).guildname = arg2;
                    ZGuild.PM.get(playername).haveguild = true;
                    p.sendMessage(prefix + "Gildia " + ChatColor.WHITE + arg2 + ChatColor.GRAY + " została utworzona.");
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "----------------------");
                    Bukkit.broadcastMessage(ChatColor.GRAY + playername + " założył gildie " + ChatColor.WHITE + newguild.name);
                    Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "----------------------");

                    break;
                case "invite":
                    if(!playerdata.haveguild){
                        p.sendMessage(prefix + "Nie należysz do żadnej gildi.");
                        break;
                    }

                    if(!playerguild.king.equals(playername)){
                        p.sendMessage(prefix + "Tylko przywódca lub oficer może zapraszać graczy do gildi.");
                        break;
                    }
                    if(arg2.isBlank()){
                        p.sendMessage(prefix + "Niepoprawna nazwa gracza.");
                        break;
                    }

                    Player ipl = Bukkit.getPlayer(arg2);

                    if(ipl == null) {
                        p.sendMessage(prefix + "Niepoprawna nazwa gracza lub gracz jest offline.");
                        break;
                    }

                    IPlayer inpl = ZGuild.PM.get(arg2);
                    if(!inpl.isonline){
                        p.sendMessage(prefix + "Ten gracz jest offline.");
                        break;
                    }

                    inpl.invites.add(playerguild.name);
                    ipl.sendMessage(prefix + "Otrzymałeś zaproszenie do gildi " + ChatColor.WHITE + playerguild.name);
                    ipl.sendMessage(prefix + "Aby do niej dołączyć wpisz /g join " + playerguild.name);
                    p.sendMessage(prefix + "Zaproszenie zostało wysłane.");

                    break;
                case "listinvites":

                    for(String g : playerdata.invites){
                        p.sendMessage(prefix + "Zaproszenie od: " + g);
                    }

                    break;
                case "join":
                    if(playerdata.haveguild){
                        p.sendMessage(prefix + "Jesteś już członkiem innej gildi!");
                        break;
                    }

                    if(playerdata.invites.contains(arg2)){
                        ZGuild.PM.get(playername).guildname = arg2;
                        ZGuild.PM.get(playername).haveguild = true;
                        ZGuild.PM.get(playername).invites.clear();
                        p.sendMessage(prefix + "Dołączyłeś do gildi " + ChatColor.WHITE + arg2 + ".");
                    }
                    else {
                        p.sendMessage(prefix + "Nie znaleziono takiego zaproszenia.");
                    }
                    break;
                case "leave":
                    if(!playerdata.haveguild){
                        p.sendMessage(prefix + "Nie należysz do żadnej gildi.");
                        break;
                    }

                    if(playerguild.king.equals(playername)){
                        p.sendMessage(prefix + "Jesteś przywódcą gildi. Nie możesz jej opuścić.");
                        p.sendMessage(prefix + "Aby usunąć gildię wpisz /g delete");
                        break;
                    }

                    if(arg2.isBlank()){
                        p.sendMessage(prefix + "Aby potwierdzić wpisz /g leave confirm.");
                    }
                    else if(arg2.equals("confirm")){
                        p.sendMessage(prefix + "Opuściłeś gildie " + playerdata.guildname);
                        ZGuild.PM.get(playername).haveguild = false;
                        ZGuild.PM.get(playername).guildname = "";
                    }

                    break;
                case "delete":
                    if(!playerdata.haveguild){
                        p.sendMessage(prefix + "Nie należysz do żadnej gildi.");
                        break;
                    }

                    if(!playerguild.king.equals(playername)){
                        p.sendMessage(prefix + "Tylko przywódca może rozwiązać gildie.");
                        break;
                    }

                    if(arg2.isBlank()){
                        p.sendMessage(prefix + "Gildia zostanie bezpowrotnie usunięta.");
                        p.sendMessage(prefix + "Aby potwierdzić wpisz /g delete confirm.");
                        break;
                    }
                    else if(arg2.equals("confirm")){
                        for(String member : playerguild.members){
                            // kick players
                            Bukkit.getPlayer(member).sendMessage(prefix + playername + " rozwiązał gildię " + playerdata.guildname);
                            ZGuild.PM.get(member).guildname = "";
                            ZGuild.PM.get(member).haveguild = false;
                        }
                        for(String oficer : playerguild.oficers){
                            // kick oficers
                            Bukkit.getPlayer(oficer).sendMessage(prefix + playername + " rozwiązał gildię " + playerdata.guildname);
                            ZGuild.PM.get(oficer).guildname = "";
                            ZGuild.PM.get(oficer).haveguild = false;
                        }

                        ZGuild.PM.get(playername).haveguild = false;
                        ZGuild.PM.get(playername).guildname = "";
                        p.sendMessage(prefix + "Gildia została rozwiązana.");
                        ZGuild.GM.removeGuild(playerdata.guildname);
                        //todo send members message guild was deleted
                        break;
                    }
                    break;
            }

        }
        return true;
    }
}
