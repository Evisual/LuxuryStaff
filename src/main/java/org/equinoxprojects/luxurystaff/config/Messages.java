package org.equinoxprojects.luxurystaff.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.files.ConfigFile;
import org.equinoxprojects.luxurystaff.files.FileManager;
import org.equinoxprojects.luxurystaff.util.Utils;

import java.util.HashMap;

public enum Messages
{
    STAFF_CHAT_ENABLED("staff-chat-enabled", "%prefix% &7Successfully &a&lENABLED &7staff-chat"),
    STAFF_CHAT_DISABLED("staff-chat-disabled", "%prefix% &7Successfully &c&lDISABLED &7staff-chat"),
    RELOADED_FILES("reloaded-files", "%prefix% &a&lSuccessfully &7reloaded files"),
    RELOADED_FILES_FAILED("reloaded-files-failed", "%prefix% &c&lFailed &7to reload files"),
    NOT_A_PLAYER("not-a-player", "&cNo player by the name of &e%player% &cexists."),
    SUCCESSFULLY_REPORTED_PLAYER("successfully-reported-player", "%prefix% &aYou have successfully reported &e%player% &afor &e%reason%"),
    YOU_WERE_REPORTED("you-were-reported", "%prefix% &cYou have been reported by &e%player% &cfor &e%reason%"),
    REPORT_CLOSED("report-closed", "%prefix% &aSuccessfully marked report &e%id% &aas &c&l%status%"),
    VANISHED("vanished", "%prefix% &7Successfully &a&lENABLED &7vanish"),
    UNVANISHED("unvanished", "%prefix% &7Successfully &c&lDISABLED &7vanish"),
    NEED_LOGIN("need-login", "%prefix% &cYou are required to log-in before you can access the server. Please type /auth <pass> with the password you previously set up."),
    NEED_SETUP_PASSWORD("need-setup-password", "%prefix% &cYou haven't set up a password yet! You are required to set one up before accessing the server. Please type /auth <pass> <confirm>"),
    INCORRECT_PASSWORD("incorrect-password", "%prefix% &cIncorrect password! Please try again or contact an administrator"),
    LOGGED_IN("logged-in", "%prefix% &aYou have been successfully logged in!"),
    PASSWORDS_DONT_MATCH("passwords-dont-match", "%prefix% &cThe given passwords don't match!"),
    ENABLED_CHAT("enabled-chat", "%prefix% &7Successfully &a&lENABLED &7chat"),
    DISABLED_CHAT("disabled-chat", "%prefix% &7Successfully &c&lDISABLED &7chat"),
    CHAT_DISABLED("chat-disabled", "%prefix% &cYou cannot talk because chat is currently disabled!"),
    CHAT_SLOWED("chat-slowed", "%prefix% &cYou cannot talk for %time% more seconds because chat is slowed!");

    private final String path;
    private String message;

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

    public static void reload()
    {
        for(Messages message : Messages.values())
        {
            ConfigFile messagesConfig = FileManager.getInstance().getMessagesConfig();
            FileConfiguration config = messagesConfig.getConfig();

            String path = message.path;

            String newMessage = config.getString(path);
            String defaultMessage = message.message;

            if(newMessage == null)
            {
                message.message = defaultMessage.replaceAll("%prefix%", LuxuryStaff.getCustomConfig().PREFIX);
                config.set(path, defaultMessage);
                messagesConfig.save();
            }
            else
                message.message = newMessage.replaceAll("%prefix%", LuxuryStaff.getCustomConfig().PREFIX);
        }
    }
}
