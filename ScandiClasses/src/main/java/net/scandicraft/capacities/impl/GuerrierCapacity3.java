package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.config.ClassesConfig;
import net.scandicraft.utils.MathUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * speed 3 pendant 5 secondes et 5 coeurs d'absorption
 */
public class GuerrierCapacity3 extends BaseCapacity {
    @Override
    public String getName() {
        return "fuyard";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_3;
    }

    @Override
    public String getUniqueIdentifier() {
        return "GuerrierCapacity3";
    }

    @Override
    public void onUse(Player sender) {
        sender.removePotionEffect(PotionEffectType.SPEED);
        sender.removePotionEffect(PotionEffectType.ABSORPTION);
        sender.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MathUtils.convertSecondsToTicks(5), 2));
        sender.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, MathUtils.convertSecondsToTicks(30), 2));
    }
}
