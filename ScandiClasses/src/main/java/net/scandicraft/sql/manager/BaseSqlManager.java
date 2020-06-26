package net.scandicraft.sql.manager;

import net.scandicraft.logs.LogManager;
import net.scandicraft.sql.SqlConfig;
import net.scandicraft.sql.SqlManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseSqlManager implements ISqlManager {
    private final String tableName;
    private final ArrayList<SqlField> tableFields = new ArrayList<>();

    protected BaseSqlManager(String tableName) {
        this.tableName = tableName;

        initSchema();
    }

    @Override
    public String getTable() {
        return SqlConfig.getConfig().getString(SqlConfig.getTable(this.tableName));
    }

    @Override
    public void initSchema() {
        createFieldsSchema();

        LogManager.consoleInfo("Create table SQL: " + this.createTable());
        boolean success = insert(this.createTable());
        if (success) {
            LogManager.consoleSuccess("Table " + this.getTable() + " created successfully in database " + SqlConfig.getConfig().getString(SqlConfig.database));
        } else {
            LogManager.consoleError("Error when trying to create table " + this.getTable() + " in database " + SqlConfig.getConfig().getString(SqlConfig.database));
        }
    }

    private String createTable() {
        return String.format("CREATE TABLE IF NOT EXISTS `%s` (`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY%s);", this.getTable(), this.getFieldsSchema());
    }

    private String getFieldsSchema() {
        StringBuilder sql_fields = new StringBuilder();

        ArrayList<SqlField> constraites = new ArrayList<>();
        for (int i = 0; i < this.tableFields.size(); i++) {
            SqlField field = this.tableFields.get(i);

            boolean isFirst = (i == 0);
            boolean isLast = (i == this.tableFields.size() - 1);

            if (isFirst) {
                sql_fields.append(", ");
            }

            //format: "`field_name` VARCHAR(2) NOT NULL,"

            String fieldSize = "";
            if (field.getSize() > 0) {
                fieldSize = "(" + field.getSize() + ")";
            }

            sql_fields.append(String.format(
                    "`%s` %s%s %s",
                    field.getName(),
                    field.getType().getName().toUpperCase(),
                    fieldSize,
                    field.isNullable() ? "NULL" : "NOT NULL"
            ));

            if (!isLast) {
                sql_fields.append(", ");
            }

            if (field.getIndex() != null) {
                constraites.add(field);
            }
        }

        //Ajouts des INDEX (Constraites)
        for (SqlField fieldWithConstraite : constraites) {
            switch (fieldWithConstraite.getIndex()) {
                default:
                    LogManager.consoleError("SQL Contrainte " + fieldWithConstraite.getIndex().getName() + " non supporté !");
                    break;
                case UNIQUE:
                    sql_fields.append(String.format(", UNIQUE INDEX `%s` (`%s`)", "unique_" + fieldWithConstraite.getName(), fieldWithConstraite.getName()));
                    break;
            }
        }

        return sql_fields.toString();
    }

    public void addSqlField(SqlField sqlField) {
        this.tableFields.add(sqlField);
    }

    /**
     * Simple insert SQL sans paramètre
     *
     * @param sql sql query
     * @return success
     */
    public boolean insert(String sql) {
        try {
            PreparedStatement statement = SqlManager.getInstance().getConnection().prepareStatement(sql);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Prépare une requête sql insert
     *
     * @param params paramètres
     * @return sql query avec ? pour les paramètres
     */
    public String prepareInsertSql(List<String> params) {
        StringBuilder sql = new StringBuilder();

        //INSERT INTO `scandidb`.`classe` (`id`, `name`) VALUES ('4', '44');
        sql.append(String.format("INSERT INTO `%s`", this.getTable()));

        for (int i = 0; i < params.size(); i++) {
            String param = params.get(i);

            boolean isFirst = (i == 0);
            boolean isLast = (i == params.size() - 1);

            if (isFirst) {
                sql.append(" (");
            }

            sql.append(param);

            if (isLast) {
                sql.append(")");
            } else {
                sql.append(", ");
            }
        }

        sql.append(" VALUES (");
        for (int i = 0; i < params.size(); i++) {
            boolean isFirst = (i == 0);
            boolean isLast = (i == params.size() - 1);

            sql.append("?");

            if (!isLast) {
                sql.append(", ");
            }

        }
        sql.append(");");

        return sql.toString();
    }

    /**
     * Prépare une requête sql select WHERE
     *
     * @param whereFields where clauses
     * @return sql query
     */
    public String prepareWhereSql(List<String> whereFields) {
//        SELECT * FROM `classe` WHERE `uuid` = ?;
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("SELECT * FROM `%s` WHERE ", this.getTable()));

        for (int i = 0; i < whereFields.size(); i++) {
            String whereField = whereFields.get(i);

            boolean isFirst = (i == 0);
            boolean isLast = (i == whereFields.size() - 1);

            sql.append(String.format("`%s` = ?", whereField));
        }

        return sql.toString();
    }

}
