package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.capacities.utils.CapacityUtils;
import net.scandicraft.config.CapacitiesConfig;
import net.scandicraft.config.ClassesConfig;
import org.bukkit.entity.Player;

/**
 * flèche qui enlève la moitié de la vie actuelle du joueur
 */
public class ArcherCapacity3 extends BaseCapacity {
    @Override
    public String getName() {
        return "flèche sanglante";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_3;
    }

    @Override
    public void onUse(Player sender) throws CapacityException {
        //TODO quand on tire une flèche
        Player target = CapacityUtils.getTargetPlayer(sender, CapacitiesConfig.MAX_TARGET_DISTANCE);
        if (target != null) {
            target.setHealth(target.getHealth() / 2);
        } else {
            throw new CapacityException("no target found", CapacityException.NO_TARGET_PLAYER);
        }
    }
}
