package com.dmteam;

import com.dmteam.crawler.Spider;
import com.dmteam.crawler.html.PageSaver;
import com.dmteam.crawler.html.PageAnalyzer;
import com.dmteam.crawler.html.SimplePageSaver;
import com.dmteam.crawler.html.UrlStorage;
import com.dmteam.crawler.html.impl.*;
import com.dmteam.crawler.net.HttpPageFetcher;
import com.dmteam.extractor.impl.PeopleExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/11/27.
 */
public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
//        NetEaseGo();
//        QQGo();
//       PeopleGo();
        a();
    }

    static void a() {

        PageSaver ps = new SimplePageSaver();
        ps.setRootDir(System.get("root_dir"));

        PageAnalyzer pageAnalyzer = new PeopleNetPageAnalyzer();
        HttpPageFetcher httpPageFetcher = new HttpPageFetcher("gb2312");
        PeopleExtractor peopleExtractor = new PeopleExtractor();

        final UrlStorage vt = new UrlStorage(ps);

        vt.recoveryFromBackup(pageAnalyzer);
        vt.startBackup();

        String[] root = {
                "http://news.people.com.cn","http://politics.people.com.cn",
                "http://society.people.com.cn","http://legal.people.com.cn",
                "http://chinese.people.com.cn","http://leaders.people.com.cn",
                "http://expo.people.com.cn","http://invest.people.com.cn",
                "http://travel.people.com.cn","http://finance.people.com.cn",
                "http://energy.people.com.cn","http://health.people.com.cn",
                "http://house.people.com.cn","http://shipin.people.com.cn",
                "http://ccnews.people.com.cn","http://people.com.cn"
        };

        final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(root.length, root.length,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));


        final List<Spider> slist = new ArrayList<Spider>(root.length);
        for (int i = 0; i < root.length; i++) {
            Spider spider = new Spider(root[i], vt)
                    .setPageSaver(ps)
                    .setPageAnalyzer(pageAnalyzer)
                    .setHttpPageFetcher(httpPageFetcher)
                    .setArticleContentExtractor(peopleExtractor);

            slist.add(spider);
            poolExecutor.execute(spider);
        }


        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {

                logger.info("get into shutdown hook.. wait 10 seconds...");

                for (Spider s : slist) {
                    s.stop();
                }

                poolExecutor.shutdown();
                vt.stop();

                try {
                    Thread.sleep(10000);
                } catch (Exception e) {}


                logger.info("shutdown wait end...");

            }
        });

    }

//    static void NetEaseGo() {
//        PageSaver ps = new NetEasePageSaver();
//        ps.setRootDir("D:/___folder/NetEase");
//
//        Spider spider = new Spider("http://news.163.com/")
//                .setMaxDepth(5)
//                .setPageSaver(ps)
//                .setUrlExtractor(new NetEaseUrlExtractor())
//                .setHttpPageFetcher(new HttpPageFetcher("gb2312"));
//        spider.goBfs();
//    }
//
//    static void QQGo() {
//        PageSaver ps = new TencentPageSaver();
//        ps.setRootDir("D:/___folder/qq");
//
//        Spider spider = new Spider("http://news.qq.com/")
//                .setMaxDepth(7)
//                .setPageSaver(ps)
//                .setUrlExtractor(new TencentUrlExtractor())
//                .setHttpPageFetcher(new HttpPageFetcher("gb2312"));
//
//        spider.goBfs();
//    }
//    static void PeopleGo(){
//        PageSaver ps = new PeopleNetPageSaver();
//        ps.setRootDir("D:/folder/People04");
//
//        Spider spider = new Spider("http://www.people.com.cn/")
//                .setMaxDepth(3)
//                .setPageSaver(ps)
//                .setUrlExtractor(new PeopleNetUrlExtractor())
//                .setHttpPageFetcher(new HttpPageFetcher("gb2312"))
//                .setArticleContentExtractor(new PeopleExtractor());
//
//        spider.goBfs();
////        spider.goBfsWithoutExtractor();
//    }


}
