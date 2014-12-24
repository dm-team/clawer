package com.dmteam.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2014/12/1.
 */
public class IOUtils {

    static Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                logger.error("IOUtils close error", e);
            }
        }
    }
}
