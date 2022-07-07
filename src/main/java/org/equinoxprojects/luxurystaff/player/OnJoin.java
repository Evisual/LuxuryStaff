package org.equinoxprojects.luxurystaff.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnJoin implements Listener
{

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        PlayerManager.getInstance().addPlayer(new LuxuryPlayer(p));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        PlayerManager.getInstance().removePlayer(new LuxuryPlayer(p));
    }

}
