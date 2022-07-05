package org.equinoxprojects.luxurystaff.commands;

import java.util.ArrayList;
import java.util.Map;

public abstract class LuxuryCommand implements ILuxuryCommand
{

    protected final ArrayList<ISubCommand> subCommands = new ArrayList<>();

    protected final String name;

    protected LuxuryCommand(final String name) { this.name = name; }

    public void addSubCommand(ISubCommand command) { subCommands.add(command); }

    public String getName() { return name; }

    public Map<String, String> getUsageStrings() { return null; }

    public ISubCommand getSubCommand(String subCommand)
    {
        for(ISubCommand command : subCommands)
        {
            if(command.getName().equalsIgnoreCase(subCommand))
                return command;
        }

        return null;
    }
}
