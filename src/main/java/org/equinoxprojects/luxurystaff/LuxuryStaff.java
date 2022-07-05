package org.equinoxprojects.luxurystaff;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.logger.LogType;
import org.equinoxprojects.luxurystaff.logger.Logger;
import org.equinoxprojects.luxurystaff.util.Utils;

public class LuxuryStaff extends JavaPlugin
{
    private final String version = "V0.1.0";
    private final Logger logger = new Logger("LuxuryStaff");

    @Override
    public void onEnable()
    {
        enableMessage();
        logger.createFile(this);
        logger.debug("Test Debug");
        logger.log(LogType.INFO, "Test log", false);
    }

    public void enableMessage()
    {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();

        sender.sendMessage(Utils.colorize("&7------------ &bLuxury Staff &7------------"));
        sender.sendMessage(Utils.colorize(String.format("&7Version: &a%s", version)));
        sender.sendMessage(Utils.colorize("&7Status: &aOK")); //TODO: Check status of plugin
        sender.sendMessage(Utils.colorize("&7--------------------------------------"));
    }


}
