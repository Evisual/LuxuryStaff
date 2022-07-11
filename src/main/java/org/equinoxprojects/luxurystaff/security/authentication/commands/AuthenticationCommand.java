package org.equinoxprojects.luxurystaff.security.authentication.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.commands.LuxuryCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.files.FileManager;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.player.LuxuryPlayer;
import org.equinoxprojects.luxurystaff.player.PlayerManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AuthenticationCommand extends LuxuryCommand
{

    public AuthenticationCommand()
    {
        super("authenticate");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws UnknownCommandException, NotEnoughArgumentsException, NoPermissionException, MustBePlayerException
    {
        if(!(sender instanceof Player))
            throw new MustBePlayerException();

        if(!sender.hasPermission(Permissions.NEED_AUTHENTICATION.getPermission()))
            throw new NoPermissionException();

        Player p = (Player) sender;

        LuxuryPlayer pl = PlayerManager.getInstance().getPlayerById(p.getUniqueId());

        if(!pl.needsAuthentication()) return;

        if(pl.needsSetupPassword())
        {
            if(args.length != 2)
                throw new NotEnoughArgumentsException();
            if(!args[0].equals(args[1]))
            {
                p.sendMessage(Messages.PASSWORDS_DONT_MATCH.getMessage());
            } else
            {
                pl.setNeedsSetupPassword(false);
                pl.setAuthentication(false);
                pl.setPassword(args[0]);
                p.sendMessage(Messages.LOGGED_IN.getMessage());
                FileConfiguration config = FileManager.getInstance().getLogins().getConfig();
                config.set(p.getUniqueId() + ".login", args[0]);
                FileManager.getInstance().getLogins().save();
            }
        } else
        {
            if(args.length != 1)
                throw new NotEnoughArgumentsException();
            if(args[0].equals(pl.getPassword()))
            {
                pl.setAuthentication(false);
                p.sendMessage(Messages.LOGGED_IN.getMessage());
            } else
                p.sendMessage(Messages.INCORRECT_PASSWORD.getMessage());

        }
    }

    @Override
    public String getUsage() {
        return "/authenticate";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("auth");
    }

    @Override
    public boolean noSubCommands() {
        return true;
    }

    @Override
    public String getPermission() {
        return Permissions.NEED_AUTHENTICATION.getPermission();
    }
}
