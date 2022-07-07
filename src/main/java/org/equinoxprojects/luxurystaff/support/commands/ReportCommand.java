package org.equinoxprojects.luxurystaff.support.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.commands.LuxuryCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.support.ReportHandler;
import org.equinoxprojects.luxurystaff.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportCommand extends LuxuryCommand
{

    public ReportCommand()
    {
        super("report");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws UnknownCommandException, NotEnoughArgumentsException, NoPermissionException, MustBePlayerException
    {
        if(args.length < 2)
            throw new NotEnoughArgumentsException();

        if(!(sender instanceof Player))
            throw new MustBePlayerException();

        Player p = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if(target == null)
        {
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%player%", args[0]);
            sender.sendMessage(Messages.NOT_A_PLAYER.getMessage(placeholders));
            return;
        }

        StringBuilder reason = new StringBuilder();

        for(String word : args)
        {
            if(word.equals(args[0])) // If it's the first element, continue because that's the player name
                continue;

            reason.append(word);

            if(!word.equals(args[args.length-1])) // If it's the last element, don't add a space
                reason.append(" ");
        }

        ReportHandler handler = new ReportHandler();
        handler.addReport(target.getUniqueId(), p.getUniqueId(), reason.toString());

        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("%reason%", reason.toString());
        if(LuxuryStaff.getCustomConfig().NOTIFY_REPORTED_PLAYER)
        {
            placeholders.put("%player%", p.getName());
            target.sendMessage(Messages.YOU_WERE_REPORTED.getMessage(placeholders));
        }

        placeholders.put("%player%", target.getName());
        p.sendMessage(Messages.SUCCESSFULLY_REPORTED_PLAYER.getMessage(placeholders));
    }

    @Override
    public String getUsage() {
        return "/report <player> <reason>";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public boolean noSubCommands() {
        return true;
    }
}
