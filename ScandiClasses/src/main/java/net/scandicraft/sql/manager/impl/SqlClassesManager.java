package net.scandicraft.sql.manager.impl;

import net.scandicraft.classes.ClasseType;
import net.scandicraft.classes.IClasse;
import net.scandicraft.sql.SqlConfig;
import net.scandicraft.sql.SqlManager;
import net.scandicraft.sql.manager.BaseSqlManager;
import net.scandicraft.sql.manager.SqlField;
import net.scandicraft.sql.manager.SqlFieldType;
import net.scandicraft.sql.manager.SqlIndexType;
import org.bukkit.entity.Player;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        addSqlField(new SqlField(SqlFieldType.DATE, "since", false));
        addSqlField(new SqlField(SqlFieldType.BIGINT, "xp", false, 0));
    }

    /**
     * Choisi une classe (Guerrier, Archer, Magicien)
     *
     * @param player     Joueur
     * @param classeType Type de classe
     * @return success ou erreur
     */
    public boolean selectClasse(Player player, ClasseType classeType) {
        try {
            List<String> params = new ArrayList<>();
            params.add("uuid");             //param 1
            params.add("class_type");       //param 2
            params.add("since");            //param 3
            String sql = prepareInsertSql(params);

            PreparedStatement statement = SqlManager.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            statement.setInt(2, classeType.getId());
            statement.setDate(3, Date.valueOf(LocalDate.now()));
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * Va chercher la classe du joueur dans la base de donnée
     *
     * @param player Joueur
     * @return Classe du joueur
     */
    public IClasse getPlayerClasse(Player player) {
        try {
            List<String> params = new ArrayList<>();
            params.add("uuid");             //param 1
            String sql = prepareSelectWhereSql(params);

            PreparedStatement statement = SqlManager.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int class_type = result.getInt("class_type");

                ClasseType classeType = ClasseType.getClasseTypeFromId(class_type);
                if (classeType != null) {
                    return classeType.getIClasse();
                }
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * Update la classe du joueur dans la base de donnée
     *
     * @param player     joueur
     * @param classeType type de classe
     * @return success
     */
    public boolean changePlayerClasse(Player player, ClasseType classeType) {
        try {
            List<String> paramsUpdate = new ArrayList<>();
            paramsUpdate.add("class_type");             //param 1

            List<String> paramsWhere = new ArrayList<>();
            paramsWhere.add("uuid");                    //param 2

            String sql = prepareUpdateSql(paramsUpdate, paramsWhere);

            PreparedStatement statement = SqlManager.getInstance().getConnection().prepareStatement(sql);
            statement.setInt(1, classeType.getId());
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

}
