package db;

import java.sql.*;

public class ConnectionManager {

    private final Connection connection;
    private final Statement statement;

    private static ConnectionManager instance;

    public static ConnectionManager getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionManager();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    // возвращает единственный экземпляр класса ConnectionManager
    private ConnectionManager() throws SQLException {
        try {
            connection  = DriverManager.getConnection(
//                    "jdbc:postgresql://postgres:iqtqniUnQaOBFGfyLHthOMMawoFDkAaL@shortline.proxy.rlwy.net:51726/railway",
//                    "postgres",
//                    "iqtqniUnQaOBFGfyLHthOMMawoFDkAaL");
                     "jdbc:postgresql://sql.otus.kartushin.su:5432/stage",
                     "student",
                     "student");
        } catch (Exception e) {
            throw new RuntimeException("Ошибка подключения к базе данных: " + e.getMessage(), e);
        }

        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // для получения соединения
    public Connection getConnection() {
        return connection;
    }

    // для выполнения SELECT-запросов, возвращающих набор результатов
    public ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // для выполнения UPDATE, INSERT и DELETE-запросов, без возвращающих набор результатов
    public void executeUpdate(String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
