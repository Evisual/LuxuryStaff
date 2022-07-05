package org.equinoxprojects.luxurystaff.logger;

import org.bukkit.ChatColor;

public enum LogType
{
    INFO(ChatColor.GREEN, "INFO"),
    WARN(ChatColor.YELLOW, "WARNING"),
    ERROR(ChatColor.RED, "ERROR"),
    CRITICAL(ChatColor.DARK_RED, "CRITICAL");

    private final ChatColor color;
    private final String name;

    LogType(ChatColor color, String name)
    {
        this.color = color;
        this.name = name;
    }

    public ChatColor getColor() { return color; }

    public String getType() { return name; }
}