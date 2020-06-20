package net.scandicraft.capacities;

import net.scandicraft.capacities.target.ICapacityTarget;
import org.bukkit.entity.Player;

public interface ICapacity {

    String getName();

    void onUse(Player sender, ICapacityTarget target);

}
