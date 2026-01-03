package db.dao;

import db.AnimalTColumns;
import db.ConnectionManager;

import java.sql.SQLException;

public class AnimalTable extends AbstractTable {
    public static final String TABLE_NAME = "table_animals_kozhevnikova";

    public AnimalTable(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected void insertData(Object data) throws SQLException {

    }

    @Override
    protected int updateData(Object data) throws SQLException {
        return 0;
    }

    public void createAnimalsTableIfNotExist() throws SQLException {
        StringBuilder columnsDefinition = new StringBuilder();

        for (AnimalTColumns field : AnimalTColumns.values()) {
            if (field != AnimalTColumns.ANIMAL_ID) {
                columnsDefinition.append(field.getFieldName()).append(" ").append(getColumnDefinition(field)).append(",");
            } else {
                columnsDefinition.append(field.getFieldName()).append(" SERIAL PRIMARY KEY").append(",");
            }
        }

        columnsDefinition.setLength(columnsDefinition.length() - 1); // удаляем последний знак - ", "

        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + columnsDefinition + ");";
        createTableIfNotExists(TABLE_NAME, createTableSQL);
    }

    private String getColumnDefinition(AnimalTColumns field) {
        switch (field) {
            case TYPE:
            case NAME:
            case COLOR:
                return "VARCHAR(50)";
            case AGE:
                return "INT";
            case WEIGHT:
                return "DOUBLE PRECISION";
            default:
                System.out.println("Тип данных не поддерживается.");
        }
        return "";
    }

    @Override
    protected void executeUpdate(String sql) throws SQLException {
        try (var conn = connectionManager.getConnection(); var stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

}