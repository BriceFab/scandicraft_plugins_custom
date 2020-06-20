package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.target.ICapacityTarget;
import net.scandicraft.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * se téléporte sur le joueur visé
 */
public class GuerrierCapacity2 extends BaseCapacity {
    @Override
    public String getName() {
        return "GuerrierCapacity2";
    }

    @Override
    public int getCooldownTime() {
        return 3 * 60;     //3 mn
    }

    @Override
    public void onUse(Player sender, ICapacityTarget target) {
        //TODO check target here
        if (target != null && target.getTarget() instanceof Player) {
            Player playerTarget = (Player) target.getTarget();
            sender.teleport(playerTarget.getLocation());
            playerTarget.sendMessage(String.format("%s%s %s a utilisé la capacité %s sur vous.", ChatColor.RED, Config.PREFIX, sender.getDisplayName(), this.getName()));
        } else {
            sender.sendMessage("No target player");
        }
    }
}
