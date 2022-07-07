package org.equinoxprojects.luxurystaff.logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.util.Utils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger
{
    private static File file;
    private final String name;

    public Logger(String name)
    {
        this.name = name;
    }

    /**
     * Logs a message to console and then saves the log to log file
     *
     * @param type    Type of log
     * @param message Log message that will be shown
     */

    public void log(LogType type, String message)
    {
        log(type, message, true);
    }

    /**
     * Logs a message to console and then saves the log to log file
     *
     * @param type    Type of log
     * @param message Log message that will be shown
     * @param save    Whether or not to save log to file
     */
    public void log(LogType type, String message, boolean save)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        final String logMessage = Utils.colorize("&7[&b" + name + "&7] " + "[" + type.getColor() + type.getType() + "&7] " + message);
        final String saveMessage = Utils.colorize("&7[&b" + name + "&7] " + "[" + type.getColor() + type.getType() + "&7 " + now + "] " + message);

        Bukkit.getConsoleSender().sendMessage(logMessage);
        if (save)
            saveLog(saveMessage);
    }

    /**
     * Logs a message to console ONLY when debug mode is enabled in the Config
     *
     * @param message Log message that will be shown
     */
    public void debug(String message)
    {
        if(!LuxuryStaff.getCustomConfig().DEBUG_MODE) return;

        String finalMessage = Utils.colorize("&7[&b" + name + "&7] " + "&7[&3DEBUG&7] " + message);

        Bukkit.getConsoleSender().sendMessage(finalMessage);
    }

    private static void saveLog(String saveMessage)
    {
        try
        {
            Writer output;
            output = new BufferedWriter(new FileWriter(file, true));  //clears file every time
            output.append(saveMessage).append("\n");
            output.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void createFile(JavaPlugin plugin)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");
        LocalDateTime now = LocalDateTime.now();

        file = new File(plugin.getDataFolder() + "/logs", dtf.format(now) + ".log");
        if (!file.exists())
        {
            log(LogType.INFO, "No log file found, creating one now.", false);
            file.getParentFile().mkdirs();
        }
    }
}