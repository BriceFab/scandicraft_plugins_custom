package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.target.ICapacityTarget;
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
        return 30;  //30s
    }

    @Override
    public void onUse(Player sender, ICapacityTarget target) {
        if (target != null && target.getTarget() instanceof Player) {
            Player playerTarget = (Player) target.getTarget();

            Arrow arrow = sender.getWorld().spawnArrow(sender.getEyeLocation(), new Vector(0, 0, 0), 0.6F, 12F);
            arrow.setShooter(sender);
            arrow.setVelocity(sender.getEyeLocation().getDirection().multiply(5F));
            arrow.setFireTicks(MathUtils.convertSecondsToTicks(5));
        }
    }
}
