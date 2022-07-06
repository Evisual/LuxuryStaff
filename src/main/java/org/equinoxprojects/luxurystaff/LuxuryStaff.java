package org.equinoxprojects.luxurystaff;

import lombok.Getter;
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
import org.equinoxprojects.luxurystaff.config.Config;
import org.equinoxprojects.luxurystaff.files.FileManager;
import org.equinoxprojects.luxurystaff.logger.Logger;
import org.equinoxprojects.luxurystaff.logger.util.Utils;

public class LuxuryStaff extends JavaPlugin
{
    private final String version = "V0.1.0";
    private final LuxuryCommandHandler handler = new LuxuryCommandHandler(this);
    private final Logger logger = new Logger("LuxuryStaff");
    private static Config CONFIG; //TODO: Reload this when config reloaded
    private LoadStatus status = LoadStatus.OK;

    public static Config getCustomConfig() { return CONFIG; }

    @Override
    public void onEnable()
    {
        logger.createFile(this);

        if(!FileManager.getInstance().loadFiles(this))
        {
            status = LoadStatus.FAILED;
            enableMessage("Unable to create and/or load files. Please try again or contact support.");
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        CONFIG = new Config();

        registerCommands();
        registerListeners();

        enableMessage(null);
    }

    public void registerCommands()
    {
        handler.registerCommand(new StaffChatCommand());
    }

    public void registerListeners()
    {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }
    public void enableMessage(String failedMessage)
    {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();

        sender.sendMessage(Utils.colorize("&7------------ &bLuxury Staff &7------------"));
        sender.sendMessage(Utils.colorize(String.format("&7Version: &a%s", version)));
        sender.sendMessage(Utils.colorize("&7Status: " + status.getMessage()));
        sender.sendMessage(Utils.colorize("&7--------------------------------------"));

        if(status != LoadStatus.OK)
            sender.sendMessage(Utils.colorize("&c" + failedMessage));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        String name = cmd.getName();

        ILuxuryCommand command = handler.findCommand(name);

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

enum LoadStatus
{
    OK("&aOK"),
    NEED_REBOOT("&cNEED REBOOT"),
    FAILED("&cFAILED");

    private @Getter final String message;

    LoadStatus(final String message)
    {
        this.message = message;
    }
}
