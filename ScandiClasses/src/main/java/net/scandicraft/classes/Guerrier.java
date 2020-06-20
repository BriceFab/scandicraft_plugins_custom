package net.scandicraft.classes;

import net.scandicraft.capacities.impl.GuerrierCapacity1;
import net.scandicraft.capacities.ICapacity;

import java.util.ArrayList;
import java.util.Arrays;

public class Guerrier implements IClasse {
    private final ArrayList<ICapacity> capacities = new ArrayList<>(Arrays.asList(
            new GuerrierCapacity1(),
            new GuerrierCapacity1()
    ));

    @Override
    public String getClasseName() {
        return "Guerrier";
    }

    @Override
    public ArrayList<ICapacity> getCapacities() {
        return capacities;
    }
}
