package com.dmteam.extractor;

import com.dmteam.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2014/12/1.
 */
public class ExtractTask implements Runnable {


    private String dir;

    private long delay;

    private FileFilter fileFilter;

    private ArticleContentExtractor extractor;

    private String charset;

    public ExtractTask(String dir,
                       long delay,
                       FileFilter fileFilter,
                       ArticleContentExtractor extractor,
                       String charset) {
        this.dir = dir;
        this.delay = delay;
        this.fileFilter = fileFilter;
        this.extractor = extractor;
        this.charset = charset;
    }


    @Override
    public void run() {

        while (!Thread.interrupted()) {

            File dirFile = new File(dir);
            if (!dirFile.isDirectory()) {
                System.out.println("dir: " + dir + " is not a dir.");
                return;
            }

            for (File file : dirFile.listFiles()) {

                if (!fileFilter.accept(file)) {
                    continue;
                }

                System.out.println("start process file: " + file.getName());

                try {
                    String rawContent = FileUtils.readToString(file, charset);

                    String result = extractor.extract(rawContent);

                    FileUtils.writeToFile(fileFilter.transFileName(file), result, charset);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            doWait(delay); //30s
        }
    }

    private void doWait(long mill) {

        try {
            System.out.println("go to sleep...");
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
