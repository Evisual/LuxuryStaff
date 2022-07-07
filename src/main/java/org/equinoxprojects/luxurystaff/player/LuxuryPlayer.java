package org.equinoxprojects.luxurystaff.player;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.equinoxprojects.luxurystaff.support.Report;
import org.equinoxprojects.luxurystaff.support.ReportHandler;

import java.util.ArrayList;

public class LuxuryPlayer
{
    private final @Getter Player bukkitPlayer;
    private ArrayList<Report> reportsReceived = new ArrayList<>();
    private ArrayList<Report> reportsGiven = new ArrayList<>();

    public LuxuryPlayer(Player p)
    {
        bukkitPlayer = p;
        loadData();
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
