package db;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;

public class ConnectionManager {

    private final Connection connection;
    private final Statement statement;

    private static ConnectionManager instance;

    private static final String CONF_FILE = "configsDB.conf";

    public static ConnectionManager getInstance() {
        if (instance == null) {
            try {
                instance = new ConnectionManager();
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    // обращаемся к файлу
    private Config loadConfig() throws IOException {
        Config config = ConfigFactory.load(CONF_FILE);

        if (!config.hasPath("dbParams")) {
            throw new FileNotFoundException("Отсутствует секция 'dbParams' в файле конфигурации '" + CONF_FILE + "'.");
        }
        return config.getConfig("dbParams");
    }

    private ConnectionManager() throws SQLException, IOException {
        Config config = loadConfig();

        // получаем необходимые значения из конфига по имени и времени
        String envName = System.getenv("ENV_NAME");
        if (envName == null || !config.hasPath(envName)) {
            Calendar calendar = Calendar.getInstance(); // берем библиотеку календаря
            int currentMinute = calendar.get(Calendar.MINUTE); // получим текущие минуты

            // выбираем БД по текущему времени внутри часа
            if (currentMinute >= 0 && currentMinute <= 19) {
                envName = "mysql";
            } else if (currentMinute >= 20 && currentMinute <= 39) {
                envName = "postgre";
            }
            else {
                envName = "kartushin";
            };
        }

        Config envConfig = config.getConfig(envName);
        String url = envConfig.getString("url");
        String login = envConfig.getString("login");
        String password = envConfig.getString("password");

        if (url == null || login == null || password == null) {
            throw new IllegalStateException("Настройки подключения к базе данных отсутствуют.");
        }

        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка подключения к базе данных: " + e.getMessage(), e);
        }

        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException();
        }
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