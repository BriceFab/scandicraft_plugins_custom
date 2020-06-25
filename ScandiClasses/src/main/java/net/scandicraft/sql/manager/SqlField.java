package net.scandicraft.sql.manager;

public class SqlField {

    private final SqlFieldType type;
    private final String name;
    private final int size;   //0 = null
    private boolean nullable = true;
    private final SqlIndexType index;   //constrainte SQL (unique, index)

    public SqlField(SqlFieldType type, String name) {
        this(type, name, 255, true, null);
    }

    public SqlField(SqlFieldType type, String name, int size) {
        this(type, name, size, true, null);
    }

    public SqlField(SqlFieldType type, String name, boolean nullable) {
        this(type, name, 0, nullable, null);
    }

    public SqlField(SqlFieldType type, String name, int size, boolean nullable) {
        this(type, name, size, nullable, null);
    }

    public SqlField(SqlFieldType type, String name, int size, boolean nullable, SqlIndexType index) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.nullable = nullable;
        this.index = index;
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

    public SqlIndexType getIndex() {
        return index;
    }
}
