package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.target.ICapacityTarget;
import net.scandicraft.utils.MathUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Capacité 1: donne 10 secondes de force II
 */
public class GuerrierCapacity1 extends BaseCapacity {
    @Override
    public String getName() {
        return "GuerrierCapacity1";
    }

    @Override
    public void onUse(Player sender, ICapacityTarget target) {
        sender.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MathUtils.convertSecondsToTicks(10), 1));
    }

    @Override
    public int getCooldownTime() {
        return 30;    //30 secondes
    }

}
