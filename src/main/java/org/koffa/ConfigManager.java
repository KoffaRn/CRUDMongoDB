package org.koffa;
import java.io.FileReader;
import java.util.Properties;

public class ConfigManager {
    private final String connectionUrl;
    public ConfigManager() {
        String tmpUrl = "mongodb+srv://";
        String username = readConfig("username");
        String password = readConfig("password");
        String url = readConfig("url");
        if(!username.isBlank()) {
            tmpUrl += username;
            if(!password.isBlank()){
                tmpUrl += ":" + password;
            }
            tmpUrl += "@";
        }
        if(!url.isBlank()) tmpUrl += url;
        else tmpUrl += "localhost:27017";
        this.connectionUrl = tmpUrl;
    }
    public String getConnectionUrl() {
        return connectionUrl;
    }
    private String readConfig(String property) {
        Properties properties = new Properties();
        try {
            FileReader fr = new FileReader("mongodb.config");
            properties.load(fr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties.getProperty(property);
    }
}
