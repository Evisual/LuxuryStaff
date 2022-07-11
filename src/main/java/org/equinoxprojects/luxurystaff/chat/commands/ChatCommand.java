package org.equinoxprojects.luxurystaff.chat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.commands.LuxuryCommand;
import org.equinoxprojects.luxurystaff.commands.exceptions.MustBePlayerException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NoPermissionException;
import org.equinoxprojects.luxurystaff.commands.exceptions.NotEnoughArgumentsException;
import org.equinoxprojects.luxurystaff.commands.exceptions.UnknownCommandException;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChatCommand extends LuxuryCommand
{
    public ChatCommand()
    {
        super("chat");
        this.addSubCommand(new ChatToggleCommand());
        this.addSubCommand(new ChatClearCommand());
        this.addSubCommand(new ChatSlowCommand());
    }


    @Override
    public void execute(CommandSender sender, Command cmd, String label, String[] args, JavaPlugin mainclass) throws UnknownCommandException, NotEnoughArgumentsException, NoPermissionException, MustBePlayerException
    {
        if(!sender.hasPermission(Permissions.CHAT_HELP.getPermission()))
            throw new NoPermissionException();

        String line = Utils.colorize("&7&m---------------------------------");
        sender.sendMessage(line);
        sender.sendMessage(Utils.colorize("&b/chat clear &f&l- &7Clear chat"));
        sender.sendMessage(Utils.colorize("&b/chat toggle &f&l- &7Toggle chat on/off"));
        sender.sendMessage(Utils.colorize("&b/chat slow <time/off> &f&l- &7Set the chat delay"));
        sender.sendMessage(line);
    }

    @Override
    public String getUsage() {
        return "/chat";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public boolean noSubCommands() {
        return false;
    }

    @Override
    public String getPermission() {
        return Permissions.CHAT_HELP.getPermission();
    }
}
