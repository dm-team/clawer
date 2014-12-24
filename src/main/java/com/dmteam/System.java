package com.dmteam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by xh on 2014/12/23.
 */
public class System {

    public static Logger logger = LoggerFactory.getLogger(System.class);

    static Properties p = new Properties();

    static {
        try {
            p.load(new FileInputStream(
                    java.lang.System.getProperty("user.dir") + "/config/config.properties"));
        } catch (IOException e) {
            logger.error("load config error", e);
        }
    }

    public static String get(String s){
        return (String)p.get(s);
    }

}
