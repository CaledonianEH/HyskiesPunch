package me.caledonian.hyskiespunch.commands;

import me.caledonian.hyskiespunch.managers.CommandHandler;
import me.caledonian.hyskiespunch.utils.Files;
import me.caledonian.hyskiespunch.utils.Logger;
import me.caledonian.hyskiespunch.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class HyskiesPunch implements CommandHandler {
    String prefix = Utils.chat(Utils.prefix);
    private JavaPlugin plugin;
    public HyskiesPunch(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->{
            if (sender instanceof Player) {
                Player p = (Player) sender;

                // Console
                if (args.length == 0) {
                    msg(p);
                    // Status command
                }else if(args[0].equalsIgnoreCase("status")){
                        if(args.length == 2){
                            if(p.hasPermission(Files.config.getString("perms.status"))){
                                if(args[1].equalsIgnoreCase("sql")){
                                        p.sendMessage(Utils.chat("%prefix% The plugin is currently %status%&f to a database.").replace("%prefix%", prefix).replace("%status%", Utils.getSQLStatus()));
                                }else if(args[1].equalsIgnoreCase("online")){
                                    if(me.caledonian.hyskiespunch.HyskiesPunch.SQL.isConnected()){
                                        p.sendMessage(Utils.chat(Files.msgs.getString("status.be-punched").replace("%num%", Utils.getPunched()).replace("%prefix%", prefix)));
                                        p.sendMessage(Utils.chat(Files.msgs.getString("status.can-punch").replace("%num%", Utils.getPunchers()).replace("%prefix%", prefix)));
                                    }else{Logger.log(Logger.LogLevel.ERROR, "HyskiesSync is not connected to a database.");p.sendMessage(Utils.chat("&cError: HyskiesSync is not connected to a database."));}
                                }else{
                                    if(Bukkit.getPlayer(args[1]) != null){
                                        Player o = Bukkit.getPlayer(args[1]);

                                        String can = Utils.binary(me.caledonian.hyskiespunch.HyskiesPunch.data.canPunch(p));
                                        String be = Utils.binary(me.caledonian.hyskiespunch.HyskiesPunch.data.canBePunched(p));

                                        p.sendMessage(Utils.chat("%prefix% &b%player% &fplayer data: &8(&fCan: &b%can%&8) (&fBe: &b%be%&8) &fStatus: &a%online%").replace("%prefix%", prefix).replace("%player%", o.getName()).replace("%can%", can).replace("%be%", be).replace("%online%", Utils.playerOnline(o)));
                                    }else{p.sendMessage(Utils.chat(Files.msgs.getString("core.player-null").replace("%prefix%", prefix).replace("%player%", args[1])));}
                                }
                            }else{p.sendMessage(Utils.chat(Files.msgs.getString("core.no-perm").replace("%prefix%", prefix)));}
                        }else{p.sendMessage(Utils.chat(Files.msgs.getString("help.status").replace("%prefix%", prefix)));}
                }else if(args[0].equalsIgnoreCase("set")){
                    if(me.caledonian.hyskiespunch.HyskiesPunch.SQL.isConnected()){
                        Utils.set(p, args);
                    }else{Logger.log(Logger.LogLevel.ERROR, "HyskiesSync is not connected to a database.");p.sendMessage(Utils.chat("&cError: HyskiesSync is not connected to a database."));}
                }else{
                    msg(p);
                }
            } else {
                Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("core.console-error")));
            }
        });
    }

    private static void msg(Player p){
        List<String> msg1 = Files.msgs.getStringList("help.main");
        for (String x : msg1) {
            x = x.replace("%prefix%", Utils.chat(Utils.prefix));
            p.sendMessage(Utils.chat(x));
        }
    }
}
