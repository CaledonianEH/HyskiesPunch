package me.caledonian.hyskiespunch.utils;

import me.caledonian.hyskiespunch.HyskiesPunch;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Files {

    public static File configFile;
    public static FileConfiguration config;

    public static File msgsFile;
    public static FileConfiguration msgs;

    public static void base(HyskiesPunch m) {
        if (!m.getDataFolder().exists()) {
            m.getDataFolder().mkdirs();
        }
        // config.yml
        configFile = new File(m.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            //Logger.log(Logger.LogLevel.INFO, "File config.yml doesn't exist, creating one...");
            m.saveResource("config.yml", false);
            //Logger.log(Logger.LogLevel.SUCCESS, "File config.yml was created.");
        }
        // msgs.yml
        msgsFile = new File(m.getDataFolder(), "msgs.yml");
        if (!msgsFile.exists()) {
            //Logger.log(Logger.LogLevel.INFO, "File msgs.yml doesn't exist, creating one...");
            m.saveResource("msgs.yml", false);
            //Logger.log(Logger.LogLevel.SUCCESS, "File msgs.yml was created.");
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        //Logger.log(Logger.LogLevel.SUCCESS, "File config.yml was loaded");
        msgs = YamlConfiguration.loadConfiguration(msgsFile);
        //Logger.log(Logger.LogLevel.SUCCESS, "File msgs.yml was loaded");
    }
    public static void reloadConfig() {config = YamlConfiguration.loadConfiguration(configFile);}
    public static void reloadMsgs() {msgs = YamlConfiguration.loadConfiguration(msgsFile);}

    public static void saveConfig() throws IOException {config.save(configFile);}
    public static void saveMsgs() throws IOException {msgs.save(msgsFile);}
}
