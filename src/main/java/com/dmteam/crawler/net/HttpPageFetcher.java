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
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();
        BufferedReader reader = null;


        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();

        } finally {
            if (reader != null) reader.close();
        }

    }
}
