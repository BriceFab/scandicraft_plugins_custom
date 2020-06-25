package net.scandicraft.sql.manager.impl;

import net.scandicraft.LogManager;
import net.scandicraft.classes.IClasse;
import net.scandicraft.sql.SqlConfig;
import net.scandicraft.sql.SqlManager;
import net.scandicraft.sql.manager.BaseSqlManager;
import net.scandicraft.sql.manager.SqlField;
import net.scandicraft.sql.manager.SqlFieldType;
import net.scandicraft.sql.manager.SqlIndexType;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlClassesManager extends BaseSqlManager {

    private static final SqlClassesManager INSTANCE = new SqlClassesManager(SqlConfig.classeTable);

    protected SqlClassesManager(String tableName) {
        super(tableName);
    }

    public static SqlClassesManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void createFieldsSchema() {
        addSqlField(new SqlField(SqlFieldType.STRING, "uuid", 255, false, SqlIndexType.UNIQUE));
        addSqlField(new SqlField(SqlFieldType.INT, "class_type", false));
        addSqlField(new SqlField(SqlFieldType.BIGINT, "since", false));
    }

    /**
     * Choisi une classe (Guerrier, Archer, Magicien)
     * @param player Joueur
     * @param classe Classe
     * @return success ou erreur
     */
    public boolean selectClass(Player player, IClasse classe) {
        try {
            List<String> params = new ArrayList<>();
            params.add("uuid");             //param 1
            params.add("class_type");       //param 2
            params.add("since");            //param 3
            String sql = prepareInsertSql(params);

            PreparedStatement statement = SqlManager.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, classe.getClassType().getId());
            statement.setLong(3, System.currentTimeMillis());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

}
