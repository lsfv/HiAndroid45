package com.linson.android.hiandroid2.BackServices;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.AppHelper.GlobalDefine;
import com.linson.android.hiandroid2.R;

import static com.linson.android.hiandroid2.AppHelper.GlobalDefine.Broadcast_downloadName;

//Service注册后，全app只有一个，而且onBind也只会执行一次，之后不管在哪里的connection，不同的action或不同进程下的， bind 的时候都不会在执行onbaind 了。
//二.services最重要的任务就是onBind返回binder。
public class DownloadServices extends Service
{
    //返回Binder,因为任何时候都只会执行一次，所以binder可以放心的放在这里。而且最好的位置就是这里，只有bind才需要binder，oncreate 不需要binder.
    // 当connection bind后，会在onConnected后获得binder.

    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter=new IntentFilter(Broadcast_downloadName);
        localBroadcastManager.registerReceiver(new BinderReceiver(), intentFilter );
        DownloadBinder mDownloadBinder = new DownloadBinder(localBroadcastManager);
        return mDownloadBinder;
    }

    //六,监听的实现,从合理性来说应该放在Service这里。如果有必要 可以再调用activity。想了下用本地广播也非常合适.
    //那就用本地广播，多路广播，方便.
    public static class BinderReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if(intent.getSerializableExtra(GlobalDefine.Broadcast_downloadParamName) instanceof DownloadBinder.CallBackParams)
            {
                DownloadBinder.CallBackParams callBackParams=(DownloadBinder.CallBackParams)intent.getSerializableExtra(GlobalDefine.Broadcast_downloadParamName);
                if(callBackParams.msgType==0)
                {
                    LSComponentsHelper.LS_Log.Log_INFO("download:"+callBackParams.progress+"%");
                }
                else if(callBackParams.msgType==1)
                {
                    LSComponentsHelper.LS_Log.Log_INFO("download:result"+callBackParams.downloadState+"%");
                }
            }
        }
    }
}