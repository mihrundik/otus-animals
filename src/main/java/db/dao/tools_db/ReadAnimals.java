package db.dao.tools_db;

import db.ConnectionManager;
import db.dao.AbstractTable;
import db.dao.AnimalTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadAnimals extends AbstractTable {

    private final ConnectionManager connectionManager;
    private String type;

    public ReadAnimals(ConnectionManager connectionManager) {
        super(connectionManager);
        this.connectionManager = connectionManager;
    }

    @Override
    protected void insertData(Object data) throws SQLException {

    }

    @Override
    protected void updateData(int id, String data) throws SQLException {

    }

    @Override
    protected void createTable(String sqlCreateCommand) throws SQLException {

    }

    @Override
    protected void deleteData(int id) throws SQLException {

    }

    @Override
    protected List<String[]> readData(String type) throws SQLException {
        this.type = type;
        return retrieveByType(type);
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