package configuration;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationHelper {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadFile();
    }

    @SneakyThrows
    public static void loadFile() {
        FileInputStream input = new FileInputStream("src/main/resources/config.properties");
        PROPERTIES.load(input);
    }

    public static String getBaseUrl() {
        return PROPERTIES.getProperty("base.url");
    }

    public static String getBasePath() {
        return PROPERTIES.getProperty("base.path");
    }
}