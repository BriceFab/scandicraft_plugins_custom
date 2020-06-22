package net.scandicraft.sql.manager;

public interface ISqlManager {

    /**
     * @return le nom de la table
     */
    String getTable();

    /**
     * Créer le schema de la table
     */
    void initSchema();

    /**
     * schema SQL de la table
     */
    void createFieldsSchema();
}
