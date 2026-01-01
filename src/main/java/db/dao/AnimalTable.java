package db.dao;

import db.ConnectionManager;

import java.sql.SQLException;

public class AnimalTable extends AbstractTable {
    public static final String TABLE_NAME = "table_animals_kozhevnikova";

    public AnimalTable(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    public void createAnimalsTableIfNotExist() throws SQLException {
        String createTableSQL =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        "animal_id SERIAL PRIMARY KEY," +
                        "type VARCHAR(20)," +
                        "name VARCHAR(50)," +
                        "age INT," +
                        "weight DOUBLE PRECISION," +
                        "color VARCHAR(20)" +
                        ")";

        createTableIfNotExists(TABLE_NAME, createTableSQL);
    }

}