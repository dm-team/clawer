package com.dmteam.crawler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yak on 2014/12/16.
 */
public class VisitedTable {

    private ConcurrentHashMap<String, Object> visitUrl = new ConcurrentHashMap();

    public boolean visit(String url) {
        if (null == visitUrl.putIfAbsent(url, 0)) return true;
        return false;
    }
}
