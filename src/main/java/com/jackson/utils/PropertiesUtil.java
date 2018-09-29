package com.jackson.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Create by: Jackson
 */
public class PropertiesUtil {

    public static Properties loadProperties(File file) {
        Properties props = new Properties();

        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (!newFile)
                    throw new RuntimeException("创建配置文件失败:" + file.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(file.getAbsolutePath(),e);
            }
        }
        try (InputStream inStream = new FileInputStream(file)) {
            props.load(inStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return props;
    }
}
