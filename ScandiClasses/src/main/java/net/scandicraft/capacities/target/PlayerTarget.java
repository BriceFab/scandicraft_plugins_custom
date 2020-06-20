package net.scandicraft.capacities.target;

import org.bukkit.entity.Player;

public class PlayerTarget implements ICapacityTarget {

    private Object target = null;

    public PlayerTarget(Player target) {
        setTarget(target);
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public void setTarget(Object target) {
        this.target = target;
    }
}
