package me.caledonian.hyskiespunch.listener;

import me.caledonian.hyskiespunch.HyskiesPunch;
import me.caledonian.hyskiespunch.utils.Files;
import me.caledonian.hyskiespunch.utils.Logger;
import me.caledonian.hyskiespunch.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class HitEvent implements Listener {
    private JavaPlugin plugin;
    public HitEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    HashMap<String, Long> cd = new HashMap<String, Long>();

    String prefix = Utils.chat(Utils.prefix);


    @EventHandler
    public void Hit(EntityDamageByEntityEvent e){
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Entity te = e.getEntity();


            Player t = (Player) Bukkit.getPlayer(te.getName());
            Entity pe = e.getDamager();
            Player p = (Player) Bukkit.getPlayer(pe.getName());

            if(HyskiesPunch.SQL.isConnected()){
                if (cd.containsKey(p.getName())) {
                    long oldTime = cd.get(p.getName());
                    long newTime = System.currentTimeMillis();
                    if ((newTime - oldTime) > (Files.config.getInt("punch.cooldown") * 1000)) {
                        cd.remove(p.getName());
                        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->{
                            punch(p, t);
                        }, 1L);
                    }else{
                        int time = (int) (newTime - oldTime);
                        int ti = Files.config.getInt("punch.cooldown") - time / 1000;
                        p.sendMessage(Utils.chat(Files.msgs.getString("core.cooldown").replace("%prefix%", prefix).replace("%time%", String.valueOf(ti))));
                    }
                } else {
                    // Starting cooldown
                    cd.put(p.getName(), System.currentTimeMillis());
                    // Punch logic
                    Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->{
                        punch(p, t);
                    }, 1L);
                }
            }else{Logger.log(Logger.LogLevel.ERROR, "HyskiesSync is not connected to a database. Cannot execute punch.");}
        });
    }

    @EventHandler
    public void FallDamage(EntityDamageEvent e){
           if(Files.config.getBoolean("punch.disable-fall-damage")){
               if(e.getEntity().getType().equals(EntityType.PLAYER)){
                   if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
                       e.setCancelled(true);
                   }
               }
           }
    }

    private static void punch(Player p, Player t){
        if (HyskiesPunch.data.canBePunched(t) == 1) {
            if (HyskiesPunch.data.canPunch(p) == 1) {

                Utils.punchPlayer(t, p, "hit");
                //Utils.punchPlayer(t, p);
            } else {
                p.sendMessage(Utils.chat(Files.msgs.getString("punch.user-disabled").replace("%prefix%", Utils.chat(Utils.prefix)).replace("%target%", t.getName())));
            }
        } else {
            p.sendMessage(Utils.chat(Files.msgs.getString("punch.player-disabled").replace("%prefix%", Utils.chat(Utils.prefix)).replace("%target%", t.getName())));
            t.sendMessage(Utils.chat(Files.msgs.getString("punch.tried-to-punch").replace("%prefix%", Utils.chat(Utils.prefix)).replace("%player%", t.getName())));
        }
    }
}
