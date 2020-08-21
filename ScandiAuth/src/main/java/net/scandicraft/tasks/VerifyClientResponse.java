package net.scandicraft.tasks;

import org.bukkit.ChatColor;

public class VerifyClientResponse {

    private final boolean is_using_client;
    private final String message;

    public VerifyClientResponse(boolean is_using_client, String message) {
        this.is_using_client = is_using_client;
        this.message = message;
    }

    public boolean isUsingClient() {
        return this.is_using_client;
    }

    public String getMessage() {
        return (is_using_client ? ChatColor.GREEN : ChatColor.RED) + this.message;
    }
}
