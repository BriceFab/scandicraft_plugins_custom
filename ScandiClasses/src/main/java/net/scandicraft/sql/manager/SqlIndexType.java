package net.scandicraft.sql.manager;

/**
 * Index SQL: UNIQUE, PRIMARY pour des colonnes
 */
public enum SqlIndexType {
    UNIQUE("UNIQUE");

    private final String name;

    SqlIndexType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
