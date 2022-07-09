package org.equinoxprojects.luxurystaff.security.authentication.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.player.LuxuryPlayer;
import org.equinoxprojects.luxurystaff.player.PlayerManager;

public class AuthenticationListeners implements Listener
{

    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        Location from = e.getFrom();
        Location to = e.getTo();

        Player p = e.getPlayer();
        LuxuryPlayer pl = PlayerManager.getInstance().getPlayerById(p.getUniqueId());

        if(!pl.needsAuthentication()) return;

        if(from.getBlockX() != to.getBlockX() ||
        from.getBlockY() != to.getBlockY() ||
        from.getBlockZ() != to.getBlockZ())
        {
            e.setCancelled(true);
            if(!pl.needsSetupPassword())
                p.sendMessage(LuxuryStaff.getCustomConfig().PREFIX + ChatColor.RED + "You cannot move until you have logged in! Type /auth <pass> to log in!");
            else
                p.sendMessage(LuxuryStaff.getCustomConfig().PREFIX + ChatColor.RED + "You cannot move until you have set up a password! Type /auth <pass> <confirm> to create one!");

        }
    }

}
