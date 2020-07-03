package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.target.ICapacityTarget;
import net.scandicraft.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * soigne lui + la personne qui vise
 */
public class MagicienCapacity2 extends BaseCapacity {
    @Override
    public String getName() {
        return "MagicienCapacity2";
    }

    @Override
    public int getCooldownTime() {
        return 3 * 60;  //3 mn
    }

    @Override
    public void onUse(Player sender, ICapacityTarget target) {
        sender.setHealth(sender.getMaxHealth());
        if (target != null && target.getTarget() instanceof Player) {
            Player playerTarget = (Player) target.getTarget();
            playerTarget.setHealth(playerTarget.getMaxHealth());
            playerTarget.sendMessage(String.format("%s %s a utilisé la capacité %s sur vous.", ChatColor.GREEN, sender.getDisplayName(), this.getName()));
        }
    }
}
