package me.caledonian.hyskiespunch;

import me.caledonian.hyskiespunch.data.MySQL;
import me.caledonian.hyskiespunch.data.SQLUtils;
import me.caledonian.hyskiespunch.listener.DevInfo;
import me.caledonian.hyskiespunch.listener.HitEvent;
import me.caledonian.hyskiespunch.listener.SQLJoin;
import me.caledonian.hyskiespunch.managers.CommandManager;
import me.caledonian.hyskiespunch.managers.PlaceholderHandler;
import me.caledonian.hyskiespunch.utils.Files;
import me.caledonian.hyskiespunch.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class HyskiesPunch extends JavaPlugin {

    public static HyskiesPunch plugin;
    public static MySQL SQL;
    public static SQLUtils data;

    @Override
    public void onEnable() {

        Files.base(this);
        // Registering
        if(Files.config.getBoolean("mysql.enable") == false){
            Logger.log(Logger.LogLevel.WARNING, "You must be connected to SQL for the plugin to enable. TIP: Make sure &6enable: &fis set to true.");
            //Bukkit.getConsoleSender().sendMessage(Utils.chat("&8(&6&lWARNING&8) &fYou must be connected to SQL for the plugin to enable. TIP: Make sure enable: is set to true."));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }else{
            if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null && getServer().getPluginManager().getPlugin("PlaceholderAPI").isEnabled()){
                Logger.log(Logger.LogLevel.INFO, "Found PlaceholderAPI. Attempting to register placeholders...");
                new PlaceholderHandler().register();
            }else{Logger.log(Logger.LogLevel.WARNING, "Could not find the plugin PlaceholderAPI. External placeholders will be disabled.");}

            SQL = new MySQL();
            data = new SQLUtils(this);

            // Event registering


            Logger.log(Logger.LogLevel.INFO, "HyskiesPunch will now try to connect to SQL");
            try{
                SQL.connect();
            }catch (ClassNotFoundException | SQLException e){
                Logger.log(Logger.LogLevel.ERROR, "Could not connect to the database. Enable debug to see the stack trace.");
                if(Files.config.getBoolean("mysql.debug")){e.printStackTrace();}
            }

            if(SQL.isConnected()){
                Logger.log(Logger.LogLevel.SUCCESS, "HyskiesPunch connected to SQL. Creating tables");
                data.createTable();
            }

            new CommandManager(this);
            Logger.log(Logger.LogLevel.INFO, "Registering all events");
            Bukkit.getServer().getPluginManager().registerEvents(new SQLJoin(this), this);
            Bukkit.getServer().getPluginManager().registerEvents(new HitEvent(this), this);
            Bukkit.getServer().getPluginManager().registerEvents(new DevInfo(this), this);

            Logger.log(Logger.LogLevel.SUCCESS, "Hyskies Punch is fully loaded.");
        }
    }

    @Override
    public void onDisable() {
        Logger.log(Logger.LogLevel.INFO, "HyskiesPunch is starting to shut down.");
        if(Files.config.getBoolean("mysql.enable") == true){
            if(SQL.isConnected()){SQL.disconnect();}

        }
    }

    public static HyskiesPunch getPlugin(){return plugin;}
}
