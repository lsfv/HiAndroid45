package com.linson.LSLibrary.Network;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class LSOKHttp
{
    //enqueue,这个方法猜测应该是开了新线程处理网络。接受完数据回到ui线程，并线程安全得到数据，并回调高层方法。
    public static void get(String url, final Callback callbackHandler)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callbackHandler);
    }

    //为了和调用方解耦，还是接受Map<String,String>这个参数。方便调用方以后更改为其他网络层。
    public static void post(String url, Map<String,String> parameter, final Callback callbackHandler)
    {
        OkHttpClient client=new OkHttpClient();
        FormBody.Builder formbody=new FormBody.Builder();
        for(Map.Entry<String,String> item : parameter.entrySet())
        {
            formbody.add(item.getKey(), item.getValue());
        }

        Request request=new Request.Builder()
                .url(url)
                .post(formbody.build())
                .build();
        client.newCall(request).enqueue(callbackHandler);
    }

    /**
     *it is a synctask. so you should not invoke it in ui thread.
     * @param url you want to get
     * @param callbackHandler call back
     */
    public static void getSync(String url,  Callback callbackHandler)
    {
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS )
                .build();
        Request request=new Request.Builder()
                .url(url)
                .build();
        Call callab = client.newCall(request);
        try
        {
            Response response=callab.execute();
            callbackHandler.onResponse(callab, response);
        }
        catch (Exception e) {
            IOException exception=new IOException(e.toString());
            callbackHandler.onFailure(callab,exception);
        }
    }
}