package org.equinoxprojects.luxurystaff.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.UUID;

public class ChatManager
{

    private static final ChatManager instance = new ChatManager();

    public static ChatManager getInstance() { return instance; }

    private @Getter @Setter boolean disabled = false;
    private @Getter @Setter int slow = 0;
    private @Getter HashMap<UUID, Integer> onCooldown = new HashMap<>();


}