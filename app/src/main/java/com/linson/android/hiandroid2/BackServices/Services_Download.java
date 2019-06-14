package com.linson.android.hiandroid2.BackServices;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.AppHelper.GlobalDefine;

public class Services_Download extends Service
{
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        IntentFilter intentFilter=new IntentFilter(GlobalDefine.Broadcast_downloadName);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver(), intentFilter);
        Binder_Download binder_download=new Binder_Download(LocalBroadcastManager.getInstance(this));
        return binder_download;
    }


    private static class BroadcastReceiver extends android.content.BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Binder_Download.BroadcastParames pp=(Binder_Download.BroadcastParames) intent.getSerializableExtra(GlobalDefine.Broadcast_downloadParamName);
            if(pp.mbroadCastType==Binder_Download.ENUM_BroadcastType.doing)
            {
                LSComponentsHelper.LS_Log.Log_INFO("download:"+pp.mprogress + "%");
            }
            else if(pp.mbroadCastType==Binder_Download.ENUM_BroadcastType.result)
            {
                LSComponentsHelper.LS_Log.Log_INFO("download:"+pp.mdownloadState + ".");
            }
            else if(pp.mbroadCastType==Binder_Download.ENUM_BroadcastType.onAction)
            {
                LSComponentsHelper.LS_Log.Log_INFO("service:change state:"+pp.mdownloadState + ".");
            }
        }
    }

}