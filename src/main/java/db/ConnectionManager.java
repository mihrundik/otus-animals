package db;

import com.typesafe.config.Config;

import java.io.IOException;
import java.sql.*;

import static db.tools_db.ProviderConfig.loadConfig;
import static db.tools_db.ProviderConfig.readConfig;

public class ConnectionManager {

    private Connection connection;
    private Statement statement;
    private static volatile ConnectionManager instance;

    public ConnectionManager(String envName) throws IOException {

        Config config = loadConfig();

        Config envConfig = config.getConfig(envName);
        String url = envConfig.getString("url");
        String login = envConfig.getString("login");
        String password = envConfig.getString("password");

        if (url == null || login == null || password == null) {
            throw new IllegalStateException("Настройки подключения к базе данных отсутствуют.");
        }

        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных: " + e.getMessage(), e);
        }

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания statement ", e);
        }
    }

    public ConnectionManager() {

    }

    // реализация Singleton - гарантия создания одного экземпляра класса - synchronized - метод сможет выполняться только одним потоком
    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            try {
                String envName = readConfig();
                instance = new ConnectionManager(envName);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось загрузить конфигурацию ", e);
            }
        }
        return instance;
    }


    public Connection getConnection() {
        return connection;
    }

    public ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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