package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.capacities.target.ICapacityTarget;
import net.scandicraft.logs.LogManager;
import org.bukkit.entity.Player;

/**
 * téléporte lui et le joueur visé dans une arène PvP
 */
public class MagicienCapacity3 extends BaseCapacity {
    @Override
    public String getName() {
        return "MagicienCapacity3";
    }

    @Override
    public int getCooldownTime() {
        return 3 * 60;  //3 mn
    }

    @Override
    public void onUse(Player sender, ICapacityTarget target) {
        LogManager.consoleWarn(getName() + " a implémenter..");
    }
}
