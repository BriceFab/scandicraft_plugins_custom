package net.scandicraft.capacities;

import net.scandicraft.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class BaseCapacity implements ICapacity {

    @Override
    public void sendSucessMessage(Player sender) {
        sender.sendMessage(String.format("%s%s Capacité %s utilisée avec succès.", ChatColor.GREEN, Config.PREFIX, this.getName()));
    }

    @Override
    public void sendWaitingMessage(Player sender, long cooldown) {
        sender.sendMessage(String.format("%s%s Vous devez attendre %d secondes pour utiliser la capacité %s.", ChatColor.RED, Config.PREFIX, cooldown, this.getName()));
    }
}
