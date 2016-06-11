package com.sun.app.sun.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by Administrator on 2016/6/10.
 */
public class HttpUtil {
    /**这里要用到一个回调机制
     * 因为子线程是不允许有return对象的，而我们返回的数据可能又要运用到另一个类中的方法中使用
     * 那么如何将该数据在两个类中传递呢，就会运用到回调机制。
     */
    public static void sendHttpRequest(final String address,final HttpCallBackListener listener){
        //开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null ;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line ;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if(listener != null){
                        //回调onFinish()方法
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if(listener != null){
                        //回调onError()方法
                        listener.onError(e);
                    }
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
