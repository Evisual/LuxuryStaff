package org.equinoxprojects.luxurystaff.player;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager
{
    private static final PlayerManager instance = new PlayerManager();

    public static PlayerManager getInstance() { return instance; }

    private final ArrayList<LuxuryPlayer> players = new ArrayList<>();

    public void addPlayer(LuxuryPlayer p)
    {
        players.add(p);
    }

    public void removePlayer(LuxuryPlayer p)
    {
        players.remove(p);
    }

    public LuxuryPlayer getPlayerById(UUID id)
    {
        for(LuxuryPlayer p : players)
        {
            if(p.getBukkitPlayer().getUniqueId().equals(id))
                return p;
        }

        return null;
    }

    public LuxuryPlayer getPlayerByName(String name)
    {
        for(LuxuryPlayer p : players)
        {
            if(p.getBukkitPlayer().getName().equalsIgnoreCase(name))
                return p;
        }

        return null;
    }
}
