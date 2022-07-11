package org.equinoxprojects.luxurystaff.chat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.equinoxprojects.luxurystaff.LuxuryStaff;
import org.equinoxprojects.luxurystaff.chat.ChatManager;
import org.equinoxprojects.luxurystaff.chat.StaffChat;
import org.equinoxprojects.luxurystaff.config.Messages;
import org.equinoxprojects.luxurystaff.permissions.Permissions;
import org.equinoxprojects.luxurystaff.util.Utils;

import java.util.HashMap;
import java.util.UUID;

public class ChatListener implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        ChatManager manager = ChatManager.getInstance();

        if(staffChatCheck(p, e.getMessage()))
        {
            e.setCancelled(true);
            return;
        }

        if(manager.getOnCooldown().containsKey(p.getUniqueId()) && !p.hasPermission(Permissions.SLOW_CHAT_BYPASS.getPermission()))
        {
            e.setCancelled(true);

            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("%time%", Integer.toString(manager.getOnCooldown().get(id)));
            p.sendMessage(Messages.CHAT_SLOWED.getMessage(placeholders));
            return;
        }

        if(manager.isDisabled() && !p.hasPermission(Permissions.TOGGLE_CHAT_BYPASS.getPermission()))
        {
            e.setCancelled(true);
            p.sendMessage(Messages.CHAT_DISABLED.getMessage());
            return;
        }

        if(manager.getSlow() != 0)
            manager.getOnCooldown().put(id, manager.getSlow());
    }

    public boolean staffChatCheck(Player p, String message)
    {
        if(!StaffChat.getInstance().hasStaffChatEnabled(p))
            return false;

        String newMessage = Utils.colorize("&7(&b" + Bukkit.getServer().getName() + "&7) &b" + p.getName() + "&7 » &f" + message);

        for(Player pl : Bukkit.getOnlinePlayers())
        {
            if(!Permissions.VIEW_STAFFCHAT.hasPermission(pl))
                continue;

            pl.sendMessage(newMessage);
        }

        return true;
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

        if(!p.hasPermission(Permissions.STAFFCHAT.getPermission())) return;

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
