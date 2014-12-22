package com.dmteam.extractor.impl;

import com.dmteam.extractor.ArticleContentExtractor;
import com.dmteam.utils.HtmlTools;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/12/1.
 */
public class QQExtractor implements ArticleContentExtractor {

    private static final Pattern p1 = Pattern.compile("<(.*?)>");
    private static final Pattern p2 = Pattern.compile("\\s+");


    public String extract(String rawContent) {

        String ctt = HtmlTools.extractTag(rawContent, "<div id=\"Cnt-Main-Article-QQ\"", "div");

        if (ctt.isEmpty()) return ctt;

        // p标签 大小写
        List<String> strings = HtmlTools.extractContentByTagName(ctt, "p");
        strings.addAll(HtmlTools.extractContentByTagName(ctt, "P"));

        StringBuilder r = new StringBuilder();
        for (String s : strings) {
            s = HtmlTools.removeTagByName(s, "script");

            Matcher m1 = p1.matcher(s);
            s = m1.replaceAll(" ");

            Matcher m2 = p2.matcher(s);
            s = m2.replaceAll(" ");

            r.append(s).append("\n");
        }

        return r.toString();
    }
}
