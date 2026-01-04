package db;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;
import java.util.Calendar;

public interface ProviderConfig {

    // Используйте имя файла, без абсолютного пути
    String CONF_FILE = "configsDB.conf";

    // обращаемся к файлу
    static Config loadConfig() throws IOException {
        // Загружаем конфигурацию из файла в classpath
        Config config = ConfigFactory.load(CONF_FILE);

        // Проверяем наличие секции 'dbParams'
        if (!config.hasPath("dbParams")) {
            throw new IOException("Отсутствует секция 'dbParams' в файле конфигурации '" + CONF_FILE + "'.");
        }
        return config.getConfig("dbParams");
    }

    static String readConfig() throws IOException {
        Config config = loadConfig();

        // получаем необходимые значения из конфига по имени и времени
        String envName = System.getenv("ENV_NAME");
        if (envName == null || !config.hasPath(envName)) {
            Calendar calendar = Calendar.getInstance(); // берем библиотеку календаря
            int currentMinute = calendar.get(Calendar.MINUTE); // получим текущие минуты

            // выбираем БД по текущему времени внутри часа

            if (currentMinute <= 30) {
                envName = "mysql";
            } else {
                envName = "kartushin";
            }
        }

        return envName;
    }

}
