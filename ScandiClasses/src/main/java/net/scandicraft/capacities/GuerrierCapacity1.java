package net.scandicraft.capacities;

import net.scandicraft.capacities.target.ICapacityTarget;
import net.scandicraft.utils.MathUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/*
Capacité 1: donne 10 secondes de force II
 */
public class GuerrierCapacity1 implements ICapacity {
    @Override
    public String getName() {
        return "GuerrierCapacity1";
    }

    @Override
    public void onUse(Player sender, ICapacityTarget target) {
        sender.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, MathUtils.convertSecondsToTicks(10), 2));
    }

}
