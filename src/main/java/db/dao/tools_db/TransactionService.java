package db.dao.tools_db;

import db.ConnectionManager;
import db.dao.AbstractTable;

import java.sql.SQLException;


public class TransactionService extends AbstractTable {
    private final ConnectionManager connectionManager;

    public TransactionService(ConnectionManager connectionManager, ConnectionManager connectionManager1) {
        super(connectionManager);
        this.connectionManager = connectionManager1;
    }


    // работа с транзакциями
    public void executeInTransaction(Runnable action) throws SQLException {
        connectionManager.getConnection().setAutoCommit(false); // начиная транзакцию отключаем автокоммит на случай работы с postgre
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
