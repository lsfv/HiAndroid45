package com.linson.android.hiandroid2.Services;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.AppHelper.GlobalDefine;
import com.linson.android.hiandroid2.BackServices.Binder_Download;
import com.linson.android.hiandroid2.BackServices.Services_Download;
import com.linson.android.hiandroid2.R;

public class DownloadService2 extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnStartdown;
    private Button mBtnPause;
    private Button mBtnCancel;
    private BroadcastReceiver mBroadcastReceiver;

    private void findControls()
    {
        mBtnStartdown = (Button) findViewById(R.id.btn_startdown);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
    }


    private Connection_download connection_download;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service2);

        findControls();
        setControlsEvent();

        bindService();
        regeditBroadcast();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    private void regeditBroadcast()
    {
        IntentFilter intentFilter=new IntentFilter(GlobalDefine.Broadcast_downloadName);
        mBroadcastReceiver=new Receive_onaction();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, intentFilter);
        Binder_Download binder_download=new Binder_Download(LocalBroadcastManager.getInstance(this));
    }

    private void bindService()
    {
        Intent intent=new Intent(this, Services_Download.class);
        startService(intent);
        connection_download=new Connection_download();
        bindService(intent, connection_download, BIND_AUTO_CREATE);

    }

    private void setControlsEvent()
    {
        mBtnStartdown.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_startdown://start
            {
                start();
                break;
            }
            case R.id.btn_pause://pause
            {
                pause();
                break;
            }
            case R.id.btn_cancel://cancel
            {
                cancel();
                break;
            }
        }
    }


    private void onchangebtns(Binder_Download.ENUM_task_stats type)
    {
        if(type==Binder_Download.ENUM_task_stats.prepare|| type==Binder_Download.ENUM_task_stats.cancel||type==Binder_Download.ENUM_task_stats.error||type==Binder_Download.ENUM_task_stats.ok)
        {
            mBtnStartdown.setEnabled(true);
            mBtnPause.setEnabled(false);
            mBtnCancel.setEnabled(false);
        }
        else if(type==Binder_Download.ENUM_task_stats.doing)
        {
            mBtnStartdown.setEnabled(false);
            mBtnPause.setEnabled(true);
            mBtnPause.setText("pause");
            mBtnCancel.setEnabled(true);
        }
        else if(type==Binder_Download.ENUM_task_stats.pause)
        {
            mBtnStartdown.setEnabled(false);
            mBtnPause.setEnabled(true);
            mBtnPause.setText("continue");
            mBtnCancel.setEnabled(true);
        }

    }

    public void start()
    {
        connection_download.mBinder_download.start();
    }
    public void pause()
    {
        connection_download.mBinder_download.pause();
    }
    public void cancel()
    {
        connection_download.mBinder_download.cancel();
    }

    //inner class
    public class Connection_download implements ServiceConnection
    {
        public Binder_Download mBinder_download;
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            if(service instanceof Binder_Download)
            {
                try
                {
                    mBinder_download=(Binder_Download)service;
                    onchangebtns(mBinder_download.gettaskstats());
                }
                catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    }

    private  class Receive_onaction extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Binder_Download.BroadcastParames pp=(Binder_Download.BroadcastParames) intent.getSerializableExtra(GlobalDefine.Broadcast_downloadParamName);
            if(pp.mbroadCastType==Binder_Download.ENUM_BroadcastType.onAction)
            {
                onchangebtns(pp.mdownloadState);
            }
        }
    }
}