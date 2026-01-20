package db.dao.tools_db;

import app.factory.Color;
import db.ConnectionManager;
import db.dao.AbstractTable;
import db.dao.AnimalTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAnimals extends AbstractTable {
    
    public UpdateAnimals(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    private void executeInTransaction(Runnable action) {
    }

    protected void updateData(int id, String data) throws SQLException {
        executeInTransaction(() -> { // обновление в транзакции
            try {
                update(id, data);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean update(int id, String data) throws SQLException {
        String sql = "UPDATE " + AnimalTable.TABLE_NAME + " SET type=?, name=?, age=?, weight=?, color=? WHERE animal_id=?";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            // Разделяем строку на части
            String[] parts = data.split(",");

            stmt.setString(1, parts[0].trim());
            stmt.setString(2, parts[1].trim());
            stmt.setInt(3, Integer.parseInt(parts[2].trim()));
            stmt.setDouble(4, Double.parseDouble(parts[3].trim()));
            stmt.setObject(5, Color.stringColor(parts[4]));
            stmt.setInt(6, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

}
