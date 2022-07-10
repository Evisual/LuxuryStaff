package org.equinoxprojects.luxurystaff.chat;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class ChatManager
{

    private static final ChatManager instance = new ChatManager();

    public static ChatManager getInstance() { return instance; }

    private @Getter @Setter boolean disabled = false;
    private @Getter @Setter int slow = 0;
    private @Getter HashMap<UUID, Integer> onCooldown = new HashMap<>();

    public void startRunnable(JavaPlugin plugin)
    {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            HashMap<UUID, Integer> it = (HashMap<UUID, Integer>) onCooldown.clone();

            for(UUID id : it.keySet())
            {
                int prev = onCooldown.get(id);
                onCooldown.remove(id);

                prev--;
                if(prev != 0) onCooldown.put(id, prev);
            }
        }, 20L, 20L);
    }
}