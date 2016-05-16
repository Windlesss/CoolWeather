package com.learn.wangtianyuan.coolweather.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangtianyuan on 16/5/16.
 */
public class HttpUtil {
    public static void sendHeepRequest(final String address,
                                       final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while (null != (line = reader.readLine())) {
                        response.append(line);
                    }
                    if (null != listener) {
                        listener.onFinish(response.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (null != connection) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
