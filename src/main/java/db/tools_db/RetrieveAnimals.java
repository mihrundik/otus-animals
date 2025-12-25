package db.dao;

import db.ConnectionManager;
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
        List<String[]> result = new ArrayList<>();
        String sql = "SELECT * FROM " + AnimalTable.TABLE_NAME;

        try(ResultSet rs = connectionManager.executeQuery(sql)) {
            while(rs.next()) {
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
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }
}