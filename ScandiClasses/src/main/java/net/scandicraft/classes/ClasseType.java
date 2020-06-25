package net.scandicraft.classes;

/**
 * Représente la liste des classes (à la place de les mettre en bdd)
 */
public enum ClasseType {
    GUERRIER(0, "Guerrier"),
    ARCHER(1, "Archer"),
    MAGICIEN(2, "Magicien");

    private final int id;
    private final String name;

    ClasseType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
