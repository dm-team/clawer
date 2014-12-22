package com.dmteam.crawler;

import com.dmteam.crawler.html.PageContext;
import com.dmteam.crawler.html.PageSaver;
import com.dmteam.crawler.html.UrlExtractor;
import com.dmteam.crawler.html.Stopable;
import com.dmteam.crawler.net.HttpPageFetcher;
import com.dmteam.extractor.ArticleContentExtractor;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/11/27.
 */
public class Spider implements Runnable, Stopable {

    private String rootUrl;

    private ArticleContentExtractor articleContentExtractor;

    private PageSaver saver;

//    private ConcurrentHashMap<String, Object> visitUrl = new ConcurrentHashMap();

    private VisitedTable vt;

    private HttpPageFetcher fetcher;

    private int maxDepth = DEFAULT_DEPTH;

    private UrlExtractor urlExtractor;

//    public static ExecutorService es = Executors.newFixedThreadPool(1);

    public static final int DEFAULT_DEPTH = 5;

    public Spider(String rootUrl, VisitedTable vt) {
        this.rootUrl = rootUrl;
        this.vt = vt;
    }


    public void goBfs() {

        List<String> bfsUrls = new ArrayList<String>(1);
        bfsUrls.add(rootUrl);

        int curDepth = maxDepth;
        while (curDepth > 0) {
            System.out.println("curDepth: " + curDepth);

            List<String> tmpBfsUrls = new ArrayList<String>();
            for (String url : bfsUrls) {

                if (!vt.visit(url)) continue;

                try {
                    System.out.println("current url: " + url);

                    String fetchHtml = fetcher.fetch(url);

                    List<String> urls = urlExtractor.extractUrls(fetchHtml);

                    tmpBfsUrls.addAll(urls);

                    PageContext pc = new PageContext(url);

                    String contentForSave = fetchHtml;
                    if (articleContentExtractor != null) {
                        contentForSave = articleContentExtractor.extract(fetchHtml);
                    }
                    if(StringUtils.isNotBlank(contentForSave)||contentForSave.length()>=30)
                        saver.doSave(saver.generateFileName(pc), contentForSave);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            bfsUrls = tmpBfsUrls;
            curDepth--;
        }
    }

//    private boolean visit(String url) {
//        if (null == visitUrl.putIfAbsent(url, 0)) return true;
//        return false;
//    }

    public Spider setMaxDepth(int d) {
        this.maxDepth = d;
        return this;
    }

    public Spider setUrlExtractor(UrlExtractor u) {
        this.urlExtractor = u;
        return this;
    }

    public Spider setPageSaver(PageSaver saver) {
        this.saver = saver;
        return this;
    }

    public Spider setHttpPageFetcher(HttpPageFetcher f) {
        this.fetcher = f;
        return this;
    }

    public Spider setArticleContentExtractor(ArticleContentExtractor e) {
        this.articleContentExtractor = e;
        return this;
    }

    // TODO
    public void stop() {

    }

    @Override
    public void run() {
        goBfs();
    }
}
