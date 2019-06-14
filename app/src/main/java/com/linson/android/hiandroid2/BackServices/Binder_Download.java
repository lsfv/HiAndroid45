package com.linson.android.hiandroid2.BackServices;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.support.v4.content.LocalBroadcastManager;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.AppHelper.GlobalDefine;

import java.io.Serializable;

public class Binder_Download extends Binder
{
    private static LocalBroadcastManager mLocalBroadcastManager;
    private AsyncTask_Downlad mAsyncTask_downlad;
    private int autoFile=0;
    public Binder_Download(LocalBroadcastManager localBroadcastManager)
    {
        mLocalBroadcastManager=localBroadcastManager;
    }


    public boolean start()
    {
        boolean res=false;
        if(mAsyncTask_downlad==null || mAsyncTask_downlad.getStatus()==AsyncTask.Status.FINISHED)
        {
            mAsyncTask_downlad=new AsyncTask_Downlad();
            TaskInputParames taskParames=new TaskInputParames("file"+autoFile++);
            mAsyncTask_downlad.execute(taskParames);
            res=true;
        }
        return res;
    }

    public boolean pause()
    {
        boolean res=false;
        if(mAsyncTask_downlad!=null)
        {
            mAsyncTask_downlad.pause();
            res=true;
        }
        return res;
    }

    public ENUM_task_stats gettaskstats()
    {
        if(mAsyncTask_downlad!=null)
        {
            return mAsyncTask_downlad.mTask_stats;
        }
        else
        {
            return ENUM_task_stats.prepare;
        }
    }

    public boolean cancel()
    {
        boolean res=false;
        if(mAsyncTask_downlad!=null)
        {
            mAsyncTask_downlad.cancel();
            res=true;
        }
        return res;
    }


    //aysncTask class
    public static class TaskInputParames
    {
        public String murl;
        public TaskInputParames(String url)
        {
            murl=url;
        }
    }

    public static class BroadcastParames implements Serializable
    {
        public ENUM_BroadcastType mbroadCastType;//1.progress 2.end 3.onchange
        public int mprogress;
        public ENUM_task_stats mdownloadState;
        public String mfilename;
        public BroadcastParames(ENUM_BroadcastType broadCastType,int progress,ENUM_task_stats state,String filen)
        {
            mbroadCastType=broadCastType;
            mprogress=progress;
            mdownloadState=state;
            mfilename=filen;
        }
    }

    public enum ENUM_BroadcastType
    {
        doing,
        result,
        onAction
    }

    public enum ENUM_task_stats
    {
        prepare,
        doing,
        cancel,
        pause,
        ok,
        error
    }


    //难道没有什么办法搞得清晰一点吗？？？我日
    private static class AsyncTask_Downlad extends AsyncTask<TaskInputParames,Void,Void>
    {
        private ENUM_task_stats mTask_stats=ENUM_task_stats.prepare;
        private String fileName="";

        //要非常注意函数运行在后台线程，所以对于任何函数外变量，都是读,只有返回后触发onPostExecute，在ui线程修改函数外变量。
        @Override
        protected Void doInBackground(TaskInputParames... taskParames)
        {
            fileName=((TaskInputParames)taskParames[0]).murl;
            int PaustTime=0;
            int progress=0;
            mTask_stats=ENUM_task_stats.doing;
            sendAction(mTask_stats);
            while (true)
            {
                if(mTask_stats==ENUM_task_stats.cancel ||mTask_stats==ENUM_task_stats.error||mTask_stats==ENUM_task_stats.ok)
                {
                    sendAction(mTask_stats);
                    sendResult(mTask_stats);
                    break;
                }


                if(mTask_stats==ENUM_task_stats.pause)
                {
                    try
                    {
                        Thread.sleep(500);
                        PaustTime+=500;
                    }
                    catch (InterruptedException e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                }
                else if(mTask_stats==ENUM_task_stats.doing)
                {
                    try
                    {
                        Thread.sleep(1000);
                        progress+=10;
                        sendProgress(progress);
                    }
                    catch (InterruptedException e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }

                }

                if(PaustTime>15000 && mTask_stats==ENUM_task_stats.pause)
                {
                    mTask_stats=ENUM_task_stats.error;
                }
                if(progress>=100)
                {
                    mTask_stats=ENUM_task_stats.ok;
                }
            }
            return null;
        }

        private  void sendAction(ENUM_task_stats enum_task_stats)
        {
            BroadcastParames broadcastParames=new BroadcastParames(ENUM_BroadcastType.onAction, -1, enum_task_stats, fileName);
            sendBroadcast(broadcastParames);
        }

        private void sendProgress(int progress)
        {
            BroadcastParames broadcastParames=new BroadcastParames(ENUM_BroadcastType.doing, progress, ENUM_task_stats.doing, fileName);
            sendBroadcast(broadcastParames);
        }

        private void sendResult(ENUM_task_stats enum_task_stats)
        {
            BroadcastParames broadcastParames=new BroadcastParames(ENUM_BroadcastType.result, -1, enum_task_stats, fileName);
            sendBroadcast(broadcastParames);
        }

        private void sendBroadcast(BroadcastParames broadcastParames)
        {
            Intent intent=new Intent(GlobalDefine.Broadcast_downloadName);
            intent.putExtra(GlobalDefine.Broadcast_downloadParamName,broadcastParames );
            mLocalBroadcastManager.sendBroadcast(intent);
        }

        public void pause()
        {
            if(mTask_stats==ENUM_task_stats.doing)
            {
                mTask_stats=ENUM_task_stats.pause;
                sendAction(mTask_stats);
            }
            else if(mTask_stats==ENUM_task_stats.pause)
            {
                mTask_stats=ENUM_task_stats.doing;
                sendAction(mTask_stats);
            }
        }

        public void cancel()
        {
            if(mTask_stats==ENUM_task_stats.doing || mTask_stats==ENUM_task_stats.pause)
            {
                mTask_stats=ENUM_task_stats.cancel;
                sendAction(mTask_stats);
            }
        }



    }
}