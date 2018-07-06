package com.dmteam.crawler.html.impl;

import com.dmteam.crawler.html.PageAnalyzer;
import com.dmteam.crawler.html.PageContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yak on 2014/12/15.
 */
public class PeopleNetPageAnalyzer implements PageAnalyzer {

    static Logger logger = LoggerFactory.getLogger(PeopleNetPageAnalyzer.class);

    private static Pattern regexPattern = Pattern.compile("<a href=\"http://\\S*\"");

    private static Pattern articleUrlPattern =
            Pattern.compile("^(.*).people.com.cn(/.+)?/n/\\d{4}/\\d{4}/c\\d+-\\d{8}(-\\d+)?.html$");

    public static final Set<String> INTEREST_DOMAIN = new HashSet<String>();

    static {
        String[] domains = new String[]{
                "news.people.com.cn","politics.people.com.cn",
                "society.people.com.cn","legal.people.com.cn",
                "chinese.people.com.cn","leaders.people.com.cn",
                "expo.people.com.cn","invest.people.com.cn",
                "travel.people.com.cn","finance.people.com.cn",
                "energy.people.com.cn","health.people.com.cn",
                "house.people.com.cn","shipin.people.com.cn",
                "ccnews.people.com.cn","people.com.cn",
                "edu.people.com.cn"

        };
        for (String s : domains) INTEREST_DOMAIN.add(s);
    }

    public List<PageContext> extractUrls(String source) {

        Matcher matcher = regexPattern.matcher(source);

        List<PageContext> result = new ArrayList<PageContext>();

        while (matcher.find())//查找符合pattern的字符串
        {
            String s = matcher.group();

            s = s.substring(s.indexOf("href=\"") + "href=\"".length());

            s = s.substring(0, s.indexOf("\""));

            if (!isInterestDomain(s)) continue;


            result.add(extractInfo(s));
        }


        return result;
    }

    public List<PageContext> extractUrls(InputStream source) {
        return null;
    }

    public PageContext extractInfo(String url) {

        PageContext pageContext = new PageContext(url);
        pageContext.isArticle = articleUrlPattern.matcher(url).matches();

        if (!pageContext.isArticle) return pageContext;

        String s = "/n/";
        int p = url.indexOf(s) + s.length();

        //http://theory.people.com.cn/abc/n/2014/1222/c148980-26249744.html
        url = url.substring(p);

        String year = url.substring(0, 4);
        String monthDate = url.substring(5, 9);

        try {
            pageContext.pageDate = DateUtils.parseDate(year+monthDate, new String[]{"yyyyMMdd"});

        } catch (ParseException e) {
            logger.error("Parse Date Exception", e);
        }

        return pageContext;
    }

    private boolean isInterestDomain(String s) {

        // 只要静态页面
        if (s.contains("?") || s.contains("#") || s.contains("&")) return false;

        s = s.substring("http://".length());

        int p = s.indexOf("/");
        if (p > 0) {
            s = s.substring(0, p);
        }

        return s.contains(".people.com.cn");
    }
}
