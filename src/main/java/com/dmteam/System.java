package com.dmteam;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by xh on 2014/12/23.
 */
public class System {

    static Properties p = new Properties();

    static {
        try {
            p.load(new FileInputStream(
                    java.lang.System.getProperty("user.dir") + "/config/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String s){
        return (String)p.get(s);
    }

}
