package org.equinoxprojects.luxurystaff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.chat.commands.StaffChatCommand;
import org.equinoxprojects.luxurystaff.chat.listeners.ChatListener;
import org.equinoxprojects.luxurystaff.commands.ILuxuryCommand;
import org.equinoxprojects.luxurystaff.commands.ISubCommand;
import org.equinoxprojects.luxurystaff.commands.LuxuryCommandHandler;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.files.FileManager;
import org.equinoxprojects.luxurystaff.logger.Logger;
import org.equinoxprojects.luxurystaff.logger.util.Utils;

public class LuxuryStaff extends JavaPlugin
{
    private final String VERSION = "V0.1.0";
    private final LuxuryCommandHandler HANDLER = new LuxuryCommandHandler(this);
    private final Logger LOGGER = new Logger("LuxuryStaff");

    @Override
    public void onEnable()
    {
        enableMessage();
        LOGGER.createFile(this);

        registerCommands();
        registerListeners();
        FileManager.getInstance().loadFiles(this);
    }

    public void registerCommands()
    {
        HANDLER.registerCommand(new StaffChatCommand());
    }

    public void registerListeners()
    {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }
    public void enableMessage()
    {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();

        sender.sendMessage(Utils.colorize("&7------------ &bLuxury Staff &7------------"));
        sender.sendMessage(Utils.colorize(String.format("&7Version: &a%s", VERSION)));
        sender.sendMessage(Utils.colorize("&7Status: &aOK")); //TODO: Check status of plugin
        sender.sendMessage(Utils.colorize("&7--------------------------------------"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        String name = cmd.getName();
  // /staffchat
        ILuxuryCommand command = HANDLER.findCommand(name);

        if(command == null)
        {
            sender.sendMessage(Utils.colorize("&cUnknown command. Type &e/help &cfor help"));
            return true;
        }

        if(args.length == 0 || command.noSubCommands())
        {
            try {
                command.execute(sender, cmd, label, args, this);
            } catch(UnknownCommandException e) {
                sender.sendMessage(Utils.colorize("&cUnknown command. Type &e/help &cfor help"));
            } catch(NotEnoughArgumentsException e) {
                sender.sendMessage(Utils.colorize("&cNot enough arguments. Usage: " + command.getUsage()));
            } catch(NoPermissionException e) {
                sender.sendMessage(Utils.colorize("&cNo permission."));
            } catch(MustBePlayerException e) {
                sender.sendMessage(Utils.colorize("&cYou must be a player to run this command!"));
            }

            return true;
        }

        ISubCommand subCommand = command.getSubCommand(args[0]);

        if(subCommand == null)
        {
            sender.sendMessage(Utils.colorize("&cUnknown command. Type &e/" + cmd.getName() + " help &cfor help"));
            return true;
        }

        try {
            subCommand.execute(sender, cmd, label, args, this);
        } catch(NotEnoughArgumentsException e) {
            sender.sendMessage(Utils.colorize("&cNot enough arguments. Usage: " + subCommand.getUsage()));
        } catch(UnknownCommandException e) {
            sender.sendMessage(Utils.colorize("&cUnknown command. Type &e/" + cmd.getName() + " help &cfor help"));
        } catch(NoPermissionException e) {
            sender.sendMessage(Utils.colorize("&cNo permission."));
        } catch(MustBePlayerException e) {
            sender.sendMessage(Utils.colorize("&cYou must be a player to run this command!"));
        }

        return true;
    }
}
