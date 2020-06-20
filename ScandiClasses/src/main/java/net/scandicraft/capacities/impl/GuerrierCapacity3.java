package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.target.ICapacityTarget;
import net.scandicraft.utils.MathUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GuerrierCapacity3 extends BaseCapacity {
    @Override
    public String getName() {
        return "GuerrierCapacity3";
    }

    @Override
    public int getCooldownTime() {
        return 3 * 60;  //3 mn
    }

    @Override
    public void onUse(Player sender, ICapacityTarget target) {
        sender.removePotionEffect(PotionEffectType.SPEED);
        sender.removePotionEffect(PotionEffectType.ABSORPTION);
        sender.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, MathUtils.convertSecondsToTicks(8), 2));
        sender.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, MathUtils.convertSecondsToTicks(30), 2));
    }
}
