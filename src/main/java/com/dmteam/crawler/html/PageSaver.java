package com.dmteam.crawler.html;

import java.io.IOException;

/**
 * Created by Administrator on 2014/11/27.
 */
public interface PageSaver {

    void setRootDir(String dir);

    void generateFile(PageContext context);

    void doSave(PageContext context, String content) throws IOException;
}
