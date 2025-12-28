package db.tools_db;

import db.ConnectionManager;
import db.dao.AnimalTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAnimals {
    public UpdateAnimals(ConnectionManager instance) {
    }

    public static boolean update(int id, String data) {
        try (Connection conn = ConnectionManager.getInstance().getConnection()) {
            String sql = "UPDATE " + AnimalTable.TABLE_NAME + " SET name=?, age=?, weight=?, color=? WHERE animal_id=?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Разделяем строку на части
                String[] parts = data.split(",");

                stmt.setString(1, parts[0]);
                stmt.setInt(2, Integer.parseInt(parts[1]));
                stmt.setDouble(3, Double.parseDouble(parts[2]));
                stmt.setString(4, parts[3]);
                stmt.setInt(5, id);

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}