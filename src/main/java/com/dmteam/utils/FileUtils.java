package com.dmteam.utils;

import sun.misc.IOUtils;

import java.io.*;

/**
 * Created by Administrator on 2014/12/1.
 */
public class FileUtils {

    public static String readToString(String fileName, String charset) throws IOException {

        return readToString(new File(fileName), charset);
    }

    public static String readToString(File file, String charset) throws IOException {

        FileInputStream fis = null;
        try {
            return new String(IOUtils.readFully((fis = new FileInputStream(file)), -1, true), charset);
        } finally {
            com.dmteam.utils.IOUtils.close(fis);
        }

    }

    public static void writeToFile(String fileName, String s, String charset) throws IOException {

        writeToFile(new File(fileName), s, charset);
    }

    public static void writeToFile(File file, String s, String charset) throws IOException {

        OutputStreamWriter outputStreamWriter = null;

        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), charset);

            outputStreamWriter.write(s);
        } finally {
            com.dmteam.utils.IOUtils.close(outputStreamWriter);
        }

    }
}
