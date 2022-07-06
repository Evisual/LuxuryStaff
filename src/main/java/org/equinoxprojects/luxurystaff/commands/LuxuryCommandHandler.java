package org.equinoxprojects.luxurystaff.commands;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class LuxuryCommandHandler
{
    private final JavaPlugin plugin;
    private ArrayList<ILuxuryCommand> commands = new ArrayList<>();

    public LuxuryCommandHandler(final JavaPlugin mainclass) { this.plugin = mainclass;}

    public void registerCommand(ILuxuryCommand command) { commands.add(command); }

    public ILuxuryCommand findCommand(String name)
    {
        for(ILuxuryCommand command : commands)
        {
            if(command.getName().equalsIgnoreCase(name))
                return command;

            for(String s : command.getAliases())
            {
                if(s.equalsIgnoreCase(name))
                    return command;
            }
        }

        return null;
    }
}
