package com.dmteam.crawler.html;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xh on 2014/11/24.
 */
public abstract class UrlExtractor {

    public abstract List<String> extractUrls(String source);

    public abstract List<String> extractUrls(InputStream source);



    static UrlExtractor create(String type, String pattern) {
        if ("regex".equals(type)) {
            return new RegexUrlExtractor(pattern);
        }
        return null;
    }

    private static class RegexUrlExtractor extends UrlExtractor {

        private String pattern;

        RegexUrlExtractor(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public List<String> extractUrls(String source) {

            Pattern pattern = Pattern.compile(this.pattern);
            Matcher matcher = pattern.matcher(source);

            List<String> result = new ArrayList<String>();

            while (matcher.find())//查找符合pattern的字符串
            {
                String s = matcher.group();

                s = s.substring(s.indexOf("href=\"") + "href=\"".length());

                s = s.substring(0, s.indexOf("\""));

                result.add(s);
            }


            return result;
        }

        @Override
        public List<String> extractUrls(InputStream source) {

            InputStreamReader reader = new InputStreamReader(source);

            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            try {
                while (reader.read(buffer) > 0) {
                    sb.append(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return extractUrls(sb.toString());
        }

    }
}
