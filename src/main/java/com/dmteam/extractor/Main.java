package com.dmteam.extractor;

import com.dmteam.extractor.impl.QQExtractor;

import java.io.IOException;

/**
 * Created by Administrator on 2014/12/1.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        ExtractTask task1 = new ExtractTask(
                "D:\\___folder\\QQ",
                30000l,
                new MyFileFilter(),
                new QQExtractor(),
                "utf-8"
        );

        new Thread(task1).start();


//        NetEaseExtractor e = new NetEaseExtractor();
//        System.out.println(e.extract(FileUtils.readToString("D:\\___folder\\NetEase\\travel.163.com_12_1031_18_8F5Q8N6P00063U7D.html.txt","utf-8")));



//        QQExtractor e = new QQExtractor();
//        System.out.println(e.extract(FileUtils.readToString("D:\\___folder\\QQ\\ent.qq.com_a_20130121_000155.htm.txt","utf-8")));

    }


}
