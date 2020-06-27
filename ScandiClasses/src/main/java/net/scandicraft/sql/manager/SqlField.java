package net.scandicraft.sql.manager;

public class SqlField {

    private final SqlFieldType type;
    private final String name;
    private final int size;   //0 = null
    private boolean nullable = true;
    private final SqlIndexType index;   //constrainte SQL (unique, index)
    private Object defaultValue = null;

    public SqlField(SqlFieldType type, String name) {
        this(type, name, 255, true, null, null);
    }

    public SqlField(SqlFieldType type, String name, int size) {
        this(type, name, size, true, null, null);
    }

    public SqlField(SqlFieldType type, String name, boolean nullable) {
        this(type, name, 0, nullable, null, null);
    }

    public SqlField(SqlFieldType type, String name, int size, boolean nullable) {
        this(type, name, size, nullable, null, null);
    }

    public SqlField(SqlFieldType type, String name, int size, boolean nullable, SqlIndexType index) {
        this(type, name, size, nullable, index, null);
    }

    public SqlField(SqlFieldType type, String name, boolean nullable, Object defaultValue) {
        this(type, name, 0, nullable, null, defaultValue);
    }

    public SqlField(SqlFieldType type, String name, int size, boolean nullable, SqlIndexType index, Object defaultValue) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.nullable = nullable;
        this.index = index;
        this.defaultValue = defaultValue;
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

    public Object getDefaultValue() {
        return defaultValue;
    }
}
