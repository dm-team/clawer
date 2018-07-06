package com.dmteam.crawler.html;

import org.apache.commons.lang.time.DateFormatUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 网页存储：设置存储路径；文件名设置基于网页url；存储内容
 * Created by Administrator on 2014/11/27.
 */
public class SimplePageSaver implements PageSaver {

    private File dirFile;

    private File visitedDir;

    public void setRootDir(String dir) {
        if (dir.endsWith("/")) {
            this.dirFile = new File(dir.substring(0, dir.length() - 1));
        } else {
            this.dirFile = new File(dir);
        }
        File f = this.dirFile;
        if (!f.exists()) {
            f.mkdirs();
        }else{
            f.delete();
            f.mkdirs();
        }

        visitedDir = new File(this.dirFile, "visited");
        f = this.visitedDir;
        if (!f.exists()) {
            f.mkdirs();
        }else{
            f.delete();
            f.mkdirs();
        }
    }

    public void generateFile(PageContext pc) {

        String url = pc.pageUrl;

        if (url.startsWith("http://")) {
            url = url.substring(7);
        }

        url = url.replace("/", "_");

        if (!pc.isArticle) {
            pc.desFile = new File(visitedDir, url);
            return;
        }

        pc.desFile = new File(new File(this.dirFile, DateFormatUtils.format(pc.pageDate, "yyMM")), url);
    }

    public void doSave(PageContext pc, String content) throws IOException {

        FileWriter fileWriter = null;
        try {

            fileWriter = new FileWriter(pc.desFile);
            fileWriter.write(content);

        } finally {
            fileWriter.close();
        }
    }
}
