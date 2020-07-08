package com.efisher.testtaskagileengine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties getProperties() throws IOException {
        String filePath = "src\\main\\resources\\application.properties";

        FileInputStream fis = null;
        Properties props = null;
        try {
            fis = new FileInputStream(filePath);
            props = new Properties();
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }

        return props;
    }
}
