package net.scandicraft.classes;

import net.scandicraft.capacities.ICapacity;
import net.scandicraft.capacities.impl.GuerrierCapacity1;
import net.scandicraft.capacities.impl.GuerrierCapacity2;
import net.scandicraft.capacities.impl.GuerrierCapacity3;

import java.util.ArrayList;
import java.util.Arrays;

public class Guerrier implements IClasse {

    @Override
    public ClasseType getClassType() {
        return ClasseType.GUERRIER;
    }

    private final ArrayList<ICapacity> capacities = new ArrayList<>(Arrays.asList(
            new GuerrierCapacity1(),
            new GuerrierCapacity2(),
            new GuerrierCapacity3()
    ));

    @Override
    public String getDisplayClasseName() {
        return getClassType().getName();
    }

    @Override
    public ArrayList<ICapacity> getCapacities() {
        return capacities;
    }
}
