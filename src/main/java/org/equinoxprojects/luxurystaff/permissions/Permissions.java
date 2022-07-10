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
    SEE_VANISHED("see.vanished"),
    NEED_AUTHENTICATION("authentication"),
    CHAT_HELP("command.chat"),
    TOGGLE_CHAT("command.chat.toggle"),
    IGNORE_CHAT_DISABLED("chat.disabled.ignore"),
    IGNORE_CHAT_SLOWED("chat.slowed.ignore"),
    SLOW_CHAT("command.chat.slow"),
    CLEAR_CHAT("command.chat.clear"),
    CLEAR_CHAT_BYPASS("command.chat.clear.bypass");

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
