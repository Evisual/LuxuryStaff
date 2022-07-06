package org.equinoxprojects.luxurystaff.chat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.chat.StaffChat;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.logger.util.Utils;

public class ChatListener implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        if(!StaffChat.getInstance().hasStaffChatEnabled(p))
            return;

        String message = e.getMessage();
        e.setCancelled(true);

        String newMessage = Utils.colorize("&7(&b" + Bukkit.getServer().getName() + "&7) &b" + p.getName() + "&7 » &f" + message);

        for(Player pl : Bukkit.getOnlinePlayers())
        {
            if(!Permissions.VIEW_STAFFCHAT.hasPermission(pl))
                continue;

            pl.sendMessage(newMessage);
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e)
    {
        Player p = e.getPlayer();

        if(StaffChat.getInstance().hasStaffChatEnabled(p))
            StaffChat.getInstance().removeStaffChat(p);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();

        if(LuxuryStaff.getCustomConfig().ENABLE_STAFF_CHAT_ON_JOIN)
        {
            StaffChat.getInstance().addStaffChat(p);
            p.sendMessage(Messages.STAFF_CHAT_ENABLED.getMessage());
        } else
        {
            StaffChat.getInstance().removeStaffChat(p);
            p.sendMessage(Messages.STAFF_CHAT_DISABLED.getMessage());
        }
    }
}
