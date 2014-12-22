package com.dmteam.crawler.html.impl;

import com.dmteam.crawler.html.SimplePageSaver;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by yak on 2014/12/12.
 */
public class PeopleNetPageSaver extends SimplePageSaver {

    private static Pattern fileNamePattern =
            Pattern.compile("^(.*).people.com.cn_n_\\d{4}_\\d{4}(_c\\d+-\\d{8})?.html.txt$");

    @Override
    public void doSave(String pageFileName, String content) throws IOException {

        if (!fileNamePattern.matcher(pageFileName).matches()) {
            return;
        }

        super.doSave(pageFileName, content);
    }
}
