package com.cemonan.blog.utils;

import com.cemonan.blog.exceptions.CannotResolveApplicationPropertiesPathException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertyExtractor {

    private static Properties properties;

    static {
        properties = new Properties();
        URL url = new PropertyExtractor().getClass().getClassLoader().getResource("application.properties");
        try {
            properties.load(new FileInputStream(url.getPath()));
        } catch (IOException ex) {
            CannotResolveApplicationPropertiesPathException ex2 = new CannotResolveApplicationPropertiesPathException(ex.getMessage());
            ex2.setStackTrace(ex.getStackTrace());
            throw ex2;
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
