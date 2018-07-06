package com.dmteam.crawler.html.impl;

import com.dmteam.crawler.html.PageContext;
import com.dmteam.utils.HtmlTools;
import com.dmteam.crawler.html.PageAnalyzer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2014/11/27.
 */
public class NetEasePageAnalyzer implements PageAnalyzer {

    private static Pattern regexPattern = Pattern.compile("<a href=\"http://\\S*\">");


    private static final Set<String> INTEREST_DOMAIN = new HashSet<String>();

    static {
        String[] domains = new String[]{
                "news.163.com", "sports.163.com", "tech.163.com", "travel.163.com",
                "ent.163.com", "edu.163.com",
                "money.163.com",
                "wh.house.163.com",
                "data.163.com",
                "view.163.com",
                "war.163.com",
                "bbs.163.com",
                "bbs.news.163.com",
                "auto.163.com",
                "lady.163.com",
                "digi.163.com",
                "mobile.163.com",
                "game.163.com"
        };
        for (String s : domains) INTEREST_DOMAIN.add(s);
    }

    public List<PageContext> extractUrls(String source) {

        // TODO 去掉一些解析上的干扰
        source = HtmlTools.removeTag(source, "<div class=\"ntes-nav-main clearfix\">", "div");

        Matcher matcher = regexPattern.matcher(source);

        List<PageContext> result = new ArrayList<PageContext>();

        while (matcher.find())//查找符合pattern的字符串
        {
            String s = matcher.group();

            s = s.substring(s.indexOf("href=\"") + "href=\"".length());

            s = s.substring(0, s.indexOf("\""));

            if (!isInterestDomain(s)) continue;

//            System.out.println(s);
            result.add(extractInfo(s));
        }


        return result;
    }

    public List<PageContext> extractUrls(InputStream source) {
        return null;
    }

    public PageContext extractInfo(String url) {
        return new PageContext(url);
    }

    private boolean isInterestDomain(String s) {

        // 只要静态页面
        if (s.contains("?") || s.contains("#") || s.contains("&")) return false;

        s = s.substring("http://".length());

        int p = s.indexOf("/");
        if (p > 0) {
            s = s.substring(0, p);
        }

        return INTEREST_DOMAIN.contains(s);
    }
}