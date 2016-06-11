package com.sun.app.sun.util;

/**
 * Created by Administrator on 2016/6/10.
 * 用于回调服务返回的结果
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
