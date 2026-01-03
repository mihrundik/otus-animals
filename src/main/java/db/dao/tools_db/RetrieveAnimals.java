package db.dao.tools_db;

import db.ConnectionManager;
import db.dao.AnimalTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrieveAnimals {

    private final ConnectionManager connectionManager;

    public RetrieveAnimals(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<String[]> retrieveAllAnimals() {
        return retrieveByType(null); // если операция LIST, то NULL
    }

    public List<String[]> retrieveByType(String type) {
        List<String[]> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(AnimalTable.TABLE_NAME);

        if (type != null && !type.trim().isEmpty()) { // если указан тип
            sql.append(" WHERE type='").append(type).append("'");
        }

        try (ResultSet rs = connectionManager.executeQuery(sql.toString())) {
            while (rs.next()) {
                String[] row = {
                        String.valueOf(rs.getInt("animal_id")),
                        rs.getString("type"),
                        rs.getString("name"),
                        Integer.toString(rs.getInt("age")),
                        Double.toString(rs.getDouble("weight")),
                        rs.getString("color")
                };
                result.add(row);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
}