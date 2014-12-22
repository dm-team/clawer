package com.dmteam;

import com.dmteam.crawler.Spider;
import com.dmteam.crawler.VisitedTable;
import com.dmteam.crawler.html.PageSaver;
import com.dmteam.crawler.html.UrlExtractor;
import com.dmteam.crawler.html.impl.*;
import com.dmteam.crawler.net.HttpPageFetcher;
import com.dmteam.extractor.impl.PeopleExtractor;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2014/11/27.
 */
public class Main {

    public static void main(String[] args) {
//        NetEaseGo();
//        QQGo();
//       PeopleGo();
        a();
    }

    static void a() {

        PageSaver ps = new PeopleNetPageSaver();
        ps.setDir("D:/folder/People06");

        UrlExtractor urlExtractor = new PeopleNetUrlExtractor();
        HttpPageFetcher httpPageFetcher = new HttpPageFetcher("gb2312");
        PeopleExtractor peopleExtractor = new PeopleExtractor();

        VisitedTable vt = new VisitedTable();
           String[] root = {
                "http://www.people.com.cn/",
                "http://www.people.com.cn/",
                "http://www.people.com.cn/",
                "http://www.people.com.cn/"
        };

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(root.length, root.length,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(1));

        for (int i = 0; i < root.length; i++) {
            Spider spider = new Spider(root[i], vt)
                    .setMaxDepth(6)
                    .setPageSaver(ps)
                    .setUrlExtractor(urlExtractor)
                    .setHttpPageFetcher(httpPageFetcher)
                    .setArticleContentExtractor(peopleExtractor);

            poolExecutor.execute(spider);
        }


    }

//    static void NetEaseGo() {
//        PageSaver ps = new NetEasePageSaver();
//        ps.setDir("D:/___folder/NetEase");
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
//        ps.setDir("D:/___folder/qq");
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
//        ps.setDir("D:/folder/People04");
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
