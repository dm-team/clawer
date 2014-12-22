package com.dmteam.crawler.html.impl;

import com.dmteam.crawler.html.UrlExtractor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yak on 2014/12/15.
 */
public class PeopleNetUrlExtractor extends UrlExtractor{
    private static Pattern regexPattern = Pattern.compile("<a href=\"http://\\S*\"");

    private static final Set<String> INTEREST_DOMAIN = new HashSet<String>();

    static {
        String[] domains = new String[]{
                "news.people.com.cn","politics.people.com.cn",
                "society.people.com.cn","legal.people.com.cn",
                "chinese.people.com.cn","leaders.people.com.cn",
                "expo.people.com.cn","invest.people.com.cn",
                "travel.people.com.cn","finance.people.com.cn",
                "energy.people.com.cn","health.people.com.cn",
                "house.people.com.cn","shipin.people.com.cn",
                "ccnews.people.com.cn/","people.com.cn"

        };
        for (String s : domains) INTEREST_DOMAIN.add(s);
    }

    @Override
    public List<String> extractUrls(String source) {

        Matcher matcher = regexPattern.matcher(source);

        List<String> result = new ArrayList<String>();

        while (matcher.find())//查找符合pattern的字符串
        {
            String s = matcher.group();

            s = s.substring(s.indexOf("href=\"") + "href=\"".length());

            s = s.substring(0, s.indexOf("\""));

            if (!isInterestDomain(s)) continue;

            System.out.println(s);
            result.add(s);
        }


        return result;
    }

    @Override
    public List<String> extractUrls(InputStream source) {
        return null;
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
