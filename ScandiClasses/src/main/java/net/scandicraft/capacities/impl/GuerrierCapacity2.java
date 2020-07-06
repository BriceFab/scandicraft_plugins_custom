package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.capacities.utils.CapacityUtils;
import net.scandicraft.config.CapacitiesConfig;
import net.scandicraft.config.ClassesConfig;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;

/**
 * se téléporte sur le joueur visé
 */
public class GuerrierCapacity2 extends BaseCapacity {
    @Override
    public String getName() {
        return "téléportation guerrière";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_2;
    }

    @Override
    public void onUse(Player sender) throws CapacityException {
        //TODO check target here
        Player target = CapacityUtils.getTargetPlayer(sender, CapacitiesConfig.MAX_TARGET_DISTANCE);
        if (target != null) {
            sender.teleport(target.getLocation());
            sender.getWorld().playEffect(sender.getLocation(), Effect.ENDER_SIGNAL, 50);
            target.sendMessage(String.format("%s %s a utilisé la capacité %s sur vous.", ChatColor.RED, sender.getDisplayName(), this.getName()));
        } else {
            throw new CapacityException("no target found", CapacityException.NO_TARGET_PLAYER);
        }
    }
}
