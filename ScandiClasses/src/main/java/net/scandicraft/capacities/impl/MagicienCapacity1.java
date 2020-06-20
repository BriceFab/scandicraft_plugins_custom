package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.target.ICapacityTarget;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * éclair sur la personne ou le bloc qu’il vise
 */
public class MagicienCapacity1 extends BaseCapacity {
    @Override
    public String getName() {
        return "MagicienCapacity1";
    }

    @Override
    public int getCooldownTime() {
        return 30;  //30s
    }

    @Override
    public void onUse(Player sender, ICapacityTarget target) {
        Location targetLightningLocation = getLightningLocation(target);
        if (targetLightningLocation != null) {
            sender.getWorld().strikeLightning(targetLightningLocation);
        }
    }

    private Location getLightningLocation(ICapacityTarget target) {
        if (target == null) {
            return null;
        }

        Object targetObject = target.getTarget();
        if (targetObject instanceof Player) {
            return ((Player) targetObject).getLocation();
        } else if (targetObject instanceof Location) {
            return (Location) targetObject;
        }

        return null;
    }
}
