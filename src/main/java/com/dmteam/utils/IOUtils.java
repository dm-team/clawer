package com.dmteam.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2014/12/1.
 */
public class IOUtils {

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
