package org.equinoxprojects.luxurystaff.chat;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class StaffChat
{
    private final ArrayList<Player> staffChat = new ArrayList<>();

    private static final StaffChat instance = new StaffChat();

    public static StaffChat getInstance() { return instance; }

    public void removeStaffChat(Player p)
    {
        staffChat.remove(p);
    }

    public void addStaffChat(Player p)
    {
        staffChat.add(p);
    }

    public void toggleStaffChat(Player p)
    {
        if(staffChat.contains(p))
            staffChat.remove(p);
        else
            staffChat.add(p);
    }

    public boolean hasStaffChatEnabled(Player p)
    {
        return staffChat.contains(p);
    }
}
