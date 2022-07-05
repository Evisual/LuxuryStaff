package org.equinoxprojects.luxurystaff.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;

import java.util.Map;

public interface ISubCommand
{

    void execute(final CommandSender sender, final Command cmd, final String label, final String[] args, final JavaPlugin mainclass) throws NotEnoughArgumentsException, UnknownCommandException, NoPermissionException, MustBePlayerException;

    String getName();

    Map<String, String> getUsageStrings();

    String getUsage();

}
