package org.equinoxprojects.luxurystaff.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.permissions.Permissions;

public class OnJoin implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        LuxuryPlayer pl = new LuxuryPlayer(p);
        PlayerManager.getInstance().addPlayer(pl);

        vanishJoinHandler(p, pl);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();
        LuxuryPlayer pl = PlayerManager.getInstance().getPlayerById(p.getUniqueId());
        PlayerManager.getInstance().removePlayer(pl);

        if(pl.isVanished())
            pl.unvanish();
    }

    private void vanishJoinHandler(Player p, LuxuryPlayer pl)
    {
        if(p.hasPermission(Permissions.VANISH.getPermission()) && LuxuryStaff.getCustomConfig().ENABLE_VANISH_ON_JOIN)
        {
            pl.vanish();
            p.sendMessage(Messages.VANISHED.getMessage());
        }


        if(!p.hasPermission(Permissions.SEE_VANISHED.getPermission()))
        {
            for(LuxuryPlayer player : PlayerManager.getInstance().getVanished())
            {
                p.hidePlayer(player.getBukkitPlayer());
            }
        }
    }

}
