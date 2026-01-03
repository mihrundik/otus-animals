package db.dao.tools_db;

import app.factory.Color;
import db.ConnectionManager;
import db.dao.AnimalTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateAnimals {

    private static ConnectionManager connectionManager;

    public UpdateAnimals(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public boolean update(int id, String data) throws SQLException {
        String sql = "UPDATE " + AnimalTable.TABLE_NAME + " SET name=?, age=?, weight=?, color=? WHERE animal_id=?";

        try (PreparedStatement stmt = connectionManager.getConnection().prepareStatement(sql)) {
            // Разделяем строку на части
            String[] parts = data.split(",");

//            for (int i = 0; i < parts.length; i++) {  // отладочная
//                System.out.println(parts[i]);
//            }

            stmt.setString(1, parts[0].trim());
            stmt.setInt(2, Integer.parseInt(parts[1].trim()));
            stmt.setDouble(3, Double.parseDouble(parts[2].trim()));
            stmt.setObject(4, Color.stringColor(parts[3]));
            stmt.setInt(5, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public void format(String s, String name, int age, double weight, Color color) {
    }
}
