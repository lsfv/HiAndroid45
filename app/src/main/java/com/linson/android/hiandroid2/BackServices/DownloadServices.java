package com.linson.android.hiandroid2.BackServices;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.telecom.Call;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.Services.DownloadService;

import java.io.Serializable;

public class DownloadServices extends Service
{
    private AsyncDownload mAsyncDownload;
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return new DownloadBinder(this);
    }

    //region binder 's function
    public boolean download(String file)
    {
        boolean res=false;
        if(bNew())
        {
            mAsyncDownload=new AsyncDownload(new ProgressUpdataHandler());
            mAsyncDownload.execute(file);
            res=true;
        }
        return res;
    }

    public boolean cancel()
    {
        boolean res=false;
        if(bControl())
        {
            LSComponentsHelper.LS_Log.Log_INFO("cancel");
            mAsyncDownload.mControlAction.setCancel();
            res=true;
        }
        return res;
    }

    public boolean pause()
    {
        boolean res=false;
        if(bControl())
        {
            LSComponentsHelper.LS_Log.Log_INFO("pause");
            mAsyncDownload.mControlAction.setPause();
            res=true;
        }
        return res;
    }

    public boolean bNew()
    {
        if(mAsyncDownload==null ||(mAsyncDownload!=null && mAsyncDownload.getStatus()==AsyncTask.Status.FINISHED))
        {
            return true;
        }
        else{return false;}
    }

    public boolean bControl()
    {
        return  mAsyncDownload!=null && mAsyncDownload.getStatus()!=AsyncTask.Status.FINISHED;
    }
    //endregion

    //region impliation IProgressUpdateHandle 的实现
    public class ProgressUpdataHandler implements IProgressUpdateHandle
    {
        private DownloadBroadcast mDownloadBroadcast=new DownloadBroadcast(DownloadServices.this);
        @Override
        public void onProgressUpdate(PublishInfo value)
        {
            switch (value.mInfoType)
            {
                case resultInfo:
                {
                    if(value.mInfo instanceof resultInfo)
                    {
                        resultInfo info=(resultInfo)value.mInfo;
                        switch (info.mTaskResult)
                        {
                            case cancel:
                            {
                                mDownloadBroadcast.broadcastResult(DownloadBroadcast.ENUM_BroadcastTaskResult.cancel);
                                break;
                            }
                            case ok:
                            {
                                mDownloadBroadcast.broadcastResult(DownloadBroadcast.ENUM_BroadcastTaskResult.ok);
                                break;
                            }
                            case error:
                            {
                                mDownloadBroadcast.broadcastResult(DownloadBroadcast.ENUM_BroadcastTaskResult.error);
                                break;
                            }
                        }

                        LSComponentsHelper.LS_Log.Log_INFO(info.mTaskResult.name());
                    }
                    break;
                }
                case feedbackInfo:
                {
                    if(value.mInfo instanceof feedbackInfo)
                    {
                        feedbackInfo info=(feedbackInfo)value.mInfo;
                        DownloadBroadcast.ENUM_BroadcastClientControl type=DownloadBroadcast.ENUM_BroadcastClientControl.startdownload;
                        DownloadBroadcast.ENUM_BroadcastTaskResult result=DownloadBroadcast.ENUM_BroadcastTaskResult.ok;
                        switch (info.mControl)
                        {
                            case pause:{
                                type=DownloadBroadcast.ENUM_BroadcastClientControl.pause;
                                if(info.mResult==ENUM_TaskResult.pause)
                                {
                                    result = DownloadBroadcast.ENUM_BroadcastTaskResult.pause;
                                }
                                else
                                {
                                    result=DownloadBroadcast.ENUM_BroadcastTaskResult.downloading;
                                }
                                break;}
                            case cancel:{
                                type=DownloadBroadcast.ENUM_BroadcastClientControl.cancel;
                                result=DownloadBroadcast.ENUM_BroadcastTaskResult.cancel;
                                break;}
                            case startdown:{
                                type=DownloadBroadcast.ENUM_BroadcastClientControl.startdownload;
                                result=DownloadBroadcast.ENUM_BroadcastTaskResult.downloading;
                                break;}
                            default:{
                                type=DownloadBroadcast.ENUM_BroadcastClientControl.startdownload;
                                result=DownloadBroadcast.ENUM_BroadcastTaskResult.ok;
                                info.mBok=true;
                                break;}
                        }
                        mDownloadBroadcast.broadcastFeedback(type, info.mBok,result);

                        LSComponentsHelper.LS_Log.Log_INFO(info.mControl.name());
                    }
                    break;
                }
                case progressInfo:
                {
                    if(value.mInfo instanceof progressInfo)
                    {
                        progressInfo info=(progressInfo)value.mInfo;
                        mDownloadBroadcast.broadcastProgress(info.progress);
                        LSComponentsHelper.LS_Log.Log_INFO(info.progress+"%");
                    }
                    break;
                }
            }
        }
    }
    //endregion

    public interface IProgressUpdateHandle
    {
        public void onProgressUpdate(PublishInfo value);
    }

    //region asynTask

    //region PublishInfo 异步任务发布进展信息的定义。
    private enum ENUM_PublishInfoType
    {
        progressInfo,
        resultInfo,
        feedbackInfo
    }
    private enum ENUM_TaskResult
    {
        prepare,
        downloading,
        pause,
        cancel,
        ok,
        error,
        cancela
    }
    private enum ENUM_ClientControl
    {
        pause,
        cancel,
        startdown,
    }
    private static class PublishInfo
    {
        ENUM_PublishInfoType mInfoType;
        Object mInfo;
    }
    private static class progressInfo
    {
        int progress;
    }
    private static class resultInfo
    {
        ENUM_TaskResult mTaskResult;
    }
    private static class feedbackInfo
    {
        ENUM_ClientControl mControl;
        boolean mBok;
        ENUM_TaskResult mResult;
    }
    //endregion

    private static class AsyncDownload extends LSComponentsHelper.LS_AsyncTask<String,PublishInfo,Void>
    {
        private IProgressUpdateHandle mProgressUpdateHandle;

        public AsyncDownload(IProgressUpdateHandle progressUpdateHandle)
        {
            mProgressUpdateHandle=progressUpdateHandle;
        }
        @Override
        protected Void doInBackground(String... strings)
        {
            ENUM_TaskResult taskResult=ENUM_TaskResult.downloading;
            publishFeedback(ENUM_ClientControl.startdown,true,taskResult );

            int process=0;
            while (true)
            {
                boolean cancel= mControlAction.getCancelAndRestoreIfTrue();
                boolean pause=mControlAction.getPauseAndRestoreIfTrue();
                if(cancel)
                {
                    taskResult=ENUM_TaskResult.cancel;
                    publishFeedback(ENUM_ClientControl.cancel,true,taskResult);
                }
                else if(pause)
                {
                    if(taskResult==ENUM_TaskResult.pause)
                    {
                        taskResult=ENUM_TaskResult.downloading;
                    }
                    else
                    {
                        taskResult=ENUM_TaskResult.pause;
                    }

                    publishFeedback(ENUM_ClientControl.pause,true ,taskResult);
                }

                if(taskResult==ENUM_TaskResult.cancel ||taskResult==ENUM_TaskResult.error || taskResult==ENUM_TaskResult.ok) { break; }
                if(taskResult==ENUM_TaskResult.pause)
                {
                    try { Thread.sleep(500); }
                    catch (Exception e) { LSComponentsHelper.LS_Log.Log_Exception(e); }
                }
                else if(taskResult==ENUM_TaskResult.downloading)
                {
                    try {
                        Thread.sleep(1000);
                        process += 10;
                        publishProgress(process);
                    } catch (Exception e) {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                }
                if(process>=100){taskResult=ENUM_TaskResult.ok;}
            }
            publishResult(taskResult);
            return null;
        }

        private void publishResult(ENUM_TaskResult result)
        {
            resultInfo theProgressinof=new resultInfo();
            theProgressinof.mTaskResult=result;
            publishInfo(ENUM_PublishInfoType.resultInfo,theProgressinof );
        }
        private void publishProgress(int progress)
        {
            progressInfo theProgressinof=new progressInfo();
            theProgressinof.progress=progress;
            publishInfo(ENUM_PublishInfoType.progressInfo ,theProgressinof );
        }
        private void publishFeedback(ENUM_ClientControl type,boolean v,ENUM_TaskResult result)
        {
            feedbackInfo theProgressinof=new feedbackInfo();
            theProgressinof.mControl=type;
            theProgressinof.mBok=v;
            theProgressinof.mResult=result;
            publishInfo(ENUM_PublishInfoType.feedbackInfo ,theProgressinof );
        }
        private void publishInfo(ENUM_PublishInfoType type,Object body)
        {
            PublishInfo publishInfo=new PublishInfo();
            publishInfo.mInfoType=type;
            publishInfo.mInfo=body;

            boolean isok=false;
            if(type==ENUM_PublishInfoType.progressInfo && body instanceof progressInfo)
            {
                isok=true;
            }
            else if(type==ENUM_PublishInfoType.feedbackInfo && body instanceof feedbackInfo)
            {
                isok=true;
            }
            else if(type==ENUM_PublishInfoType.resultInfo && body instanceof resultInfo)
            {
                isok=true;
            }
            else
            {
                LSComponentsHelper.LS_Log.Log_INFO("error publish info");
            }

            if(isok)
            {
                publishProgress(publishInfo);
            }
        }

        @Override
        protected void onProgressUpdate(PublishInfo... values)
        {
            if(values!=null && values.length>0)
            {
                mProgressUpdateHandle.onProgressUpdate(values[0]);
            }
        }
    }
    //endregion
}