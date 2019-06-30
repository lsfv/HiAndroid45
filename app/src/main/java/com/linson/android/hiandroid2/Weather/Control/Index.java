package com.linson.android.hiandroid2.Weather.Control;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.Network.LSOKHttp;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.Weather.DB.WeatherDB;
import com.linson.android.hiandroid2.Weather.Model.Json_Province;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


//http://guolin.tech/api/china
//http://guolin.tech/api/weather?cityid=CN101010300&key=6c6dc51fe6d7413dbd1f7b816f8926e3
//welcome 页面,数据初始化处理，各种检测等。完毕后自动跳至主页。

//0.开启最简服务。提交bug.一定要最简，保证100%启动。
//1.检测数据库。

//多个fragment .多个adapter. 暂时可以用同一个layout. fragment 之间显示和隐藏。
//多一点内存。但是切换速度快。代码也简单。
public class Index extends AppCompatActivity
{
    private boolean mIsSuccess=true;
    private String mErrorDescription="";
    private WeatherDB mWeatherDB;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index15);
        checkDB();
        LSComponentsHelper.startActivity(this, Main.class);
    }

    //检查数据库并填充 数据
    private void checkDB()
    {
        mWeatherDB=new WeatherDB(this, WeatherDB.db_weatherName ,null    ,WeatherDB.db_weatherversion );
        boolean res= mWeatherDB.getisok();
        mWeatherDB.getReadableDatabase();
        mWeatherDB.close();
    }

    //只有后退才会触发这里。如果需要Activity的一些功能模块。又是一个非显示的过度流程。可以这样设计。
    @Override
    protected void onStart()
    {
        this.finish();
        super.onStart();
    }
}