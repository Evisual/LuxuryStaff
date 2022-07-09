package org.equinoxprojects.luxurystaff.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.files.FileManager;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.support.Report;
import org.equinoxprojects.luxurystaff.support.ReportHandler;

import java.util.ArrayList;

public class LuxuryPlayer
{
    private final @Getter Player bukkitPlayer;
    private ArrayList<Report> reportsReceived = new ArrayList<>();
    private ArrayList<Report> reportsGiven = new ArrayList<>();
    private boolean vanished = false;
    private @Getter @Setter String password = "";
    private @Setter boolean authentication = false;
    private @Setter boolean needsSetupPassword = false;

    public boolean needsAuthentication() { return authentication; }

    public boolean needsSetupPassword() { return needsSetupPassword; }

    public LuxuryPlayer(Player p)
    {
        bukkitPlayer = p;
        loadData();

        if(LuxuryStaff.getCustomConfig().REQUIRE_AUTHENTICATION && p.hasPermission(Permissions.NEED_AUTHENTICATION.getPermission()))
        {
            authentication = true;
            FileConfiguration config = FileManager.getInstance().getLogins().getConfig();
            if(!config.contains(p.getUniqueId() + ".login"))
            {
                needsSetupPassword = true;
                p.sendMessage(Messages.NEED_SETUP_PASSWORD.getMessage());
            } else
            {
                p.sendMessage(Messages.NEED_LOGIN.getMessage());
                password = config.getString(p.getUniqueId() + ".login");
            }

        }
    }

    public boolean isVanished() {
        return vanished;
    }

    public void vanish()
    {
        vanished = true;
        hide();
        PlayerManager.getInstance().getVanished().add(this);
    }

    private void hide()
    {
        for(Player pl : Bukkit.getOnlinePlayers())
        {
            if(!pl.hasPermission(Permissions.SEE_VANISHED.getPermission()))
                pl.hidePlayer(bukkitPlayer);
        }
    }

    public void unvanish()
    {
        vanished = false;
        show();
        PlayerManager.getInstance().getVanished().remove(this);
    }

    private void show()
    {
        for(Player pl : Bukkit.getOnlinePlayers())
        {
            pl.showPlayer(bukkitPlayer);
        }
    }

    private void loadData()
    {
        ReportHandler handler = new ReportHandler();
        if(handler.getGivenReports(bukkitPlayer.getUniqueId()).size() > 0)
            reportsGiven = handler.getGivenReports(bukkitPlayer.getUniqueId());
        if(handler.getReports(bukkitPlayer.getUniqueId()).size() > 0)
            reportsReceived = handler.getReports(bukkitPlayer.getUniqueId());
    }
}
