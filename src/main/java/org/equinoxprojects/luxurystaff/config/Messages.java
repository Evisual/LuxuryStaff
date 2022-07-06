package org.equinoxprojects.luxurystaff.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.files.ConfigFile;
import org.equinoxprojects.luxurystaff.files.FileManager;
import org.equinoxprojects.luxurystaff.logger.util.Utils;

import java.util.HashMap;

public enum Messages
{
    STAFF_CHAT_ENABLED("staff-chat-enabled", "%prefix% &7Successfully &a&lENABLED &7staff-chat"),
    STAFF_CHAT_DISABLED("staff-chat-disabled", "%prefix% &7Successfully &c&lDISABLED &7staff-chat");

    private final String path;
    private final String message;

    Messages(final String path, final String defaultMessage)
    {
        ConfigFile messagesConfig = FileManager.getInstance().getMessagesConfig();
        FileConfiguration config = messagesConfig.getConfig();

        this.path = path;

        String newMessage = config.getString(path);

        if(newMessage == null)
        {
            this.message = defaultMessage.replaceAll("%prefix%", LuxuryStaff.getCustomConfig().PREFIX);
            config.set(path, defaultMessage);
            messagesConfig.save();
        }
        else
            this.message = newMessage.replaceAll("%prefix%", LuxuryStaff.getCustomConfig().PREFIX);
    }

    public String getMessage()
    {
        return Utils.colorize(message);
    }

    public String getMessage(HashMap<String, String> placeholders)
    {
        String newMessage = Utils.colorize(message);
        for(String s : placeholders.keySet())
        {
            newMessage = newMessage.replaceAll(s, placeholders.get(s));
        }

        return newMessage;
    }

}
