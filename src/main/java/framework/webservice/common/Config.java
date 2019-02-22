package framework.webservice.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static String host,baseUrl;
    private static Config instance;
    final private static Object lockInstance = new Object();
    private static Properties configuration;

    private Config() throws IOException {

        loadConfigFileFromResource();
        host = getConfigValue("HOST");
        baseUrl=getConfigValue("baseUrl");

    }

    /**
     * Returns an instance of the Service Config class.
     *
     * @return Instance of Service Config
     * @throws IOException
     */
    public static Config getInstance() throws IOException {
        synchronized (lockInstance) {
            if (instance == null) {
                instance = new Config();
            }
        }

        return instance;
    }

    private void loadConfigFileFromResource() throws IOException {
        final InputStream in = this.getClass().getResourceAsStream("/config.properties");

        configuration = new Properties();

        if (in == null) {
            throw new FileNotFoundException("Resource file \"config.properties\" mevcut degil.");
        }

        configuration.load(in);

        in.close();
    }

    private String getConfigValue(final String keyPath) {
        final String completeKeyName =keyPath;
        final String completeKeyValue = configuration.getProperty(completeKeyName);

        if (completeKeyValue == null) {
            throw new IllegalArgumentException("Talep edilen \"" + completeKeyName + "\"bulunamadi");
        }
        return completeKeyValue;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
