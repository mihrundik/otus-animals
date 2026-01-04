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
    protected abstract void executeUpdate(int id, String data) throws SQLException;  // принимает строку для изменения по ИД
    protected List<String[]> readData(String type) throws SQLException {
        return null;
    }


    // работа с транзакциями
    public void executeInTransaction(Runnable action) throws SQLException {
        connectionManager.getConnection().setAutoCommit(false); // начиная транзакцию отключаем автокоммит на случай работы с oracle
        try {
            action.run();
            connectionManager.getConnection().commit(); // при успешном завершении транзакции коммитим
        } catch (SQLException e) {
            connectionManager.getConnection().rollback(); // откатываем при неуспешном
            throw e;
        } finally {
            connectionManager.getConnection().setAutoCommit(true); // возвращаем автокоммит
        }
    }

}