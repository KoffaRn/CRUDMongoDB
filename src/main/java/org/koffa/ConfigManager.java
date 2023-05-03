package org.koffa;
import java.io.FileReader;
import java.util.Properties;

public class ConfigManager {
    private final String connectionUrl;
    private final String cluster;
    public ConfigManager() {
        String tmpUrl = "mongodb+srv://";
        String username = readConfig("username");
        String password = readConfig("password");
        String url = readConfig("url");
        String cluster = readConfig("cluster");
        if(username != null) {
            if(!username.isBlank())tmpUrl += username;
            if(password != null){
                if(!password.isBlank()) tmpUrl += ":" + password + "@";
                else tmpUrl += "@";
            }
        }
        if(cluster != null) {
            if(!cluster.isBlank()) {
                tmpUrl += cluster + ".";
                this.cluster = cluster;
            }
            else this.cluster = "Cluster0";
        }
        else this.cluster = "Cluster0";
        if(url != null) {
            if(!url.isBlank()) tmpUrl += url;
            else tmpUrl = "mongodb://localhost:27017";
        }
        else tmpUrl = "mongodb://localhost:27017";
        System.out.println(tmpUrl);
        this.connectionUrl = tmpUrl;
    }
    public String getConnectionUrl() {
        return connectionUrl;
    }
    public String getCluster() {
        return cluster;
    }
    private String readConfig(String property) {
        Properties properties = new Properties();
        try {
            FileReader fr = new FileReader("mongodb.config");
            properties.load(fr);
        } catch (Exception e) {
            System.err.println("Could not read config file");
        }
        return properties.getProperty(property);
    }
}
