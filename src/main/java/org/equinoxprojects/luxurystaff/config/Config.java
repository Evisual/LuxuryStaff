package org.equinoxprojects.luxurystaff.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.equinoxprojects.luxurystaff.files.ConfigFile;
import org.equinoxprojects.luxurystaff.files.FileManager;

public class Config
{
    private final ConfigFile configFile;
    private final FileConfiguration config;
    public boolean DEBUG_MODE = false;
    private final String PS_DEBUG_MODE = "debug-mode";
    public String PREFIX = net.md_5.bungee.api.ChatColor.of("#23deac") + "" + ChatColor.BOLD + "Staff " + ChatColor.GRAY + "» ";
    private final String PS_PREFIX = "prefix";
    public boolean ENABLE_STAFF_CHAT_ON_JOIN = true;
    private final String PS_ENABLE_STAFF_CHAT_ON_JOIN = "enable-staff-chat-on-join";
    public boolean NOTIFY_REPORTED_PLAYER = false;
    private final String PS_NOTIFY_REPORTED_PLAYER = "notify-reported-player";
    public boolean ENABLE_VANISH_ON_JOIN = true;
    private final String PS_ENABLE_VANISH_ON_JOIN = "enable-vanish-on-join";
    public boolean REQUIRE_AUTHENTICATION = true;
    private final String PS_REQUIRE_AUTHENTICATION = "require-authentication";

    public Config()
    {
         configFile = FileManager.getInstance().getConfig();
         config = configFile.getConfig();

        readValues();
        writeValues();
    }
    public void readValues()
    {
        configFile.reload();

        if(config.contains(PS_DEBUG_MODE))                DEBUG_MODE                = config.getBoolean(PS_DEBUG_MODE);
        if(config.contains(PS_PREFIX))                    PREFIX                    = config.getString(PS_PREFIX);
        if(config.contains(PS_ENABLE_STAFF_CHAT_ON_JOIN)) ENABLE_STAFF_CHAT_ON_JOIN = config.getBoolean(PS_ENABLE_STAFF_CHAT_ON_JOIN);
        if(config.contains(PS_NOTIFY_REPORTED_PLAYER))    NOTIFY_REPORTED_PLAYER    = config.getBoolean(PS_NOTIFY_REPORTED_PLAYER);
        if(config.contains(PS_ENABLE_VANISH_ON_JOIN))     ENABLE_VANISH_ON_JOIN     = config.getBoolean(PS_ENABLE_VANISH_ON_JOIN);
        if(config.contains(PS_REQUIRE_AUTHENTICATION))    REQUIRE_AUTHENTICATION    = config.getBoolean(PS_REQUIRE_AUTHENTICATION);
    }

    public void writeValues()
    {
        config.set(PS_DEBUG_MODE, DEBUG_MODE);
        config.set(PS_PREFIX, PREFIX);
        config.set(PS_ENABLE_STAFF_CHAT_ON_JOIN, ENABLE_STAFF_CHAT_ON_JOIN);
        config.set(PS_NOTIFY_REPORTED_PLAYER, NOTIFY_REPORTED_PLAYER);
        config.set(PS_ENABLE_VANISH_ON_JOIN, ENABLE_VANISH_ON_JOIN);
        config.set(PS_REQUIRE_AUTHENTICATION, REQUIRE_AUTHENTICATION);

        configFile.save();
    }
}
