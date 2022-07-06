package org.equinoxprojects.luxurystaff.config;

import org.equinoxprojects.luxurystaff.logger.util.Utils;

import java.util.HashMap;

public enum Messages
{
    STAFF_CHAT_ENABLED("staff-chat-enabled", Config.PREFIX + "&7Successfully &a&lENABLED &7staff-chat"),
    STAFF_CHAT_DISABLED("staff-chat-disabled", Config.PREFIX + "&7Successfully &c&lDISABLED &7staff-chat");

    private final String path;
    private final String message;

    Messages(final String path, final String defaultMessage)
    {
        this.path = path;
        this.message = defaultMessage;
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
