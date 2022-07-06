package org.equinoxprojects.luxurystaff.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.chat.StaffChat;
import org.equinoxprojects.luxurystaff.commands.LuxuryCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.permissions.Permissions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StaffChatCommand extends LuxuryCommand {
    public StaffChatCommand()
    {
        super("staffchat");
    }

    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws UnknownCommandException, NotEnoughArgumentsException, NoPermissionException, MustBePlayerException {

        if(!(sender instanceof Player))
            throw new MustBePlayerException();

        if(!sender.hasPermission(Permissions.STAFFCHAT.getPermission()))
            throw new NoPermissionException();

        Player p = (Player) sender;

        if(StaffChat.getInstance().hasStaffChatEnabled(p))
        {
            StaffChat.getInstance().removeStaffChat(p);
            p.sendMessage(Messages.STAFF_CHAT_DISABLED.getMessage());
        } else
        {
            StaffChat.getInstance().addStaffChat(p);
            p.sendMessage(Messages.STAFF_CHAT_ENABLED.getMessage());
        }
    }

    @Override
    public String getUsage() {
        return "/staffchat";
    }

    @Override
    public List<String> getAliases()
    {
        return Collections.singletonList("sc");
    }

    @Override
    public boolean noSubCommands() {
        return true;
    }
}
