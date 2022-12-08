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
            final String playername = p.getName();

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

            if(arg1.isBlank()){
                arg1 = "help";
            }

            if (!p.hasPermission("guild." + arg1.toLowerCase())) {
                p.sendMessage(ZGuild.prefix + "Brak uprawnień.");
                return false;
            }

            IPlayer playerdata = ZGuild.PM.get(playername);
            IGuild playerguild = new IGuild("null", "null");
            boolean playerisking = playerguild.king.equalsIgnoreCase(playername);
            boolean playerisoficer = playerguild.oficers.contains(playername);
            boolean playerismember = playerguild.members.contains(playername);
            boolean playerhaveguild = false;

            if(ZGuild.GM.guildExist(playerdata.guild)){
                playerhaveguild = true;
                playerguild = ZGuild.GM.get(playerdata.guild);
            }

            switch(arg1.toLowerCase()) {
                default:
                case "help":
                    p.sendMessage(prefix + "TODO HELP");
                    break;
                case "top":
                    if(ZGuild.GM.guildsCount() == 0) {
                        p.sendMessage(prefix + "Brak gildi na serwerze.");
                    }

                    if(arg2.equalsIgnoreCase("zloto")) {
                        IGuild[] top = ZGuild.GM.topList("money");
                        p.sendMessage(ChatColor.DARK_GRAY + "-----------[" + ChatColor.GREEN + "TOP GILDIE - " + ChatColor.GOLD + "ZŁOTO" + ChatColor.DARK_GRAY + "]-----------");
                        int numer = 1;
                        for(IGuild g : top){
                            if(!g.name.isBlank()) {
                                p.sendMessage(ChatColor.DARK_GRAY + "--------------[ #" + ChatColor.WHITE + numer + " " + ChatColor.WHITE + g.name + ChatColor.DARK_GRAY + " ]--------------");
                                p.sendMessage(ChatColor.GRAY + "           Przywódca: " + ChatColor.GREEN + g.king + ChatColor.GRAY + "");
                                p.sendMessage(ChatColor.GRAY + "           Złoto: " + ChatColor.GOLD + g.money + ChatColor.GRAY + "");
                                numer++;
                            }
                        }
                        p.sendMessage(ChatColor.DARK_GRAY + "---------------------------------");
                    }else if(arg2.equalsIgnoreCase("czlonkowie")) {
                        IGuild[] top = ZGuild.GM.topList("members");
                        p.sendMessage(ChatColor.DARK_GRAY + "-----------[" + ChatColor.GREEN + "TOP GILDIE - " + ChatColor.AQUA + "CZŁONKOWIE" + ChatColor.DARK_GRAY + "]-----------");
                        int numer = 1;
                        for(IGuild g : top){
                            if(!g.name.isBlank()) {
                                p.sendMessage(ChatColor.DARK_GRAY + "--------------[ #" + ChatColor.WHITE + numer + " " + ChatColor.WHITE + g.name + ChatColor.DARK_GRAY + " ]--------------");
                                p.sendMessage(ChatColor.GRAY + "           Przywódca: " + ChatColor.GREEN + g.king + ChatColor.GRAY + "");
                                p.sendMessage(ChatColor.GRAY + "           Członkowie: " + ChatColor.AQUA + g.members.size() + ChatColor.GRAY + "");
                                numer++;
                            }
                        }
                        p.sendMessage(ChatColor.DARK_GRAY + "---------------------------------");
                    }

                    break;
                case "info":
                    if(!playerhaveguild){
                        p.sendMessage(prefix + "Nie należysz do żadnej gildi.");
                        break;
                    }
                    if(playerisking){
                        p.sendMessage(prefix + "Jesteś przywódcą gildi " + ChatColor.WHITE + playerdata.guild);
                        break;
                    }
                    else if(playerisoficer){
                        p.sendMessage(prefix + "Jesteś oficerem gildi " + ChatColor.WHITE + playerdata.guild);
                        break;
                    }
                    else if(playerismember){
                        p.sendMessage(prefix + "Jesteś członkiem gildi " + ChatColor.WHITE + playerdata.guild);
                        break;
                    }
                    else{
                        p.sendMessage("nazwa gildi:" + playerguild.name);
                        p.sendMessage(prefix + "error: guild-info:undefined-guild-rank");
                        p.sendMessage("king:" + playerguild.king);
                        p.sendMessage(playername);
                    }
                    break;
                case "zaloz":
                    if(playerhaveguild){
                        p.sendMessage(prefix + "Jesteś już członkiem innej gildi!");
                        break;
                    }
                    if(arg2.isBlank() || ZGuild.GM.guildExist(arg2)){
                        p.sendMessage(prefix + "Ta nazwa gildi nie jest dostępna.");
                        break;
                    }
                    if(arg2.length() < ZGuild.config.getInt("guild_name_min") || arg2.length() > ZGuild.config.getInt("guild_name_max")){
                        p.sendMessage(prefix + "Nazwa gildi musi zawierać od " + ChatColor.YELLOW + ZGuild.config.getInt("guild_name_min") + ChatColor.GRAY + " do " + ChatColor.YELLOW + ZGuild.config.getInt("guild_name_max") + ChatColor.GRAY + " znaków.");
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
                    newguild.money = ZGuild.config.getInt("guild_money_start");
                    newguild.slot = ZGuild.config.getInt("guild_slot_start");
                    ZGuild.GM.addGuild(newguild);
                    ZGuild.PM.get(playername).guild = arg2.toUpperCase();
                    p.sendMessage(prefix + "Gildia " + ChatColor.WHITE + arg2 + ChatColor.GRAY + " została utworzona.");
                    Bukkit.broadcastMessage(ChatColor.WHITE + playername + ChatColor.GRAY + " założył gildie " + ChatColor.WHITE + newguild.name);

                    break;
                case "zapros":
                    if(!playerhaveguild){
                        p.sendMessage(prefix + "Nie należysz do żadnej gildi.");
                        break;
                    }

                    if(!playerisking && playerisoficer){
                        p.sendMessage(prefix + "Tylko przywódca lub oficer może zapraszać graczy do gildi.");
                        break;
                    }

                    if(arg2.isBlank()){
                        p.sendMessage(prefix + "Niepoprawna nazwa gracza.");
                        p.sendMessage(prefix + "Wpisz /g zapros <nazwa gracza>");
                        break;
                    }

                    Player ipl = Bukkit.getPlayer(arg2);

                    if(ipl == null) {
                        p.sendMessage(prefix + "Niepoprawna nazwa gracza lub gracz jest offline.");
                        break;
                    }

                    p.sendMessage(prefix + "Zaproszenie do " + ChatColor.WHITE + arg2 + "zostało wysłane.");

                    ZGuild.PM.get(arg2).invites.add(playerguild.name);
                    ipl.sendMessage(prefix + "Otrzymałeś zaproszenie do gildi " + ChatColor.WHITE + playerguild.name);
                    ipl.sendMessage(prefix + "Aby do niej dołączyć wpisz /g join " + playerguild.name);

                    break;
                case "zaproszenia":
                    if(playerdata.invites.size() <= 0){
                        p.sendMessage(prefix + "Nie posiadasz zaproszeń do gildi.");
                    }

                    p.sendMessage(ChatColor.DARK_GRAY + "--------[LISTA ZAPROSZEŃ]--------");
                    for (String g : playerdata.invites) {
                        p.sendMessage(ChatColor.DARK_GRAY + "Zaprasza cię gildia: " + ChatColor.WHITE + "" + g);
                    }
                    p.sendMessage(ChatColor.DARK_GRAY + "--------[LISTA ZAPROSZEŃ]--------");

                    break;
                case "akceptuj":
                    if(playerhaveguild){
                        p.sendMessage(prefix + "Jesteś już członkiem gildi " + ChatColor.WHITE + playerdata.guild);
                        break;
                    }
                    if(!playerdata.invites.contains(arg2.toUpperCase())) {
                        p.sendMessage(prefix + "Nie znaleziono takiego zaproszenia.");
                        break;
                    }
                    ZGuild.PM.get(playername).guild = arg2.toUpperCase();
                    ZGuild.PM.get(playername).invites.clear();
                    p.sendMessage(prefix + "Dołączyłeś do gildi " + ChatColor.WHITE + arg2.toUpperCase());
                    ZGuild.GM.get(arg2).members.add(playername);
                    Bukkit.getPlayer(ZGuild.GM.get(arg2).king).sendMessage(prefix + playername + " zaakceptował zaproszenie.");

                    break;
                case "opusc":
                    if(!playerhaveguild){
                        p.sendMessage(prefix + "Nie należysz do żadnej gildi.");
                        break;
                    }

                    if(playerisking){
                        p.sendMessage(prefix + "Jesteś przywódcą gildi. Nie możesz jej opuścić.");
                        p.sendMessage(prefix + "Aby usunąć gildię wpisz /g usun potwierdzam");
                        break;
                    }

                    if(arg2.isBlank()){
                        p.sendMessage(prefix + "Aby potwierdzić wpisz /g opusc potwierdzam");
                    }

                    else if(arg2.equals("potwierdzam")){
                        p.sendMessage(prefix + "Opuściłeś gildie " + playerdata.guild);

                        if(playerisoficer){
                            ZGuild.GM.get(playerdata.guild).oficers.remove(playername);
                        }
                        else {
                            ZGuild.GM.get(playerdata.guild).members.remove(playername);
                        }
                        ZGuild.PM.get(playername).guild = "";
                    }

                    break;
                case "wyrzuc":
                    if(!playerhaveguild){
                        p.sendMessage(prefix + "Nie należysz do żadnej gildi.");
                        break;
                    }
                    if(!playerisking && !playerisoficer){
                        p.sendMessage(prefix + "Tylko przywódca lub oficer może wyrzucić gracza z gildi.");
                        break;
                    }
                    if(arg2.isBlank() || arg2.toLowerCase().equalsIgnoreCase(playername)){
                        p.sendMessage(prefix + "Niepoprawna nazwa gracza.");
                        break;
                    }

                    Player kpl = Bukkit.getPlayer(arg2);

                    if(kpl == null) {
                        p.sendMessage(prefix + "Niepoprawna nazwa gracza lub taki gracz nie istnieje.");
                        break;
                    }

                    if(playerguild.oficers.contains(arg2) && !playerisking){
                        p.sendMessage(prefix + "Tylko przywódca może wyrzucić oficera z gildi.");
                    }

                    if(playerguild.king.equalsIgnoreCase(arg2)){
                        p.sendMessage(prefix + "XD Nie możesz wyrzucić przywódcy gildi ! XD");
                    }

                    if(playerguild.members.contains(arg2)){
                        ZGuild.GM.get(playerdata.guild).members.remove(arg2);
                        p.sendMessage(prefix + "Gracz " + ChatColor.WHITE + arg2 + ChatColor.GRAY + " został wyrzucowny z gildi");
                        kpl.sendMessage(prefix + "Zostałeś wyrzucony z gildi przez " + ChatColor.WHITE + playername);
                    }

                    break;
                case "usun":
                    if(!playerhaveguild){
                        p.sendMessage(prefix + "Nie posiadasz żadnej gildi.");
                        break;
                    }

                    if(!playerisking){
                        p.sendMessage(prefix + "Tylko przywódca może rozwiązać gildie.");
                        break;
                    }

                    if(arg2.isBlank()){
                        p.sendMessage(prefix + "Gildia zostanie bezpowrotnie usunięta.");
                        p.sendMessage(prefix + "Aby potwierdzić wpisz /g usun potwierdzam");
                        break;
                    }
                    else if(arg2.equals("potwierdzam")){
                        for(String member : playerguild.members){
                            // kick players
                            Bukkit.getPlayer(member).sendMessage(prefix + playername + " rozwiązał gildię " + playerdata.guild);
                            ZGuild.PM.get(member).guild = "";
                        }
                        for(String oficer : playerguild.oficers){
                            // kick oficers
                            Bukkit.getPlayer(oficer).sendMessage(prefix + playername + " rozwiązał gildię " + playerdata.guild);
                            ZGuild.PM.get(oficer).guild = "";
                        }

                        ZGuild.PM.get(playername).guild = "";
                        p.sendMessage(prefix + "Gildia została rozwiązana.");
                        ZGuild.GM.removeGuild(playerdata.guild);
                        //todo send members message guild was deleted
                        break;
                    }
                    break;
            }

        }
        return true;
    }
}
