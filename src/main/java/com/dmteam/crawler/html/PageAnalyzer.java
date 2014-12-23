package com.dmteam.crawler.html;

import java.io.InputStream;
import java.util.List;

/**
 * Created by xh on 2014/11/24.
 */
public interface PageAnalyzer {

    List<PageContext> extractUrls(String source);

    List<PageContext> extractUrls(InputStream source);

    PageContext extractInfo(String url);
}
