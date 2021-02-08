package me.caledonian.hyskiespunch.listener;

import me.caledonian.hyskiespunch.HyskiesPunch;
import me.caledonian.hyskiespunch.managers.UpdateManager;
import me.caledonian.hyskiespunch.utils.Files;
import me.caledonian.hyskiespunch.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DevInfo implements Listener {
    private HyskiesPunch plugin;
    public DevInfo(HyskiesPunch plugin){this.plugin = plugin;}

    @EventHandler
    public void dev(PlayerJoinEvent e){
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->{
            if(p.getUniqueId().toString().equals("91de2098-0f3e-483b-9971-b0ead5141bde") || p.getUniqueId().toString().equals("5ebe0f69-5348-4a48-a7fc-333c672aad96")){
                p.sendMessage(Utils.chat("&b*&8&m-----------&b*&8&m------------------&b*&8&m-----------&b*"));
                p.sendMessage(Utils.chat("&f "));
                p.sendMessage(Utils.chat("&b&l * &fWelcome &b" + p.getName() + "&f!"));
                p.sendMessage(Utils.chat("&b&l * &fThis server is currently running HyskiesPunch &bv" + plugin.getDescription().getVersion() + "&f."));
                p.sendMessage(Utils.chat("&b&l * &fPlugin Name: &b" + plugin.getDescription().getName() + "&f."));
                p.sendMessage(Utils.chat("&b&l * &fAuthor: &b" + plugin.getDescription().getAuthors() + "&7."));
                p.sendMessage(Utils.chat("&f "));
                p.sendMessage(Utils.chat("&7&o(( Your seeing this message due to being a &f&odeveloper &7&o))"));
                p.sendMessage(Utils.chat("&b*&8&m-----------&b*&8&m------------------&b*&8&m-----------&b*"));
            }
        }, 30L);
    }

    // Update Checker
    @EventHandler
    public void update(PlayerJoinEvent e){
        String update = Utils.chat("&f&obit.ly/3q0RYaY");
        Player p = e.getPlayer();

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->{
            if(p.hasPermission(Files.config.getString("perms.punch-admin")) && Files.config.getBoolean("update-checker")){
                new UpdateManager(HyskiesPunch.getPlugin(), Utils.id).getLatestVersion(version ->{
                  if(!Utils.ver.equalsIgnoreCase(version)){
                      p.sendMessage(Utils.chat("&b*&8&m-----------&b*&8&m------------------&b*&8&m-----------&b*"));
                      p.sendMessage(Utils.chat("&f      Hyskies &bPunch &fis outdated!"));
                      p.sendMessage(Utils.chat("&f "));
                      p.sendMessage(Utils.chat("&f * &bCurrent Version&8: &f%version%").replace("%version%", Utils.ver));
                      p.sendMessage(Utils.chat("&f * &bLatest Version&8: &f%version%").replace("%version%", version));
                      p.sendMessage(Utils.chat("&f "));
                      p.sendMessage(Utils.chat("&7&o(( Update at %link% &7&o))").replace("%link%", update));
                      p.sendMessage(Utils.chat("&b*&8&m-----------&b*&8&m------------------&b*&8&m-----------&b*"));
                  }
                });
            }
        }, 40L);
    }
}
