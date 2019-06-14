package com.linson.android.hiandroid2.Services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.BackServices.MP3Services;
import com.linson.android.hiandroid2.R;

import java.io.Serializable;

//查看服务是否存在，是加入链接。获得数据。
//启动服务（绑定类型） stop:停止服务。
//先同进程服务。后新进程服务。
//*测试后台服务还是会被关闭。如果进程不是活动状态。或者点击某些耗资源动作。如快速点击某个视频。
//测试不同的activity，启动绑定服务，是否会拿到同一个binder?
public class Index extends AppCompatActivity implements View.OnClickListener
{

    private Button mBtnStop;
    private Button mBtnStart2;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private TextView mTextView3;
    private Button mBtnStart;



    private void findControls()
    {
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnStart2 = (Button) findViewById(R.id.btn_start2);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView3 = (TextView) findViewById(R.id.textView3);
        mBtnStart = (Button) findViewById(R.id.btn_start);
    }

    private Mp3Connection mp3c;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index9);
        findControls();
        bindEvent();
        connectServices();
    }

    private void connectServices()
    {
        Intent intent=new Intent(this, MP3Services.class);
        mp3c=new Mp3Connection();
        startService(intent);//永久存在
        bindService(intent, mp3c, BIND_AUTO_CREATE);//activity消失，也会消失。
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //必须解绑
        unbindService(mp3c);
    }

    private void bindEvent()
    {
        mBtnStop.setOnClickListener(this);
        mBtnStart2.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_stop:
            {
                if(mp3c!=null&& mp3c.binder!=null)
                {
                    mp3c.binder.stop();
                    OnchangeState(mp3c.binder.getState());
                }
                break;
            }
            case R.id.btn_start:
            {
                try
                {
                    mp3c.binder.Start();
                    OnchangeState(mp3c.binder.getState());
                }
                catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                break;
            }
            case R.id.btn_start2:
            {
                if(mp3c!=null&& mp3c.binder!=null)
                {
                    mp3c.binder.pause();
                    OnchangeState(mp3c.binder.getState());
                }
                break;
            }
        }
    }

    public  void OnchangeState(int state)
    {
        switch (state)
        {
            case 0://stop
            {
                mBtnStart.setEnabled(true);
                mBtnStart2.setEnabled(false);
                mBtnStop.setEnabled(false);
                mBtnStart2.setText("pause");
                break;
            }
            case 1://playing
            {
                mBtnStart.setEnabled(true);
                mBtnStart2.setEnabled(true);
                mBtnStop.setEnabled(true);
                mBtnStart2.setText("pause");
                break;
            }
            case 2://pause
            {
                mBtnStart.setEnabled(true);
                mBtnStart2.setEnabled(true);
                mBtnStop.setEnabled(true);
                mBtnStart2.setText("replay");
                break;
            }
        }
    }

    //innderclass
    public static class Mp3Connection implements ServiceConnection
    {
        public MP3Services.MP3Binder binder;
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            binder=(MP3Services.MP3Binder)service;
            LSComponentsHelper.LS_Log.Log_INFO("onServiceConnected:"+binder.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {}
    }

}