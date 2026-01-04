package db.dao.tools_db;

import app.factory.Color;
import db.ConnectionManager;
import db.dao.AbstractTable;
import db.dao.AnimalTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAnimals extends AbstractTable {

    private static ConnectionManager connectionManager;

    public UpdateAnimals(ConnectionManager connectionManager) {
        super(connectionManager);
        UpdateAnimals.connectionManager = connectionManager;
    }

    @Override
    protected void insertData(Object data) throws SQLException {

    }

    @Override
    protected void executeUpdate(int id, String data) throws SQLException {
        executeInTransaction(() -> { // обновление в транзакции
            try {
                update(id, data);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean update(int id, String data) throws SQLException {
        String sql = "UPDATE " + AnimalTable.TABLE_NAME + " SET name=?, age=?, weight=?, color=? WHERE animal_id=?";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            // Разделяем строку на части
            String[] parts = data.split(",");

            stmt.setString(1, parts[0].trim());
            stmt.setInt(2, Integer.parseInt(parts[1].trim()));
            stmt.setDouble(3, Double.parseDouble(parts[2].trim()));
            stmt.setObject(4, Color.stringColor(parts[3]));
            stmt.setInt(5, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

}
