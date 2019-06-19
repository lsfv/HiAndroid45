package com.linson.android.hiandroid2.Services;

import android.app.Service;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.BackServices.DownloadBinder;
import com.linson.android.hiandroid2.BackServices.DownloadBroadcast;
import com.linson.android.hiandroid2.BackServices.DownloadServices;
import com.linson.android.hiandroid2.R;

import static com.linson.android.hiandroid2.BackServices.DownloadBroadcast.*;


/**
 * 初步总结：典型服务步骤
 * 一.新建类继承services。最重要的任务就是onBind返回binder。
 * 二。建立一个内部类Binder.
 * 三.Connection 要在client定义，因为service方，可能是在另外一个程序,无法访问。
 *  connection的最大目的就是在onServiceConnected的时候给公共变量BINDER赋值（Service的onbind已经返回了binder）
 * 3.1 binder 写个简单的test, 实际调用service下的一个test方法。以体现binder就是一个接口的意思。任务给serivce做。
 * 3.2 manifest.xml 注册服务。
 * 四.客户端start and bindService。那么Service就会在后台运行。并且bind后Connection就有了Binder了. 还要注意ondestroy 要unbind(connection)
 * 客户端定义一个Connection类型哦变量。通过它得到binder，并测试是否可以打印出binder的test
 * 五，具体的服务需求，导致代码设计是不同的。
 * 场景：任务是one wait one。
 * 需求分析
 * 1.doInBackground 一定要保证任何情况下都会执行完毕.
 * 2.必要，任务完成后的需要回调，提示任务结束，或者可以自动进行下一个任务
 * 3.可选，随时报告任务进度。
 * 4.必要，缓存客户消息队列，逐条处理。并回应消息。
 * 5.必要，提供获取状态。以便用户随时做必要界面处理。
 * 模块设计
 */

