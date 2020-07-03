package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.utils.CapacityUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * se téléporte sur le joueur visé
 */
public class GuerrierCapacity2 extends BaseCapacity {
    @Override
    public String getName() {
        return "GuerrierCapacity2";
    }

    @Override
    public int getCooldownTime() {
        return 3 * 60;     //3 mn
    }

    @Override
    public void onUse(Player sender) {
        //TODO check target here
        Player target = CapacityUtils.getTargetPlayer(sender, 10);
        if (target != null) {
            sender.teleport(target.getLocation());
            target.sendMessage(String.format("%s %s a utilisé la capacité %s sur vous.", ChatColor.RED, sender.getDisplayName(), this.getName()));
        } else {
            sender.sendMessage("No target player");
        }
    }
}
