package dev.drinaissante.util;

import dev.drinaissante.Main;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    public static final String API_URL;
    public static final String API_KEY;

    static {
        try {
            Properties props = new Properties();
            props.load(Main.class.getResourceAsStream("/config.properties"));

            API_URL = props.getProperty("api.url");
            API_KEY = props.getProperty("api.key");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
