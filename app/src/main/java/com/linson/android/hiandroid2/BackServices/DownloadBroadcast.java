package com.linson.android.hiandroid2.BackServices;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;

public class DownloadBroadcast
{
    public static String broadcastName="bb";
    public static String broadcastExtraName="msg";
    private Context mContext;
    public DownloadBroadcast(Context context)
    {
        mContext=context;
    }


    public void broadcastProgress(int progress)
    {
        BroadcastprogressInfo broadcastprogressInfo=new BroadcastprogressInfo();
        broadcastprogressInfo.progress=progress;
        broadcastMsg(ENUM_BroadcastType.progressInfo, broadcastprogressInfo);
    }

    public void broadcastFeedback(ENUM_BroadcastClientControl type,boolean v,ENUM_BroadcastTaskResult result)
    {
        BroadcastfeedbackInfo broadcastfeedbackInfo=new BroadcastfeedbackInfo();
        broadcastfeedbackInfo.mBok=v;
        broadcastfeedbackInfo.mControl=type;
        broadcastfeedbackInfo.mResult=result;
        broadcastMsg(ENUM_BroadcastType.feedbackInfo,broadcastfeedbackInfo );
    }

    public void broadcastResult(ENUM_BroadcastTaskResult result)
    {
        BroadcastresultInfo broadcastfeedbackInfo=new BroadcastresultInfo();
        broadcastfeedbackInfo.mTaskResult=result;
        broadcastMsg(ENUM_BroadcastType.resultInfo,broadcastfeedbackInfo );
    }

    private void broadcastMsg(ENUM_BroadcastType type,Object body)
    {
        BroadcastMsg broadcastMsg=new BroadcastMsg();
        broadcastMsg.mInfoType=type;
        broadcastMsg.mInfo=body;

        LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(mContext);
        Intent intent=new Intent(broadcastName);
        intent.putExtra(broadcastExtraName, broadcastMsg);
        localBroadcastManager.sendBroadcast(intent);
    }

    //region 广播消息格式定义 broadcast message struct
    public enum ENUM_BroadcastType
    {
        progressInfo,
        resultInfo,
        feedbackInfo
    }
    public enum ENUM_BroadcastTaskResult
    {
        prepare,
        downloading,
        pause,
        cancel,
        ok,
        error,
        cancela
    }
    public enum ENUM_BroadcastClientControl
    {
        pause,
        cancel,
        startdownload
    }
    public static class BroadcastMsg implements Serializable
    {
        public ENUM_BroadcastType mInfoType;
        public Object mInfo;
    }
    public static class BroadcastprogressInfo
    {
        public int progress;
    }
    public static class BroadcastresultInfo
    {
        public ENUM_BroadcastTaskResult mTaskResult;
    }
    public static class BroadcastfeedbackInfo
    {
        public ENUM_BroadcastClientControl mControl;
        public ENUM_BroadcastTaskResult mResult;
        public boolean mBok;
    }
    //endregion
}