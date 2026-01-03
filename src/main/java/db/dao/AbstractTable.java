package db.dao;

import db.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractTable {
    final ConnectionManager connectionManager;

    public AbstractTable(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    // проверяем наличие таблицы
    public boolean exists(String tableName) throws SQLException {
        try (PreparedStatement pstmt = ConnectionManager.getInstance().getConnection().prepareStatement(
                "SELECT EXISTS(SELECT * FROM information_schema.tables WHERE table_name=?)")) {
            pstmt.setString(1, tableName.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getBoolean(1);
        }
    }

    // создание таблицы при условии ее отсутствия
    public void createTable(String sqlCreateCommand) throws SQLException {
        try (PreparedStatement pstmt = ConnectionManager.getInstance().getConnection().prepareStatement(sqlCreateCommand)) {
            pstmt.execute();
        }
    }


    protected void createTableIfNotExists(String tableName, String createSql) throws SQLException {
        if (!exists(tableName)) {
            createTable(createSql);
            System.out.println("Таблица " + tableName + " успешно создана.");
        }
    }

    // CRUD
    protected abstract void insertData(Object data) throws SQLException;
    protected abstract int updateData(Object data) throws SQLException;

    protected List<Object> readData(String whereClause) throws SQLException {
        return null;
    }


    // работа с транзакциями
    protected void executeInTransaction(Runnable action) throws SQLException {
        connectionManager.getConnection().setAutoCommit(false);
        try {
            action.run();
            connectionManager.getConnection().commit();
        } catch (SQLException e) {
            connectionManager.getConnection().rollback();
            throw e;
        } finally {
            connectionManager.getConnection().setAutoCommit(true);
        }
    }

    protected abstract void executeUpdate(String sql) throws SQLException;
}