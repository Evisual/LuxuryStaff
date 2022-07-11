package org.equinoxprojects.luxurystaff.permissions;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.files.ConfigFile;
import org.equinoxprojects.luxurystaff.files.FileManager;

public enum Permissions
{
    STAFFCHAT("staffchat", "command.staffchat"),
    VIEW_STAFFCHAT("view-staffchat", "staffchat.view"),
    CONFIG_RELOAD("reload-config", "command.config.reload"),
    VIEW_REPORTS("view-reports", "command.reports.view"),
    VANISH("vanish", "command.vanish"),
    SEE_VANISHED("see-vanished", "see.vanished"),
    NEED_AUTHENTICATION("authentication", "authentication"),
    CHAT_HELP("view-chat-help", "command.chat"),
    TOGGLE_CHAT("toggle-chat", "command.chat.toggle"),
    SLOW_CHAT("slow-chat", "command.chat.slow"),
    CLEAR_CHAT("clear-chat", "command.chat.clear"),
    TOGGLE_CHAT_BYPASS("toggle-chat", "command.chat.toggle.bypass"),
    SLOW_CHAT_BYPASS("slow-chat-bypass", "command.chat.slow.bypass"),
    CLEAR_CHAT_BYPASS("clear-chat-bypass", "command.chat.clear.bypass"),
    SILENT_JOIN("silent-join", "join.silent"),
    SEE_SILENT_JOIN("see-silent-join", "join.silent.see");

    private final String path;
    private String permission;

    Permissions(final String path, String defaultPermission)
    {
        this.path = path;

        load(path, defaultPermission);
    }

    public boolean hasPermission(Player p)
    {
        return p.hasPermission(permission);
    }

    public String getPermission()
    {
        return permission;
    }

    public static void reload()
    {
        for(Permissions permission : Permissions.values())
        {
           permission.load(permission.path, permission.permission);
        }
    }

    private void load(String path, String defaultPermission)
    {
        ConfigFile permissionsConfig = FileManager.getInstance().getPermissionsConfig();
        FileConfiguration config = permissionsConfig.getConfig();

        String newPermission = config.getString(path);

        if(newPermission == null)
        {
            this.permission = "staff." + defaultPermission;
            config.set(path, permission);
            permissionsConfig.save();
        } else
            this.permission = newPermission;
    }
}
