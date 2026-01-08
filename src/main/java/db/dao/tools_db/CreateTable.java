package db.dao.tools_db;

import db.ConnectionManager;
import db.dao.AbstractTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CreateTable extends AbstractTable {
    public CreateTable(ConnectionManager connectionManager) {
        super(connectionManager);
    }

    // создание таблицы при условии ее отсутствия
    public void createTable(String sqlCreateCommand) throws SQLException {
        try (PreparedStatement pstmt = ConnectionManager.getInstance().getConnection().prepareStatement(sqlCreateCommand)) {
            pstmt.execute();
        }
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

    protected void createTableIfNotExists(String tableName, String createSql) throws SQLException {
        if (!exists(tableName)) {
            createTable(createSql);
            System.out.println("Таблица " + tableName + " успешно создана.");
        }
    }
}
