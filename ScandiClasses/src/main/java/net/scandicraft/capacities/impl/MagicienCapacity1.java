package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.capacities.utils.CapacityUtils;
import net.scandicraft.config.CapacitiesConfig;
import net.scandicraft.config.ClassesConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * éclair sur la personne ou le bloc qu’il vise
 */
public class MagicienCapacity1 extends BaseCapacity {
    @Override
    public String getName() {
        return "éclair";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_1;
    }

    @Override
    public void onUse(Player sender) throws CapacityException {
        Player target = CapacityUtils.getTargetPlayer(sender, CapacitiesConfig.MAX_TARGET_DISTANCE);
        if (target != null) {
            Location targetLightningLocation = getLightningLocation(target);

            if (targetLightningLocation != null) {
                sender.getWorld().strikeLightning(targetLightningLocation);
            }
        } else {
            throw new CapacityException("no target found", CapacityException.NO_TARGET_PLAYER);
        }
    }

    private Location getLightningLocation(Player target) {
        if (target == null) {
            return null;
        }

//        Object targetObject = target.getTarget();
//        if (targetObject instanceof Player) {
//            return ((Player) targetObject).getLocation();
//        } else if (targetObject instanceof Location) {
//            return (Location) targetObject;
//        }
        return target.getLocation();

//        return null;
    }
}
