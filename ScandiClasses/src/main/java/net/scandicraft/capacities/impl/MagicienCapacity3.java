package net.scandicraft.capacities.impl;

import net.scandicraft.capacities.BaseCapacity;
import net.scandicraft.config.ClassesConfig;
import net.scandicraft.logs.LogManager;
import org.bukkit.entity.Player;

/**
 * téléporte lui et le joueur visé dans une arène PvP pendant 1 minute
 */
public class MagicienCapacity3 extends BaseCapacity {
    @Override
    public String getName() {
        return "combattant";
    }

    @Override
    public int getCooldownTime() {
        return ClassesConfig.COOLDOWN_CAPACITY_3;
    }

    @Override
    public void onUse(Player sender) {
        LogManager.consoleWarn(getName() + " a implémenter..");
    }
}
