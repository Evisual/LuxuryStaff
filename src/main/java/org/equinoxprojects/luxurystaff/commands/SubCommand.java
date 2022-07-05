package org.equinoxprojects.luxurystaff.commands;

import java.util.Map;

public abstract class SubCommand implements ISubCommand
{
    protected final String name;

    protected SubCommand(final String name) { this.name = name; }

    @Override
    public String getName() { return name; }

    @Override
    public Map<String, String> getUsageStrings() { return null; }
}
