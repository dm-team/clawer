package com.dmteam.crawler.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xh on 2014/11/24.
 */
public class HttpPageFetcher {

    private String charset;

    public HttpPageFetcher(String charset) {
        this.charset = charset;
    }

    public String fetch(String url) throws IOException {

        if (!url.startsWith("http")) {
            url = "http://" + url;
        }

        URL getUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.setConnectTimeout(50000);
        connection.setReadTimeout(50000);
        connection.connect();


        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), charset);
        try {

            StringBuilder sb = new StringBuilder();
            char[] data = new char[1024];
            int i;
            while ((i=reader.read(data)) != -1) {
                sb.append(data, 0, i);
            }





//            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
//
//            StringBuilder sb = new StringBuilder();
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line).append("\n");
//            }

            return sb.toString();

        } finally {
            if (reader != null) reader.close();
            connection.disconnect();
        }

    }
}
