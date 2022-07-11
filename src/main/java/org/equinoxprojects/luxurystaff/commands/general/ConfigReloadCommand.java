package org.equinoxprojects.luxurystaff.commands.general;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.commands.LuxuryCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.files.FileManager;
import org.equinoxprojects.luxurystaff.logger.LogType;
import org.equinoxprojects.luxurystaff.permissions.Permissions;

import java.util.ArrayList;
import java.util.List;

public class ConfigReloadCommand extends LuxuryCommand
{
    public ConfigReloadCommand()
    {
        super("config");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws UnknownCommandException, NotEnoughArgumentsException, NoPermissionException {
        if(!(sender.hasPermission(Permissions.CONFIG_RELOAD.getPermission())))
            throw new NoPermissionException();

        if(args.length == 0)
            throw new NotEnoughArgumentsException();

        if(!args[0].equalsIgnoreCase("reload"))
            throw new UnknownCommandException();

        if(!FileManager.getInstance().getConfig().reload() || !FileManager.getInstance().getMessagesConfig().reload())
        {
            logger.log(LogType.ERROR, "Failed to reload files...");
            sender.sendMessage(Messages.RELOADED_FILES_FAILED.getMessage());
            return;
        }

        FileManager.getInstance().reloadFiles();
        LuxuryStaff.reloadCustomConfig();
        Messages.reload();
        Permissions.reload();
        sender.sendMessage(Messages.RELOADED_FILES.getMessage());
    }

    @Override
    public String getUsage() {
        return "/config reload";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public boolean noSubCommands() {
        return true;
    }

    @Override
    public String getPermission() {
        return Permissions.CONFIG_RELOAD.getPermission();
    }
}
