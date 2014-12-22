package com.dmteam.crawler.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 网页存储：设置存储路径；文件名设置基于网页url；存储内容
 * Created by Administrator on 2014/11/27.
 */
public class SimplePageSaver implements PageSaver {

    private String dir;

//    private int serialNumber;

    @Override
    public void setDir(String dir) {
        if (dir.endsWith("/")) {
            this.dir = dir;
        } else {
            this.dir = dir + File.separator;
        }
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }else{
            f.delete();
            f.mkdirs();
        }
    }

    @Override
    public String generateFileName(PageContext pc) {

        String url = pc.pageUrl;

        if (url.startsWith("http://")) {
            url = url.substring(7);
        }

        url = url.replace("/", "_");
        return url + ".txt";
    }

    @Override
    public void doSave(String pageFileName, String content) throws IOException {

        FileWriter fileWriter = null;
        try {
            File f = new File(dir + pageFileName);

           fileWriter = new FileWriter(f);
            String pageUrl = pageFileName.substring(0,pageFileName.indexOf(".txt"));
            pageUrl = pageUrl.replace("_","/");

            fileWriter.write(pageUrl);

            fileWriter.write(content);

        } finally {
            fileWriter.close();
        }

    }
}
