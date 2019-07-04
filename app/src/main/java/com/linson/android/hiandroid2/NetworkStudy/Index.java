package com.linson.android.hiandroid2.NetworkStudy;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.Network.LSOKHttp;
import com.linson.android.hiandroid2.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class Index extends AppCompatActivity implements View.OnClickListener
{
    //auto generate
    private Button mBtnGet2;
    private Button mBtnGet;
    private TextView mTvMsg;
    private Button mBtnDownload;




    private void findControls()
    {
        mBtnGet2 = (Button) findViewById(R.id.btn_get2);
        mBtnGet = (Button) findViewById(R.id.btn_get);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mBtnDownload = (Button) findViewById(R.id.btn_download);
    }
    //auto generate


    private final String jsonUrl = "http://120.79.79.80:8044/MainPage.asmx/Category_MainCategory";
    private final String jsonpostUrl="http://120.79.79.80:8044/MainPage.asmx/Supplier_Eat";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index8);

        findControls();
        setControlsEvent();
    }

    private void setControlsEvent()
    {
        mBtnGet2.setOnClickListener(this);
        mBtnGet.setOnClickListener(this);
        mTvMsg.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_get:
            {
                //request,2.callback for string ,or callback for fail.
                LSOKHttp.get(jsonUrl, new Callback()
                {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException
                    {
                        String data=response.body().string();
                        Gson theGson=new GsonBuilder().create();
                        categoryList categorys=theGson.fromJson(data, categoryList.class);
                        LSComponentsHelper.LS_Log.Log_INFO(categorys.VCategory_All.size()+"");
                    }
                });
                break;
            }
            case R.id.btn_get2:
            {
                Map<String,String> body=new HashMap<>();
                body.put("cityid", "360300");
                LSOKHttp.post(jsonpostUrl, body, new Callback()
                {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException
                    {
                        String msg=response.body().string();
                        LSComponentsHelper.LS_Log.Log_INFO(msg);
                    }
                });
                break;
            }
            case R.id.btn_download:
            {
                download();
            }
        }
    }


    //dowanload:什么协议？http ftp? if http:1.会先得到大小？如果有大小。那就是一直收，直到收到大小。或者断掉。
    private void download()
    {


    }


//innder class
public static class categoryList
{
    public @Nullable List<category> VCategory_All;
}

    public static class category
    {
        public int cg_id;
        public int cg_fatherid;
        public String cg_name;
        public String cg_tablename;
        public String cg_pic;
        public @Nullable String cg_extend;
    }
}