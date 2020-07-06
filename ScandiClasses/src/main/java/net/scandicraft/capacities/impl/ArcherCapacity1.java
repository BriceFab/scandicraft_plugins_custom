package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.capacities.utils.CapacityUtils;
import net.scandicraft.config.CapacitiesConfig;
import net.scandicraft.config.ClassesConfig;
import net.scandicraft.utils.MathUtils;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * tire 3 flèches spectrales devant lui
 */
public class ArcherCapacity1 extends BaseCapacity {
    @Override
    public String getName() {
        return "ArcherCapacity1";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_1;
    }

    @Override
    public void onUse(Player sender) throws CapacityException {
        Player target = CapacityUtils.getTargetPlayer(sender, CapacitiesConfig.MAX_TARGET_DISTANCE);
        if (target != null) {
            Arrow arrow = sender.getWorld().spawnArrow(sender.getEyeLocation(), new Vector(0, 0, 0), 0.6F, 12F);
            arrow.setShooter(sender);
            arrow.setVelocity(sender.getEyeLocation().getDirection().multiply(5F));
            arrow.setFireTicks(MathUtils.convertSecondsToTicks(5));
        } else {
            throw new CapacityException("no target found", CapacityException.NO_TARGET_PLAYER);
        }
    }
}
