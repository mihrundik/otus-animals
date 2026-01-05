package db.dao;

import db.ConnectionManager;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractTable {
    final ConnectionManager connectionManager;

    public AbstractTable(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    // CRUD
    protected abstract void createTable(String sqlCreateCommand) throws SQLException;

    protected abstract void insertData(Object data) throws SQLException;

    protected abstract List<String[]> readData(String type) throws SQLException;

    protected abstract void updateData(int id, String data) throws SQLException;  // принимает строку для изменения по ИД

    protected abstract void deleteData(int id) throws SQLException;  // не реализовано - отстутствует в задании


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