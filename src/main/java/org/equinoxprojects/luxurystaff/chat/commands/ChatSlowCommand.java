package org.equinoxprojects.luxurystaff.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.chat.ChatManager;
import org.equinoxprojects.luxurystaff.commands.SubCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.util.Utils;

public class ChatSlowCommand extends SubCommand
{

    public ChatSlowCommand()
    {
        super("slow");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws NotEnoughArgumentsException, UnknownCommandException, NoPermissionException, MustBePlayerException
    {
        if(!sender.hasPermission(Permissions.SLOW_CHAT.getPermission()))
            throw new NoPermissionException();

        if(args.length != 2)
            throw new NotEnoughArgumentsException();

        if(args[1].equalsIgnoreCase("off"))
        {
            ChatManager.getInstance().setSlow(0);
            String line = Utils.colorize(          "&e&m----------------------------------------");

            Bukkit.broadcastMessage(line);
            Bukkit.broadcastMessage(Utils.colorize("      &eChat slow has been &6&lDISABLED"));
            Bukkit.broadcastMessage(Utils.colorize("        &eby a &6&lServer Administrator"));
            Bukkit.broadcastMessage(line);

            ChatManager.getInstance().getOnCooldown().clear();
            return;
        }

        int num;
        try {
            num = Integer.parseInt(args[1]);
        } catch(NumberFormatException e)
        {
            sender.sendMessage(LuxuryStaff.getCustomConfig().PREFIX + args[1] + Utils.colorize("&c is not a valid number!"));
            return;
        }

        ChatManager.getInstance().setSlow(num);

        String line = Utils.colorize(          "&e&m--------------------------------------");

        Bukkit.broadcastMessage(line);
        Bukkit.broadcastMessage(Utils.colorize(String.format(" &eChat slow has been set to &6&l%s SECONDS", num)));
        Bukkit.broadcastMessage(Utils.colorize(           "     &eby a &6&lServer Administrator"));
        Bukkit.broadcastMessage(line);
    }

    @Override
    public String getUsage() {
        return "/chat slow <time/off>";
    }
}
