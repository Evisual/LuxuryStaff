package org.equinoxprojects.luxurystaff.permissions;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public enum Permissions
{
    STAFFCHAT("command.staffchat");

    private final String permission;

    Permissions(String permission)
    {
        this.permission = "staff." + permission;
    }

    public boolean hasPermission(Player p)
    {
        return p.hasPermission(permission);
    }

    public String getPermission()
    {
        return permission;
    }
}