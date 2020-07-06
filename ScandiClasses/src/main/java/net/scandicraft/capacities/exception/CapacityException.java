package net.scandicraft.capacities.exception;

import org.bukkit.ChatColor;

public class CapacityException extends Exception {

    private String playerErrorMessage = "";

    public CapacityException(String message, String playerErrorMessage) {
        super(message);

        this.playerErrorMessage = playerErrorMessage;
    }

    /**
     * @return erreur que l'on peut afficher au joueur
     */
    public String getPlayerErrorMessage() {
        return ChatColor.RED + playerErrorMessage;
    }
}
