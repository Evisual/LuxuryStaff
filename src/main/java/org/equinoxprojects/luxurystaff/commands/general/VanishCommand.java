package org.equinoxprojects.luxurystaff.commands.general;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.commands.LuxuryCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.player.LuxuryPlayer;
import org.equinoxprojects.luxurystaff.player.PlayerManager;

import java.util.Collections;
import java.util.List;

public class VanishCommand extends LuxuryCommand
{

    public VanishCommand()
    {
        super("vanish");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws UnknownCommandException, NotEnoughArgumentsException, NoPermissionException, MustBePlayerException
    {
        if(!(sender instanceof Player))
            throw new MustBePlayerException();

        Player p = (Player) sender;

        if(!p.hasPermission(Permissions.VANISH.getPermission()))
            throw new NoPermissionException();

        LuxuryPlayer pl = PlayerManager.getInstance().getPlayerById(p.getUniqueId());

        if(pl.isVanished())
        {
            pl.unvanish();
            p.sendMessage(Messages.UNVANISHED.getMessage());
        } else
        {
            pl.vanish();
            p.sendMessage(Messages.VANISHED.getMessage());
        }
    }

    @Override
    public String getUsage() {
        return "/vanish";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("v");
    }

    @Override
    public boolean noSubCommands() {
        return true;
    }
}
