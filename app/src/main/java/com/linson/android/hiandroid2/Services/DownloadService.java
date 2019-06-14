package com.linson.android.hiandroid2.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.AppHelper.GlobalDefine;
import com.linson.android.hiandroid2.BackServices.DownloadBinder;
import com.linson.android.hiandroid2.BackServices.DownloadServices;
import com.linson.android.hiandroid2.R;

import javax.xml.datatype.Duration;

import static com.linson.android.hiandroid2.AppHelper.GlobalDefine.Broadcast_downloadName;


//下载功能，采用Services作为主体，并作为常驻服务存在，也就是不会执行一次任务就stop。以便统一管理所有任务，目的方便管理线程数量。（可能会服务退出，binder开的线程没推出，这样线程数量无法精确控制）
//采用前台服务，以便切换了activity之后也可以方便管理服务。管理页面list出所有任务。方便分开管理。暂时设定任务最多3个。

//下载功能，采用Services作为主体，并作为常驻服务存在,因为下载不是一个只是用一次的后台服务，下载是一个常用的功能。常驻在内存更省电。
//继承asynctask来方便处理异步任务。方便线程间的数据安全处理。并把任务限制在单任务。
//
//1.先写出简单的下载功能，用aysncTask.。当完成任务的时候，也就是在onpostexecute的时候，把自己设置为null.
//2.下载中切换activity。无法进行控制。除非把asyncTask改为全局变量。 其实Services就是一个单例类。所以把下载这种生命周期跨越activity的模块，放入Services中。利用Services的全局性.
//
public class DownloadService extends AppCompatActivity implements View.OnClickListener
{
    //auto generate
    private Button mBtnStart4;
    private Button mBtnStart3;
    private Button mBtnStart;
    private void findControls()
    {
        mBtnStart4 = (Button) findViewById(R.id.btn_start4);
        mBtnStart3 = (Button) findViewById(R.id.btn_start3);
        mBtnStart = (Button) findViewById(R.id.btn_start);
    }
    //auto generate

    //private static AsynDownload mAsyncDownloadTask;
    private DownloadConnection mDownloadConnection;
    private int autofileid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service);

        findControls();
        setEvent();
        startAndBindService();

        LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter=new IntentFilter(Broadcast_downloadName);
        localBroadcastManager.registerReceiver(new UIReceiveBroadcast(), intentFilter);
    }

    //四.客户端要start and bindService。那么Service就会在后台运行。并且bind后Connection就有了Binder了.
    private void startAndBindService()
    {
        try
        {
            Intent intentService=new Intent(this, DownloadServices.class);
            startService(intentService);
            mDownloadConnection=new DownloadConnection();
            bindService(intentService, mDownloadConnection, BIND_AUTO_CREATE);
        }
        catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
    }

    private void setEvent()
    {
        mBtnStart.setOnClickListener(this);
        mBtnStart3.setOnClickListener(this);
        mBtnStart4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_start://start
            {
                boolean res=start("file"+autofileid++);
                if(res==false)
                {
                    Toast.makeText(this, "can't download", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_start3://pause
            {
                pause();
                break;
            }
            case R.id.btn_start4://cancel
            {
                cancel();
                break;
            }
        }
    }

    private boolean start(String url)
    {
        return mDownloadConnection.mDownloadBinder.start(url);
    }

    private void cancel()
    {
        mDownloadConnection.mDownloadBinder.cancel();
    }

    private void pause()
    {
        mDownloadConnection.mDownloadBinder.pause();
    }

    //三.Connection 要在client定义，因为service方，可能是在另外一个程序,无法访问。 connection的最大目的就是获得BINDER,所以onServiceConnected要给成员变量binder赋值，
    public static class DownloadConnection implements ServiceConnection
    {
        public DownloadBinder mDownloadBinder;
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            LSComponentsHelper.LS_Log.Log_INFO("connect:onconnected.");
            if(service instanceof DownloadBinder)
            {
                mDownloadBinder = (DownloadBinder) service;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            LSComponentsHelper.LS_Log.Log_INFO("UI:disconnected:"+name.toString());
        }
    }

    private static class UIReceiveBroadcast extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getSerializableExtra(GlobalDefine.Broadcast_downloadParamName) instanceof DownloadBinder.CallBackParams)
            {
                DownloadBinder.CallBackParams callBackParams=(DownloadBinder.CallBackParams)intent.getSerializableExtra(GlobalDefine.Broadcast_downloadParamName);
                if(callBackParams.msgType==3)
                {
                    LSComponentsHelper.LS_Log.Log_INFO("state change:ui need change"+callBackParams.downloadState+"");
                }
            }
        }
    }

}