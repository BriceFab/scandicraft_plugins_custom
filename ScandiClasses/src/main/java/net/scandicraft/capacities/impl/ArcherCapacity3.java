package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.exception.CapacityException;
import net.scandicraft.capacities.utils.CapacityUtils;
import org.bukkit.entity.Player;

/**
 * flèche qui enlève la moitié de la vie actuelle du joueur
 */
public class ArcherCapacity3 extends BaseCapacity {
    @Override
    public String getName() {
        return "ArcherCapacity3";
    }

    @Override
    public int getCooldownTime() {
        return 3 * 60;  //3m
    }

    @Override
    public void onUse(Player sender) throws CapacityException {
        //TODO quand on tire une flèche
        Player target = CapacityUtils.getTargetPlayer(sender, 10);
        if (target != null) {
            target.setHealth(target.getHealth() / 2);
        } else {
            throw new CapacityException("no target found", "Aucun joueur visé");
        }
    }
}
