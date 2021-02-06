package me.caledonian.hyskiespunch.commands;

import me.caledonian.hyskiespunch.managers.CommandHandler;
import me.caledonian.hyskiespunch.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Debug implements CommandHandler {

    @Override
    public void execute(CommandSender sender, Command command, String[] args) {
        Player p = (Player) sender;
    }
}
