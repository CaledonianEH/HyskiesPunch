package me.caledonian.hyskiespunch.listener;

import me.caledonian.hyskiespunch.HyskiesPunch;
import me.caledonian.hyskiespunch.data.SQLUtils;
import me.caledonian.hyskiespunch.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SQLJoin implements Listener {
    private HyskiesPunch plugin;
    public SQLJoin(HyskiesPunch plugin){this.plugin = plugin;}

    // Create player
    @EventHandler
    public void join(PlayerJoinEvent e){
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->{
            if(HyskiesPunch.SQL.isConnected()){Logger.log(Logger.LogLevel.ERROR, "HyskiesSync is not connected to a database. Cannot create player.");}
            Player p = e.getPlayer();
            HyskiesPunch.data.createPlayer(p);
        }, 30L);
    }
}
