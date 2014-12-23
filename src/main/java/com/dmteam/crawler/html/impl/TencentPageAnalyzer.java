package com.dmteam.crawler.html.impl;

import com.dmteam.crawler.html.PageAnalyzer;
import com.dmteam.crawler.html.PageContext;

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
//public class TencentPageAnalyzer implements PageAnalyzer {
//
//    private static Pattern regexPattern = Pattern.compile("<a href=\"http://\\S*\"");
//
//    private static final Set<String> INTEREST_DOMAIN = new HashSet<String>();
//
//    static {
//        String[] domains = new String[]{
//                "news.qq.com",
//                "tech.qq.com",
//                "sports.qq.com",
//                "cul.qq.com",
//                "ent.qq.com",
//                "health.qq.com",
//                "hb.qq.com",
//        };
//        for (String s : domains) INTEREST_DOMAIN.add(s);
//    }
//
//    @Override
//    public List<String> extractUrls(String source) {
//
//        Matcher matcher = regexPattern.matcher(source);
//
//        List<String> result = new ArrayList<String>();
//
//        while (matcher.find())//查找符合pattern的字符串
//        {
//            String s = matcher.group();
//
//            s = s.substring(s.indexOf("href=\"") + "href=\"".length());
//
//            s = s.substring(0, s.indexOf("\""));
//
//            if (!isInterestDomain(s)) continue;
//
////            System.out.println(s);
//            result.add(s);
//        }
//
//        return result;
//    }
//
//    @Override
//    public List<String> extractUrls(InputStream source) {
//        return null;
//    }
//
//    @Override
//    public PageContext extractInfo(String url) {
//        return new PageContext(url);
//    }
//
//    private boolean isInterestDomain(String s) {
//
//        // 只要静态页面
//        if (s.contains("?") || s.contains("#") || s.contains("&&")) return false;
//
//        s = s.substring("http://".length());
//
//        int p = s.indexOf("/");
//        if (p > 0) {
//            s = s.substring(0, p);
//        }
//
//        return INTEREST_DOMAIN.contains(s);
//    }
//}
