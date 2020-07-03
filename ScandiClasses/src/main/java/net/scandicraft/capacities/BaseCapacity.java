package net.scandicraft.capacities;

import net.scandicraft.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class BaseCapacity implements ICapacity {

    @Override
    public void sendSucessMessage(Player sender) {
        sender.sendMessage(String.format("%sCapacité %s utilisée avec succès.", ChatColor.GREEN, this.getName()));
    }

    @Override
    public void sendWaitingMessage(Player sender, long cooldown) {
        sender.sendMessage(String.format("%sVous devez attendre %d secondes pour utiliser la capacité %s.", ChatColor.RED, cooldown, this.getName()));
    }
}
