package me.caledonian.hyskiespunch.managers;

import me.caledonian.hyskiespunch.commands.Debug;
import me.caledonian.hyskiespunch.commands.HyskiesPunch;
import me.caledonian.hyskiespunch.commands.Punch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements CommandExecutor {
    private Map<String, CommandHandler> commands = new HashMap<>();
    private JavaPlugin javaPlugin;
    public CommandManager(JavaPlugin javaPlugin){
        this.javaPlugin = javaPlugin;
        initCommands();
    }
    private void initCommands(){
        // Core
        commands.put("hyskiespunch", new HyskiesPunch(javaPlugin));
        commands.put("punch", new Punch(javaPlugin));
        commands.put("hdebug", new Debug());

        registerCommands();
    }
    private void registerCommands() { commands.forEach((s, c) -> javaPlugin.getCommand(s).setExecutor(this));}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmdname = command.getName();
        CommandHandler commandHandler = commands.get(cmdname);
        if(commandHandler != null) commandHandler.execute(sender, command,args);
        return false;
    }
}
