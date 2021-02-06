package me.caledonian.hyskiespunch.utils;

import me.caledonian.hyskiespunch.HyskiesPunch;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Utils {

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // Getter
    public static String playerOnline(Player p){
        if(p != null){
            if(p.isOnline()){
                return chat("&aOnline");
            }else{
                return chat("&cOffline");
            }
        }else{return chat("&cNull");}
    }


    // Other Strings
    public static String ver = "0.1.2";
    public static Integer id = 6245;

    public static String prefix = chat(Files.msgs.getString("prefix"));

    public static String binary(Integer i){
        if(i.equals(1)){
            return chat("&aTrue");
        }else if(i.equals(0)){
            return chat("&cFalse");
        }else{
            return chat("&4NAVN");
        }
    }

    public static void punchPlayer(Player p, Player sender, String type) {
        Vector v = p.getLocation().getDirection();
        int num = Files.config.getInt("punch.amount");
        v.setY(num);
        p.setVelocity(v);

        // Broadcaster
        for (Player t : Bukkit.getOnlinePlayers()) {
            if (t.getName() != sender.getName()) {
                if(type.equalsIgnoreCase("remote")){
                    t.sendMessage(chat(Files.msgs.getString("punch.broadcast-remote").replace("%target%", p.getName()).replace("%prefix%", prefix).replace("%player%", sender.getName()).replace("%t_displayname%", p.getName()).replace("%p_displayname%", sender.getDisplayName())));
                }else{
                    t.sendMessage(chat(Files.msgs.getString("punch.broadcast").replace("%target%", p.getName()).replace("%prefix%", prefix).replace("%player%", sender.getName()).replace("%t_displayname%", p.getName()).replace("%p_displayname%", sender.getDisplayName())));
                }

            } else {

                String remote;
                if(type.equalsIgnoreCase("remote")){
                    remote = chat("&8(&cStaff&8)");
                }else{
                    remote = chat("");
                }

                sender.sendMessage(chat(Files.msgs.getString("punch.self").replace("%prefix%", prefix).replace("%player%", p.getName())).replace("%remote%", remote));
            }
        }
    }

    public static String getSQLStatus() {
        if (HyskiesPunch.SQL.isConnected()) {
            return chat("&aConnected");
        } else if (!HyskiesPunch.SQL.isConnected()) {
            return chat("&cDisconnected");
        } else {
            return chat("&4ERROR");
        }
    }

    public static String getPunchers() {
        Integer punchers = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (HyskiesPunch.SQL.isConnected()) {
                if (HyskiesPunch.data.canPunch(p) == 1) {
                    punchers = punchers + 1;
                }
            }
            return punchers.toString();
        }
        return chat("&4&l&nERR");
    }

    public static String getPunched() {
        Integer punched = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (HyskiesPunch.SQL.isConnected()) {
                if (HyskiesPunch.data.canBePunched(p) == 1) {
                    punched = punched + 1;
                }
            }
            return punched.toString();
        }
        return chat("&4&l&nERR");
    }

    public static void toggleCanPunch(Player p){
        if(HyskiesPunch.data.canPunch(p) == 1){
            HyskiesPunch.data.setCanPunch(p, 0);
            p.sendMessage(Utils.chat(Files.msgs.getString("toggle.disabled-self").replace("%prefix%", prefix)));
        }else if(HyskiesPunch.data.canPunch(p) == 0){
            HyskiesPunch.data.setCanPunch(p, 1);
            p.sendMessage(Utils.chat(Files.msgs.getString("toggle.enabled-self").replace("%prefix%", prefix)));
        }else{
            p.sendMessage(chat("&4&lERROR: &cCannot retrieve SQL Status. Try /hyskiespunch sql for more info."));
        }
    }

    public static void toggleBePunch(Player p){
        if(HyskiesPunch.data.canBePunched(p) == 1){
            HyskiesPunch.data.setBePunched(p, 0);
            p.sendMessage(Utils.chat(Files.msgs.getString("toggle.disabled-others").replace("%prefix%", prefix)));
        }else if(HyskiesPunch.data.canBePunched(p) == 0){
            HyskiesPunch.data.setBePunched(p, 1);
            p.sendMessage(Utils.chat(Files.msgs.getString("toggle.enabled-others").replace("%prefix%", prefix)));
        }else{
            p.sendMessage(chat("&4&lERROR: &cCannot retrieve SQL Status. Try /hyskiespunch sql for more info."));
        }
    }


    // Setters
    public static void toggle(Player p, String[] args){
        if(args.length == 2){
                // Player is online & there are the correct amount of args
                if(args[1].equalsIgnoreCase("can") || args[1].equalsIgnoreCase("self") || args[1].equalsIgnoreCase("canpunch")){
                    // Can punch others
                    toggleCanPunch(p);
                }else if(args[1].equalsIgnoreCase("be") || args[1].equalsIgnoreCase("others") || args[1].equalsIgnoreCase("bepunched")){
                    toggleBePunch(p);
                }else{p.sendMessage(chat(Files.msgs.getString("help.punch").replace("%prefix%", prefix)));}
        }else{p.sendMessage(chat(Files.msgs.getString("help.punch").replace("%prefix%", prefix)));}
    }

    // Force set
    public static void set(Player p, String[] args){
        if(args.length == 4){
            // Player is online & there are the correct amount of args
            if(p != null){
                // Player not null
                if(args[2].equalsIgnoreCase("can") || args[2].equalsIgnoreCase("self") || args[2].equalsIgnoreCase("canpunch")){
                    // Can punch others
                    int value = Integer.parseInt(args[3]);
                    if(value == 0 || value == 1){
                        HyskiesPunch.data.setCanPunch(p, value);
                        p.sendMessage(Utils.chat("%prefix% Set &b%player%'s &fability to punch to &b%val%&f.").replace("%prefix%", prefix).replace("%player%", p.getName()).replace("%val%", String.valueOf(value)));
                    }else{p.sendMessage(Utils.chat("&cErr: Must use 1 (true) or 0 (false)"));}
            }else if(args[2].equalsIgnoreCase("be") || args[2].equalsIgnoreCase("others") || args[2].equalsIgnoreCase("bepunched")){
                    // Can punch others
                    int value = Integer.parseInt(args[3]);
                    if(value == 0 || value == 1){
                        HyskiesPunch.data.setBePunched(p, value);
                        p.sendMessage(Utils.chat("%prefix% Set &b%player%'s &fability to be punched to &b%val%&f.").replace("%prefix%", prefix).replace("%player%", p.getName()).replace("%val%", String.valueOf(value)));
                    }else{p.sendMessage(Utils.chat("&cErr: Must use 1 (true) or 0 (false)"));}
            }
            }else{p.sendMessage(chat(Files.msgs.getString("help.set").replace("%prefix%", prefix)));}
        }else{p.sendMessage(chat(Files.msgs.getString("help.set").replace("%prefix%", prefix)));}
    }
}