public class DownloadService extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnDownload;
    private Button mBtnCancel;
    private TextView mTvFilename;
    private ProgressBar mPbDownload;
    private TextView mTvProgress;
    private Button mBtnDownload1;
    private Button mBtnPause;

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mBtnDownload = (Button) findViewById(R.id.btn_download);
        mBtnCancel = (Button) findViewById(R.id.btn_Cancel);
        mTvFilename = (TextView) findViewById(R.id.tv_filename);
        mPbDownload = (ProgressBar) findViewById(R.id.pb_download);
        mTvProgress = (TextView) findViewById(R.id.tv_progress);
        mBtnDownload1 = (Button) findViewById(R.id.btn_download1);
        mBtnPause = (Button) findViewById(R.id.btn_pause);

        //set event handler
        mBtnDownload.setOnClickListener(this);
        mBtnDownload1.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_download:
            {
                download();
                break;
            }
            case R.id.btn_download1:
            {
                download1();
                break;
            }
            case R.id.btn_Cancel:
            {
                cancel();
                break;
            }
            case R.id.btn_pause:
            {
                pause();
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //region functions of click event
    private void download()
    {
        if(mDownloadConnection!=null && mDownloadConnection.mBinder!=null)
        {
            if (mDownloadConnection.mBinder.download("file1"))
            {
                mTvFilename.setText("file1");
            }
        }
    }

    private void download1()
    {
        if(mDownloadConnection!=null && mDownloadConnection.mBinder!=null)
        {
            if (mDownloadConnection.mBinder.download("file2"))
            {mTvFilename.setText("file2");}
        }
    }

    private void cancel()
    {
        if(mDownloadConnection!=null && mDownloadConnection.mBinder!=null)
        {
           if( mDownloadConnection.mBinder.cancel())
           {
               mBtnPause.setEnabled(false);
               mBtnCancel.setEnabled(false);
           }
        }
    }
    private void pause()
    {
        if(mDownloadConnection!=null && mDownloadConnection.mBinder!=null)
        {
            if( mDownloadConnection.mBinder.pause())
            {
                mBtnPause.setEnabled(false);
                mBtnCancel.setEnabled(false);
            }
        }
    }


    //endregion

    //member variable
    private DownloadConnection mDownloadConnection;
    private broadcastRecirver mBroadcastRecirver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service5);
        findControls();
        bindService();
        registerBroadcast();

        mBtnCancel.setEnabled(false);
        mBtnPause.setEnabled(false);
    }

    private void registerBroadcast()
    {
        LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(broadcastName);
        mBroadcastRecirver=new broadcastRecirver();
        localBroadcastManager.registerReceiver(mBroadcastRecirver,intentFilter);
    }

    private void bindService()
    {
        mDownloadConnection=new DownloadConnection();
        Intent intent=new Intent(this,DownloadServices.class);
        startService(intent);
        bindService(intent, mDownloadConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(mDownloadConnection);
        LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(this);
        localBroadcastManager.unregisterReceiver(mBroadcastRecirver);
    }

    //region innder class connection
    private class DownloadConnection implements ServiceConnection
    {
        DownloadBinder mBinder;
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            if(service instanceof DownloadBinder)
            {
                mBinder=(DownloadBinder)service;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    }


    //endregion conn

    //region inner class broadcast receiver
    private class broadcastRecirver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            BroadcastMsg msg=(BroadcastMsg)intent.getSerializableExtra(broadcastExtraName);
            switch (msg.mInfoType)
            {
                case progressInfo:
                {
                    if(msg.mInfo instanceof BroadcastprogressInfo)
                    {
                        BroadcastprogressInfo broadcastprogressInfo = (BroadcastprogressInfo) msg.mInfo;
                        mPbDownload.setProgress(broadcastprogressInfo.progress);
                        mTvProgress.setText("download:"+broadcastprogressInfo.progress+"%");
                    }
                    else
                    {
                        LSComponentsHelper.LS_Log.Log_INFO("error info type");
                    }
                    break;
                }
                case feedbackInfo:
                {
                    if(msg.mInfo instanceof BroadcastfeedbackInfo)
                    {
                        BroadcastfeedbackInfo broadcastfeedbackInfo = (BroadcastfeedbackInfo) msg.mInfo;
                        if(broadcastfeedbackInfo.mResult==ENUM_BroadcastTaskResult.ok||broadcastfeedbackInfo.mResult==ENUM_BroadcastTaskResult.cancel||broadcastfeedbackInfo.mResult==ENUM_BroadcastTaskResult.error||broadcastfeedbackInfo.mResult==ENUM_BroadcastTaskResult.prepare)
                        {
                            mBtnCancel.setEnabled(false);
                            mBtnPause.setEnabled(false);
                        }
                        else if(broadcastfeedbackInfo.mResult==ENUM_BroadcastTaskResult.downloading)
                        {
                            mBtnCancel.setEnabled(true);
                            mBtnPause.setEnabled(true);
                            mBtnPause.setText("pause");
                        }
                        else if(broadcastfeedbackInfo.mResult==ENUM_BroadcastTaskResult.pause)
                        {
                            mBtnCancel.setEnabled(true);
                            mBtnPause.setEnabled(true);
                            mBtnPause.setText("continue");
                        }
                        LSComponentsHelper.LS_Log.Log_INFO("receive:"+broadcastfeedbackInfo.mControl.name());
                    }
                    else
                    {
                        LSComponentsHelper.LS_Log.Log_INFO("error info type");
                    }
                    break;
                }
                case resultInfo:
                {
                    if(msg.mInfo instanceof BroadcastresultInfo)
                    {
                        LSComponentsHelper.LS_Log.Log_INFO("receive broadcast ok.");
                        BroadcastresultInfo broadcastresultInfo = (BroadcastresultInfo) msg.mInfo;
                        mTvProgress.setText("download"+broadcastresultInfo.mTaskResult.name());
                        mBtnCancel.setEnabled(false);
                        mBtnPause.setEnabled(false);
                    }
                    else
                    {
                        LSComponentsHelper.LS_Log.Log_INFO("error info type");
                    }
                    break;
                }
            }
        }
    }
    //endregion
}