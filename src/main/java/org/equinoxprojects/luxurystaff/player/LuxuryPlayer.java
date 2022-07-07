package org.equinoxprojects.luxurystaff.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

    public LuxuryPlayer(Player p)
    {
        bukkitPlayer = p;
        loadData();
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
