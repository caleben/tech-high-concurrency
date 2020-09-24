package tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author wuenci
 * @date 2020/9/3
 * @since 2.0.0
 */
public class ConfigProperties {
    private String fileName;
    private Properties properties = new Properties();

    public ConfigProperties(String fileName) {
        this.fileName = fileName;
    }

    public ConfigProperties(Properties properties) {
        this.properties = properties;
    }

    protected void load() {
        try (InputStream in = ConfigProperties.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key, "");
    }

    public int getIntValue(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public int getAndComputeNumWithStart(String key) {
        String s = getValue(key);
        return Arrays.stream(s.split("\\*")).map(Integer::parseInt).reduce((a, b) -> a * b).orElse(1024 * 10);
    }
}
