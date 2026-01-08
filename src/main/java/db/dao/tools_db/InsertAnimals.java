package db.dao.tools_db;

import db.ConnectionManager;
import db.dao.AbstractTable;
import db.dao.AnimalTable;
import app.factory.Animal;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class InsertAnimals extends AbstractTable {
    private final ConnectionManager connectionManager;

    public InsertAnimals(ConnectionManager connectionManager) {
        super(connectionManager);
        this.connectionManager = connectionManager;
    }

    private void executeInTransaction(Runnable action) {
    }

    public void insertAnimal(Animal animal) throws SQLException {
        executeInTransaction(() -> { //запускаем транзакцию
            insertAnimal(
                    animal.getType().name(),
                    animal.getName(),
                    animal.getAge(),
                    animal.getWeight(),
                    String.valueOf(animal.getColor())
            );
        });
    }

    public void insertAnimal(String type, String name, int age, double weight, String color) {
        String sql = "INSERT INTO " + AnimalTable.TABLE_NAME +
                "(type, name, age, weight, color) VALUES (?, ?, ?, ?, ?)";

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