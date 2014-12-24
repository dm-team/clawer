package com.dmteam.crawler;

import com.dmteam.crawler.html.*;
import com.dmteam.crawler.net.HttpPageFetcher;
import com.dmteam.extractor.ArticleContentExtractor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by Administrator on 2014/11/27.
 */
public class Spider implements Runnable, Stopable {

    static Logger logger = LoggerFactory.getLogger(Spider.class);

    private String rootUrl;

    private ArticleContentExtractor articleContentExtractor;

    private PageSaver saver;

    private UrlStorage urlStorage;

    private HttpPageFetcher fetcher;

    private PageAnalyzer pageAnalyzer;

    private boolean stop;

    public Spider(String rootUrl, UrlStorage urlStorage) {
        this.rootUrl = rootUrl;
        this.urlStorage = urlStorage;
    }


    public void goBfs() {

        stop = false;

        PageContext page = pageAnalyzer.extractInfo(rootUrl);
        urlStorage.store(page);

        while (!stop) {

            page = urlStorage.take();

            // 如果取不到url，则等待
            if (page == null)
                try {
                    System.out.println("url storage empty, go sleep...");
                    Thread.sleep(1000); continue;
                } catch (InterruptedException e) {break;}

            process(page);
        }
    }

    private void process(PageContext pc) {

        if (!urlStorage.visit(pc)) return;

        try {
            String fetchHtml = fetcher.fetch(pc.pageUrl);

            List<PageContext> urls = pageAnalyzer.extractUrls(fetchHtml);

            urlStorage.store(urls);


            if (!pc.isArticle) return;

            if (pc.desFile.exists()) return;

            String contentForSave = fetchHtml;
            if (articleContentExtractor != null) {
                contentForSave = articleContentExtractor.extract(fetchHtml);
            }

            if (StringUtils.isNotBlank(contentForSave)) {

                logger.info("Save file: " + pc.desFile.getAbsolutePath());

                saver.doSave(pc, contentForSave);
            }

        } catch (SocketTimeoutException ste) {
            // TODO 超时如何处理？
            logger.error("Timeout, url:" + pc.pageUrl, ste);
        } catch (IOException e) {
            logger.error("spider process exception, url:" + pc.pageUrl, e);
        }
    }


    public Spider setPageAnalyzer(PageAnalyzer u) {
        this.pageAnalyzer = u;
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
        stop = true;
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        goBfs();
    }
}
