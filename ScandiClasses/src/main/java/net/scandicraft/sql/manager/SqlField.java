package net.scandicraft.sql.manager;

public class SqlField {

    private final SqlFieldType type;
    private String name;
    private int size;   //0 = null
    private boolean nullable = true;

    public SqlField(SqlFieldType type, String name) {
        this(type, name, 255, true);
    }

    public SqlField(SqlFieldType type, String name, int size) {
        this(type, name, size, true);
    }

    public SqlField(SqlFieldType type, String name, boolean nullable) {
        this(type, name, 0, nullable);
    }

    public SqlField(SqlFieldType type, String name, int size, boolean nullable) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public SqlFieldType getType() {
        return type;
    }

    public boolean isNullable() {
        return nullable;
    }

    public int getSize() {
        return size;
    }
}
