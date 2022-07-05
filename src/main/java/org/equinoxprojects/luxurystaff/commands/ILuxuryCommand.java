package org.equinoxprojects.luxurystaff.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;

import java.util.Map;

public interface ILuxuryCommand
{
    void execute(final CommandSender sender, final Command cmd, final String label, final String[] args, final JavaPlugin mainclass) throws UnknownCommandException, NotEnoughArgumentsException, NoPermissionException, MustBePlayerException;

    String getName();

    Map<String, String> getUsageStrings();

    String getUsage();

    ISubCommand getSubCommand(String subCommand);

    boolean noSubCommands();
}
