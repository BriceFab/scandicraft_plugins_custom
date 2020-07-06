package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.capacities.utils.CapacityUtils;
import net.scandicraft.config.CapacitiesConfig;
import net.scandicraft.config.ClassesConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * soigne lui + la personne qui vise
 */
public class MagicienCapacity2 extends BaseCapacity {
    @Override
    public String getName() {
        return "soigneur";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_2;
    }

    @Override
    public void onUse(Player sender) throws CapacityException {
        sender.setHealth(sender.getMaxHealth());

        Player target = CapacityUtils.getTargetPlayer(sender, CapacitiesConfig.MAX_TARGET_DISTANCE);
        if (target != null) {
            target.setHealth(target.getMaxHealth());
            target.sendMessage(String.format("%s %s a utilisé la capacité %s sur vous.", ChatColor.GREEN, sender.getDisplayName(), this.getName()));
        } else {
            throw new CapacityException("no target found", CapacityException.NO_TARGET_PLAYER);
        }
    }
}
