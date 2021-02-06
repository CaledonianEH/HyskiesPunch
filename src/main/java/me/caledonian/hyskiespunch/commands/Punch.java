package me.caledonian.hyskiespunch.commands;

import me.caledonian.hyskiespunch.HyskiesPunch;
import me.caledonian.hyskiespunch.managers.CommandHandler;
import me.caledonian.hyskiespunch.utils.Files;
import me.caledonian.hyskiespunch.utils.Logger;
import me.caledonian.hyskiespunch.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Punch implements CommandHandler {

    private JavaPlugin plugin;
    public Punch(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    String prefix = Utils.chat(Utils.prefix);

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->{
            if(sender instanceof Player){
                Player p = (Player) sender;
                if(args.length == 0){
                    p.sendMessage(Utils.chat(Files.msgs.getString("help.punch").replace("%prefix%", prefix)));
                    return;
                }
                if(args[0].equalsIgnoreCase("toggle")){
                    if(HyskiesPunch.SQL.isConnected()){
                        // Toggle command
                        if(p.hasPermission(Files.config.getString("perms.punch"))){
                            // Has perm
                            Utils.toggle(p, args);
                        }else{p.sendMessage(Utils.chat(Files.msgs.getString("core.no-perm").replace("%prefix%", prefix)));}
                    }else {Logger.log(Logger.LogLevel.ERROR, "HyskiesSync is not connected to a database.");p.sendMessage(Utils.chat("&cError: HyskiesSync is not connected to a database."));}
                }else if(args.length == 1) {
                    // Punch player
                    if (p.hasPermission(Files.config.getString("perms.punch-staff"))) {
                        if (Bukkit.getPlayer(args[0]) != null) {
                            Player t = Bukkit.getPlayer(args[0]);
                            Utils.punchPlayer(t, p, "remote");
                        } else {
                            p.sendMessage(Utils.chat(Files.msgs.getString("core.player-null").replace("%prefix%", prefix).replace("%player%", args[0])));
                        }
                    } else {
                        p.sendMessage(Utils.chat(Files.msgs.getString("core.no-perm").replace("%prefix%", prefix)));
                    }
                }

            }else{
                Bukkit.getConsoleSender().sendMessage(Utils.chat(Files.msgs.getString("core.console-error").replace("%prefix%", prefix)));
            }
        });
    }
}
