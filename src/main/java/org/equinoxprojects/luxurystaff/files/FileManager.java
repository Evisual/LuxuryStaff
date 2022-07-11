package org.equinoxprojects.luxurystaff.files;

import lombok.Getter;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.logger.LogType;
import org.equinoxprojects.luxurystaff.logger.Logger;

import java.io.IOException;

public class FileManager
{
    private static FileManager instance;
    private final Logger logger = new Logger("LuxuryStaff");

    public static FileManager getInstance()
    {
        return instance == null ? instance = new FileManager() : instance;
    }

    private @Getter ConfigFile messagesConfig;
    private @Getter ConfigFile config;
    private @Getter ConfigFile reportsConfig;
    private @Getter ConfigFile logins;
    private @Getter ConfigFile permissionsConfig;

    public boolean loadFiles(final LuxuryStaff plugin)
    {
        logger.log(LogType.INFO, "Loading files...");

        try {
            messagesConfig = new ConfigFile(plugin, "messages.yml");
            config = new ConfigFile(plugin, "config.yml");
            reportsConfig = new ConfigFile(plugin, "reports.yml");
            logins = new ConfigFile(plugin, "logins.yml");
            permissionsConfig = new ConfigFile(plugin, "permissions.yml");
        } catch(IOException e)
        {
            return false;
        }

        logger.log(LogType.INFO, "Successfully loaded files");
        return true;
    }

    public void reloadFiles()
    {
        messagesConfig.reload();
        config.reload();
        reportsConfig.reload();
        logins.reload();
        permissionsConfig.reload();
    }
}
