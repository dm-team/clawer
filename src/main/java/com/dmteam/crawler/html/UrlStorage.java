package com.dmteam.crawler.html;

import com.dmteam.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by xh on 2014/12/23.
 */
public class UrlStorage implements Stopable {

    static Logger logger = LoggerFactory.getLogger(UrlStorage.class);

    private ScheduledExecutorService scheduledExecutorService;

    private LinkedHashSet<PageContext> urls = new LinkedHashSet<PageContext>();

    private PageSaver ps;

    public UrlStorage(PageSaver ps) {
        this.ps = ps;
    }



    public boolean visit(PageContext pc) {

        File file = pc.desFile;
        try {
            File parentFile = file.getParentFile();
            parentFile.mkdirs();

            // 文章不预先创建空文件
            if (pc.isArticle) {
                return !file.exists();
            }

            if (!file.createNewFile()) {
                return false;
            }
        } catch (IOException e) {
            System.out.println(file.getAbsolutePath());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void store(List<PageContext> url) {

        if (url == null || url.isEmpty()) return;

        Set<PageContext> set = new HashSet<PageContext>();
        set.addAll(url);

        for (PageContext p : set) {
            store(p);
        }
    }

    public void store(PageContext pc) {
        ps.generateFile(pc);

        if (!pc.desFile.exists())
            doStore(pc);
    }

    public PageContext take() {

        synchronized (urls) {

            Iterator<PageContext> iterator = urls.iterator();

            if (!iterator.hasNext()) return null;

            PageContext next = iterator.next();

            iterator.remove();

            return next;
        }
    }

    private void doStore(PageContext url) {
        synchronized(urls) {
            urls.add(url);
        }
    }

    public void recoveryFromBackup(PageAnalyzer analyzer) {

        logger.info("UrlStorage start recovery from backup...");

        File f = new File(com.dmteam.System.get("url_storage_dir"), "url_backup");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));

            String line;
            while ((line = br.readLine()) != null) {

                PageContext pc = analyzer.extractInfo(line);
                ps.generateFile(pc);

                doStore(pc);
            }

        } catch (FileNotFoundException e) {
            return;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        logger.info("UrlStorage end, " + urls.size() + " urls recoveried.");
    }

    public void startBackup() {

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                logger.info("url storage start save....");

                File f = new File(com.dmteam.System.get("url_storage_dir"), "url_backup");

                FileWriter fw = null;
                try {
                    fw = new FileWriter(f);

                    synchronized(urls) {
                        for (PageContext p : urls) {
                            fw.write(p.pageUrl);
                            fw.write("\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(fw);
                }

                logger.info("url storage end save....");

            }
//        }, 30, 30, TimeUnit.MINUTES);
        }, 10, 10, TimeUnit.SECONDS);
    }


    @Override
    public void stop() {
        if (scheduledExecutorService != null)
            scheduledExecutorService.shutdown();
    }
}
