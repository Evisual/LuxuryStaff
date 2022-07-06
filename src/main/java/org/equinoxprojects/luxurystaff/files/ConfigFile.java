package org.equinoxprojects.luxurystaff.files;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.equinoxprojects.luxurystaff.logger.LogType;
import org.equinoxprojects.luxurystaff.logger.Logger;

import java.io.File;
import java.io.IOException;

public class ConfigFile
{
    private final JavaPlugin plugin;
    private final @Getter File file;
    private final @Getter FileConfiguration config;
    private final Logger logger = new Logger("LuxuryStaff");

    public ConfigFile(final JavaPlugin plugin, final String file) throws IOException {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), file);
        this.config = YamlConfiguration.loadConfiguration(this.file);

        if(createFile()) return;

        if(!save())
        {
            throw new IOException();
        }
    }

    public boolean save()
    {
        try {
            config.save(file);
            return true;
        } catch(IOException e)
        {
            logger.log(LogType.ERROR, "Failed to save: " + file.getName());
            return false;
        }
    }

    public boolean reload()
    {
        try {
            config.load(file);
            return true;
        } catch(IOException | InvalidConfigurationException e)
        {
            logger.log(LogType.ERROR, "Failed to load: " + file.getName());
            return false;
        }
    }

    private boolean createFile()
    {
        try {
            if(!file.exists())
                return file.createNewFile();
        } catch(IOException e) {
            logger.log(LogType.ERROR, "Failed to create: " + file.getName());
        }

        return false;
    }
}
