package org.equinoxprojects.luxurystaff.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.chat.ChatManager;
import org.equinoxprojects.luxurystaff.commands.SubCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.util.Utils;

public class ChatToggleCommand extends SubCommand
{

    public ChatToggleCommand()
    {
        super("toggle");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws NotEnoughArgumentsException, UnknownCommandException, NoPermissionException, MustBePlayerException
    {
        if(!sender.hasPermission(Permissions.TOGGLE_CHAT.getPermission()))
            throw new NoPermissionException();

        if(ChatManager.getInstance().isDisabled())
        {
            ChatManager.getInstance().setDisabled(false);
            sender.sendMessage(Messages.ENABLED_CHAT.getMessage());

            String line = Utils.colorize(          "&e&m--------------------------------------");

            Bukkit.broadcastMessage(line);
            Bukkit.broadcastMessage(Utils.colorize("      &eChat has been &6&lENABLED"));
            Bukkit.broadcastMessage(Utils.colorize("     &eby a &6&lServer Administrator"));
            Bukkit.broadcastMessage(line);
        } else
        {
            ChatManager.getInstance().setDisabled(true);
            sender.sendMessage(Messages.DISABLED_CHAT.getMessage());

            String line = Utils.colorize(          "&e&m--------------------------------------");

            Bukkit.broadcastMessage(line);
            Bukkit.broadcastMessage(Utils.colorize("      &eChat has been &6&lDISABLED"));
            Bukkit.broadcastMessage(Utils.colorize("     &eby a &6&lServer Administrator"));
            Bukkit.broadcastMessage(line);
        }
    }

    @Override
    public String getUsage() {
        return "/chat toggle";
    }
}
