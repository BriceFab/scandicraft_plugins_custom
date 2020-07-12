package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.config.ClassesConfig;
import net.scandicraft.utils.MathUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Capacité 1: donne 5 secondes de force III
 */
public class GuerrierCapacity1 extends BaseCapacity {
    @Override
    public String getName() {
        return "épée sanglante";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_1;
    }

    @Override
    public String getUniqueIdentifier() {
        return "GuerrierCapacity1";
    }

    @Override
    public void onUse(Player sender) {
        sender.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MathUtils.convertSecondsToTicks(5), 2));
    }

}
