package com.linson.android.hiandroid2.BackServices;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.support.v4.content.LocalBroadcastManager;
import android.telecom.Call;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import static com.linson.android.hiandroid2.AppHelper.GlobalDefine.Broadcast_downloadName;
import static com.linson.android.hiandroid2.AppHelper.GlobalDefine.Broadcast_downloadParamName;

//一。定义一个Binder.最重要的角色，client和service的纽带。也是asyncTask的代理。
// 最好顶级类，否则和service在一起，自然asynctask也在service内。那么asynctask对于 services也是可见的。然而Service最好只和Binder联系，不接触asynctask。降低耦合度.
//七，简洁点，构造函数的时候把任务类创建，并传递给任务类监听对象。
public class DownloadBinder extends Binder
{
    private AsynTaskDownload mAsyncDownloadTask;
    private LocalBroadcastManager mLocalBroadcastManager;

    public DownloadBinder(LocalBroadcastManager localBroadcastManager)
    {
        mLocalBroadcastManager=localBroadcastManager;
    }

    public boolean start(String downloadurl)
    {
        //1.one by one
        boolean res=false;
        if(mAsyncDownloadTask==null || (mAsyncDownloadTask!=null && mAsyncDownloadTask.getStatus()==AsyncTask.Status.FINISHED))
        {
            mAsyncDownloadTask=new AsynTaskDownload(mLocalBroadcastManager);
            mAsyncDownloadTask.execute(new ExecuteParams(downloadurl));
            res=true;
        }
        return res;
    }

    public void pause()
    {
        if(mAsyncDownloadTask!=null)
        {
            mAsyncDownloadTask.pause();
        }
    }

    public void cancel()
    {
        if(mAsyncDownloadTask!=null)
        {
            mAsyncDownloadTask.cancel();
        }
    }

    //模块的功能实现类和回调接口
    //五，这边设计就多元化。自己衡量，添加一个字段mNumExecuting.表明正在执行的任务数量。是一个简洁的方案。可以让任务逐个开线程执行。也可以同时开几个线程执行。
    //0porgerss . 1state.
    public static class CallBackParams implements Serializable
    {
        public int progress;
        public int downloadState;
        public int msgType;
        public String filename;
        public CallBackParams(int msg,int p,int state,String filen)
        {
            msgType=msg;
            progress=p;
            downloadState=state;
            filename=filen;
        }
    }
    public static class ExecuteParams
    {
        public String uri;
        public ExecuteParams(String uu)
        {
            uri=uu;
        }
    }

    //不使用cancel，而是自已处理cancel。自己保证线程一定会执行完毕，而且一定会执行onPostExecute。用简洁避免bug。
    private static class AsynTaskDownload extends AsyncTask<ExecuteParams, CallBackParams, Boolean>
    {
        private String mDownloadURI = "";
        private int mState = 0;//0 ready, 1 download 2.pause.3.error 4 complete.5 cancel
        private int mNumExecuting=0;
        private LocalBroadcastManager mLocalBroadcastManager;

        public AsynTaskDownload(LocalBroadcastManager localBroadcastManager)
        {
            mLocalBroadcastManager=localBroadcastManager;
        }

        @Override
        protected Boolean doInBackground(ExecuteParams... strings)
        {
            Boolean res = false;
            if(mNumExecuting<1)
            {
                mNumExecuting++;
                if (strings != null && strings.length >= 1)
                {
                    mDownloadURI = ((ExecuteParams) strings[0]).uri;
                }
                int progress = 0;
                mState = 1;
                publishProgress(new CallBackParams(3, progress, mState,mDownloadURI));

                while (true)
                {
                    if (progress >= 100 || mState == 5)
                    {
                        break;
                    }
                    if (mState == 2)
                    {
                        try
                        {
                            Thread.sleep(500);//500毫秒轮询一次，看看是否已经重新下载
                        } catch (InterruptedException e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    } else
                    {
                        try
                        {
                            Thread.sleep(1000);//模拟下载
                            progress += 10;
                            publishProgress(new CallBackParams(0, progress, mState,mDownloadURI));
                        } catch (InterruptedException e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    }
                }
                if (mState == 1 && progress >= 100)//只有这一种状态才是正确下载完毕
                {
                    LSComponentsHelper.LS_Log.Log_INFO("asyntask will be over.state :completed.");
                    res = true;
                    mState = 4;//
                    publishProgress(new CallBackParams(3, progress, mState,mDownloadURI));
                } else if (mState == 5)
                {
                    LSComponentsHelper.LS_Log.Log_INFO("asyntask will be over.state :cancel.");
                } else if (mState == 3)
                {
                    LSComponentsHelper.LS_Log.Log_INFO("asyntask will be over.state :error.");
                } else
                {
                    LSComponentsHelper.LS_Log.Log_INFO("asyntask will be over.state :no way!!!!.");
                }
                mNumExecuting--;
            }
            return res;
        }


        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            super.onPostExecute(aBoolean);
            CallBackParams callBackParams=new CallBackParams(1, 0, mState, mDownloadURI);
            sendBroadcast(callBackParams);
        }

        @Override
        protected void onProgressUpdate(CallBackParams... values)
        {
            super.onProgressUpdate(values);
            if (values != null && values.length >=0)
            {
                sendBroadcast(values[0]);
            }
        }

        private void sendBroadcast(CallBackParams callBackParams)
        {
            Intent intent=new Intent(Broadcast_downloadName);
            intent.putExtra(Broadcast_downloadParamName,callBackParams );
            mLocalBroadcastManager.sendBroadcast(intent);
        }

        public void pause()
        {
            if (mState == 1)
            {
                mState = 2;
                publishProgress(new CallBackParams(3,0,mState,mDownloadURI));
            } else if (mState == 2)
            {
                mState = 1;
                publishProgress(new CallBackParams(3,0,mState,mDownloadURI));
            }
        }

        public void cancel()
        {
            if (mState == 1 || mState == 2)
            {
                mState = 5;
                publishProgress(new CallBackParams(3,0,mState,mDownloadURI));
            }
        }
    }
}