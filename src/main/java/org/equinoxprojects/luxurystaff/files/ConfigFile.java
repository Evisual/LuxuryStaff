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

    public ConfigFile(final JavaPlugin plugin, final String file)
    {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), file);
        this.config = YamlConfiguration.loadConfiguration(this.file);

        if(createFile()) return;
        save();
    }

    public void save()
    {
        try {
            config.save(file);
        } catch(IOException e)
        {
            logger.log(LogType.ERROR, "Failed to save: " + file.getName());
        }
    }

    public void reload()
    {
        try {
            config.load(file);
        } catch(IOException | InvalidConfigurationException e)
        {
            logger.log(LogType.ERROR, "Failed to load: " + file.getName());
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
