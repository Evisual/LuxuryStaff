package org.equinoxprojects.luxurystaff.permissions;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public enum Permissions
{
    STAFFCHAT("command.staffchat"),
    VIEW_STAFFCHAT("staffchat.view"),
    CONFIG_RELOAD("command.config.reload"),
    VIEW_REPORTS("command.reports.view"),
    VANISH("command.vanish"),
    SEE_VANISHED("see.vanished");

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
