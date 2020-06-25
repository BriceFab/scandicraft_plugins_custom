package net.scandicraft.capacities;

import net.scandicraft.capacities.target.ICapacityTarget;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CapacityManager {

    private static final CapacityManager INSTANCE = new CapacityManager();

    private CapacityManager() {
    }

    private final HashMap<UUID, CapacityCooldown> cooldowns = new HashMap<>();

    public void useCapacity(Player sender, ICapacity capacity, ICapacityTarget target) {
        if (canUseCapacity(sender, capacity)) {
            capacity.sendSucessMessage(sender);
            capacity.onUse(sender, target);
        } else {
            CapacityCooldown capacityCooldown = this.cooldowns.get(sender.getUniqueId());
            capacity.sendWaitingMessage(sender, calculateRemainingTime(capacityCooldown));
        }
    }

    private boolean canUseCapacity(Player sender, ICapacity capacity) {
        if (this.cooldowns.containsKey(sender.getUniqueId())) {
            CapacityCooldown capacityCooldown = this.cooldowns.get(sender.getUniqueId());

            if (calculateRemainingTime(capacityCooldown) > 0) {
                return false;
            } else {
                this.cooldowns.remove(sender.getUniqueId());
                return true;
            }
        } else {
            cooldowns.put(sender.getUniqueId(), new CapacityCooldown(System.currentTimeMillis(), capacity));
            return true;
        }
    }

    private long calculateRemainingTime(CapacityCooldown capacityCooldown) {
        return ((capacityCooldown.getTime() / 1000) + capacityCooldown.getCapacity().getCooldownTime()) - System.currentTimeMillis() / 1000;
    }

    public static CapacityManager getInstance() {
        return INSTANCE;
    }
}
