package db.tools_db;

import db.ConnectionManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import db.dao.AnimalTable;

public class InsertAnimals {

    private final ConnectionManager connectionManager;

    public InsertAnimals(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void insertAnimal(String type, String name, int age, double weight, String color) {
        String sql = "INSERT INTO " + AnimalTable.TABLE_NAME + "(type, name, age, weight, color) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, type);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setDouble(4, weight);
            stmt.setString(5, color);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при вставке животного в базу данных.", e);
        }
    }
}