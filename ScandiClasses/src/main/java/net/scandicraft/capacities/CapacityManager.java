package net.scandicraft.capacities;

import net.scandicraft.CapacityCooldown;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CapacityManager {

    private static final CapacityManager INSTANCE = new CapacityManager();

    private CapacityManager() {
    }

    private final HashMap<UUID, CapacityCooldown> cooldowns = new HashMap<>();

    public void useCapacity(Player sender, ICapacity capacity) {
        if (canUseCapacity(sender, capacity)) {
//            sender.sendMessage("vous pouvez utiliser la capacité");
            capacity.sendSucessMessage(sender);
            capacity.onUse(sender, null);   //TODO target
        } else {
            CapacityCooldown capacityCooldown = this.cooldowns.get(sender.getUniqueId());
            capacity.sendWaitingMessage(sender, calculateRemainingTime(capacityCooldown));
//            sender.sendMessage("Vous devez attendre " + calculateRemainingTime(capacityCooldown) + " secondes");
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

//    public void useCapacity(Player sender, ICapacity capacity) {
//        if (this.cooldowns.containsKey(sender.getUniqueId())) {
//            long secondsLeft = 0;
//            CapacityCooldown capacityCooldown = this.cooldowns.get(sender.getUniqueId());
//            if (capacityCooldown != null) {
//                secondsLeft = ((capacityCooldown.getTime() / 1000) + capacity.getCooldownTime()) - System.currentTimeMillis() / 1000;
//            }
//
//            if (secondsLeft > 0) {
//                //must wait
//                sender.sendMessage("Vous devez attendre " + secondsLeft + " secondes");
//            } else {
//                //ok
//                this.cooldowns.remove(sender.getUniqueId());
//                sender.sendMessage("secondsLeft > 0 : else");
//            }
//        } else {
//            cooldowns.put(sender.getUniqueId(), new CapacityCooldown(System.currentTimeMillis(), capacity));
//            sender.sendMessage("vous pouvez utiliser la capacité");
//            capacity.onUse(sender, null);   //TODO add target
//        }
//    }

    public static CapacityManager getInstance() {
        return INSTANCE;
    }
}
