package com.learn.wangtianyuan.coolweather.util;

/**
 * Created by wangtianyuan on 16/5/16.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
