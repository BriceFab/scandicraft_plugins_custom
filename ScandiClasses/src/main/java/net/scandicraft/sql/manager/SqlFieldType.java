package net.scandicraft.sql.manager;

public enum SqlFieldType {
    STRING("VARCHAR"),
    INT("INT"),
    BIGINT("BIGINT");

    private final String name;

    SqlFieldType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
