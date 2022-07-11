package org.equinoxprojects.luxurystaff.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.permissions.Permissions;

import java.util.HashMap;

public class PlayerListeners implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        LuxuryPlayer pl = new LuxuryPlayer(p);
        PlayerManager.getInstance().addPlayer(pl);

        vanishJoinHandler(p, pl);
        if(p.hasPermission(Permissions.SILENT_JOIN.getPermission())) {
            e.setJoinMessage("");
            joinMessages(p);
        }
    }

    public void joinMessages(Player p)
    {
        for(Player pl : Bukkit.getOnlinePlayers())
        {
            if(pl.hasPermission(Permissions.SEE_SILENT_JOIN.getPermission()))
            {
                HashMap<String, String> placeholders = new HashMap<>();
                placeholders.put("%player%", p.getName());
                pl.sendMessage(Messages.SILENT_JOIN.getMessage(placeholders));
            }
        }
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
