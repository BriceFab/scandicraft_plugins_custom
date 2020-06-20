package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.target.ICapacityTarget;
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
    public void onUse(Player sender, ICapacityTarget target) {
        //TODO quand on tire une flèche
        if (target != null && target.getTarget() instanceof Player) {
            Player playerTarget = (Player) target.getTarget();
            playerTarget.setHealth(playerTarget.getHealth() / 2);
        }
    }
}
