package com.dmteam.crawler.html.impl;

import com.dmteam.crawler.html.SimplePageSaver;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/11/28.
 */
//public class TencentPageSaver extends SimplePageSaver {
//
//    //news.qq.com_a_20141128_005393.htm
//    private static Pattern fileNamePattern = Pattern.compile("^(.*).qq.com_a_\\d{8}_\\d+(_\\d{1,2})?.htm.txt$");
//
//    @Override
//    public void doSave(String pageFileName, String content) throws IOException {
//
//        if (!fileNamePattern.matcher(pageFileName).matches()) {
//            return;
//        }
//
//        super.doSave(pageFileName, content);
//    }
//}
