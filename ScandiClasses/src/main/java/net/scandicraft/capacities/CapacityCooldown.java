package net.scandicraft.capacities;

import net.scandicraft.capacities.ICapacity;

public class CapacityCooldown {

    private final long time;
    private final ICapacity capacity;

    public CapacityCooldown(long time, ICapacity capacity) {
        this.time = time;
        this.capacity = capacity;
    }

    public ICapacity getCapacity() {
        return capacity;
    }

    public long getTime() {
        return time;
    }
}
