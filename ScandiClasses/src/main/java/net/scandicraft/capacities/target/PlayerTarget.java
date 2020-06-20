package net.scandicraft.capacities.target;

public class PlayerTarget implements ICapacityTarget {

    private Object target = null;

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public void setTarget(Object target) {
        this.target = target;
    }
}
