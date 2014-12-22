package com.dmteam.utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/27.
 */
public class HtmlTools {

    /**
     * "start<id c='11'> sdfs----<id a=1>xx<id b=2>mm</id></id>--,--</id> end<id 0=0>ss</id>",
     "start<id c='11'> sdfs------,--/id> end",
     "<id c='11'> sdfs------,--/id> end",
     "<id c='11'> sdfs------,--</id>",

     * @param source
     * @param ident
     * @param tag
     * @return
     */
//
    public static String removeTag(String source, String ident, String tag) {

        Map.Entry<Integer, Integer> entry = tagRange(source, ident, tag);

        if (entry == null) return source;

        return source.substring(0, entry.getKey()) + source.substring(entry.getValue());
    }

    public static String extractTag(String source, String ident, String tag) {

        Map.Entry<Integer, Integer> entry = tagRange(source, ident, tag);

        if (entry == null) return "";

        return source.substring(entry.getKey(), entry.getValue());
    }

    private static Map.Entry<Integer, Integer> tagRange(String source, String ident, String tag) {

        // TODO find the last one
        int identStart = source.lastIndexOf(ident);

        if (identStart == -1) return null;

        int ns = 1;
        int searchStart = identStart + ident.length();
        int identEnd;

        while (ns > 0) {

            int p = source.indexOf(tag, searchStart);
            if (p < 0) {
                break;
            }

            int tagType = tagType(source, tag, p);

            if (tagType == 0) {
                searchStart = p + tag.length();
                continue;
            }

            if (tagType > 0) ns++;
            else ns --;

            searchStart = p + tag.length() + 1;
        }

        if (ns > 0) identEnd = source.length();
        else identEnd = searchStart;

        return new AbstractMap.SimpleEntry<Integer, Integer>(identStart, identEnd);

    }

    // 1: start
    // -1: end
    // 0: not tag
    private static int tagType(String source, String tag, int p) {

        if (p-2 < 0 ) return 0;

        int nextPos = p + tag.length();

        if (nextPos >= source.length()) return 0;

        if (source.charAt(p-1) == '<' && source.charAt(nextPos) == ' ')
            return 1;

        if (source.charAt(p-2) == '<' && source.charAt(p-1) == '/' && source.charAt(nextPos) == '>')
            return -1;

        return 0;
    }

    /**
     * 不支持标签嵌套 可用来删除 <script> <style> 标签
     * @param source
     * @param tagName
     * @return
     */
    public static String removeTagByName(String source, String tagName) {

        StringBuilder sb = new StringBuilder();

        int lastPos = 0;

        String tagStart = "<"+tagName;
        String tagEnd = "</"+tagName+">";

        while (true) {
            int st;
            if ((st = source.indexOf(tagStart, lastPos)) < 0) {
                break;
            }

            sb.append(source.substring(lastPos, st));

            int en = source.indexOf(">", st + tagStart.length());
            if (en < 0) {
                lastPos = -1;
                break;
            }

            if (source.charAt(en-1) == '/') {
                lastPos = en+1;
                continue;
            }

            if ((en = source.indexOf(tagEnd, st + tagStart.length())) < 0) {
                lastPos = -1;
                break;
            }

            lastPos = en + tagEnd.length();
        }

        if (lastPos >= 0) {
            sb.append(source.substring(lastPos));
        }

        return sb.toString();
    }

    /**
     * 不支持标签嵌套
     * eg. <p>abc<p/><p>123</p> => abc 123
     * @param source
     * @param tagName
     * @return
     */
    public static List<String> extractContentByTagName(String source, String tagName) {

        List<String> result = new ArrayList<String>();

        int lastPos = 0;

        String tagStart = "<"+tagName;
        String tagEnd = "</"+tagName+">";

        while (true) {
            int st;
            if ((st = source.indexOf(tagStart, lastPos)) < 0) {
                break;
            }

            {
                char c = source.charAt(st + tagStart.length());
                if (!Character.isSpaceChar(c) && c != '>') {
                    lastPos = st + tagStart.length();
                    continue;
                }
            }

            int sEn = source.indexOf(">", st + tagStart.length());
            if (sEn < 0) {
                lastPos = -1;
                break;
            }

            if (source.charAt(sEn-1) == '/') {
                lastPos = sEn+1;
                continue;
            }

            int eSt;
            if ((eSt = source.indexOf(tagEnd, sEn+1)) < 0) {
                lastPos = -1;
                break;
            }

            result.add(source.substring(sEn+1, eSt));

            lastPos = eSt + tagEnd.length();
        }

        return result;
    }


    public static void main(String[] args) {

//        String[] tests = new String[] {
//                "start<id c='11'> sdfs----<id a=1>xx<id b=2>mm</id></id>--,--</id> end<id 0=0>ss</id>",
//                "start<id c='11'> sdfs------,--/id> end",
//                "<id c='11'> sdfs------,--/id> end",
//                "<id c='11'> sdfs------,--</id>",
//        };
//
//        for (String test : tests) {
//            System.out.println(HtmlTools.removeTag(test, "<id c='11'>", "id"));
//        }


//        String[] tests = new String[] {
//                "start<id c='11'> sdfs----<id a=1>xx<id b=2>mm</id></id>--,--</id> end<id 0=0>ss</id>",
//                "start<id c='11'/> sdfs------,--/id> end<id>a<</id><id/>",
//                "<id c='11'> sdfs------,--/id> end",
//                "<id c='11'> sdfs------,--</id>",
//        };
//
//        for (String test : tests) {
//            System.out.println(HtmlTools.removeTagByName(test, "id"));
//        }


        String[] tests = new String[] {
                "start<id c='11'> sdfs----<id a=1>xx<id b=2>mm</id></id>--,--</id> end<id 0=0>ss</id>",
                "start<id c='11'/> sdfs------,--/id> end<id>a<</id><id/>",
                "<id c='11'> sdfs------,--/id> end",
                "<id c='11'> sdfs------,--</id>",
        };

        for (String test : tests) {
            System.out.println("#############################");
            for (String s : HtmlTools.extractContentByTagName(test, "id")) {
                System.out.println(s);
            }

        }
    }
}
