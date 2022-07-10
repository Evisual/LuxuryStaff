package org.equinoxprojects.luxurystaff.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.commands.SubCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.util.Utils;

public class ChatClearCommand extends SubCommand
{

    public ChatClearCommand()
    {
        super("clear");
    }


    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws NotEnoughArgumentsException, UnknownCommandException, NoPermissionException, MustBePlayerException
    {
        if(!sender.hasPermission(Permissions.CLEAR_CHAT.getPermission()))
            throw new NoPermissionException();

        for(Player pl : Bukkit.getOnlinePlayers())
        {
            if(pl.hasPermission(Permissions.CLEAR_CHAT_BYPASS.getPermission()))
                continue;

            for (int i = 0; i < 400; i++)
            {
                pl.sendMessage(" ");
            }
        }

        String line = Utils.colorize(          "&e&m--------------------------------------");

        Bukkit.broadcastMessage(line);
        Bukkit.broadcastMessage(Utils.colorize("      &eChat has been &6&lCLEARED"));
        Bukkit.broadcastMessage(Utils.colorize("     &eby a &6&lServer Administrator"));
        Bukkit.broadcastMessage(line);
    }

    @Override
    public String getUsage() {
        return "/chat clear";
    }
}
