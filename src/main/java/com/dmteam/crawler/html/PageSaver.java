package com.dmteam.crawler.html;

import java.io.IOException;

/**
 * Created by Administrator on 2014/11/27.
 */
public interface PageSaver {

    void setDir(String dir);

    String generateFileName(PageContext context);

    void doSave(String pageFileName, String content) throws IOException;
}
