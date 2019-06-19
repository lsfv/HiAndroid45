package com.linson.android.hiandroid2.Services;

import android.os.AsyncTask;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class AsyncTaskPractice extends AppCompatActivity implements View.OnClickListener
{
    private TextView mTvFile;
    private ProgressBar mPbDownload;
    private TextView mTvTip;
    private Button mBtnPause;
    private Button mBtnCancel;
    private Button mBtnDown;

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mTvFile = (TextView) findViewById(R.id.tv_file);
        mPbDownload = (ProgressBar) findViewById(R.id.pb_download);
        mTvTip = (TextView) findViewById(R.id.tv_tip);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        mBtnDown = (Button) findViewById(R.id.btn_down);

        //set event handler
        mBtnPause.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        mBtnDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_down:
            {
                download();
                break;
            }
            case R.id.btn_cancel:
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
        if(mAsynDownload==null ||(mAsynDownload!=null &&mAsynDownload.getStatus()==AsyncTask.Status.FINISHED))
        {
            mAsynDownload = new AsynDownload();
            mAsynDownload.execute("file1");
        }
    }
    private void cancel()
    {
        mAsynDownload.cancelTask();
        disnableControls();
    }

    private void disnableControls()
    {
        mBtnCancel.setEnabled(false);
        mBtnDown.setEnabled(false);
        mBtnPause.setEnabled(false);
    }

    private void pause()
    {

    }

    //endregion

    //member variable
    AsynDownload mAsynDownload=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_practice);
        findControls();
    }


    //region
    private  class AsynDownload extends AsyncTask<String,Message,Void>
    {
        public static final  int prepare=1;
        public static final  int downloading=2;
        public static final  int pauseaa=3;
        public static final  int ok=4;
        public static final  int cancel=5;
        public static final  int erroraa=6;

        public static final int tellResult=1;
        public static final int tellProgress=2;
        public static final int tellFeedback=3;

        public static final int msgtype_control=1;
        public static final int msgtype_systeminfo=2;

        public static final int control_cancel=1;
        public static final int control_pause=2;


        public int mStatus=prepare;


        private BlockingQueue<Message> mReceiveAction=new LinkedBlockingDeque<>();
        public void cancelTask()
        {
            Message message=new Message();
            message.what=msgtype_control;
            message.arg1=control_cancel;
            mReceiveAction.add(message);
        }

        @Override
        protected void onProgressUpdate(Message... values)
        {
            LSComponentsHelper.LS_Log.Log_INFO(values.length+""+values[0].what);
            Message message=values[0];
            switch (message.what)
            {
                case tellResult:
                {
                    mTvTip.setText("end");
                    break;
                }
                case tellProgress:
                {
                    mPbDownload.setProgress(message.arg1);
                    mTvTip.setText("downloang:"+message.arg1+"%");
                    break;
                }
                case tellFeedback:
                {
                    if(message.arg2==99 && message.arg1==control_cancel)
                    {
                        mBtnCancel.setEnabled(true);
                        mBtnDown.setEnabled(true);
                        mBtnPause.setEnabled(true);
                    }
                }
            }
        }

        @Override
        protected Void doInBackground(String... strings)
        {
            mStatus=downloading;
            int progress=0;
            int pauseCount=0;
            while (true)
            {

                if(mReceiveAction.size()>0)
                {
                    int size=mReceiveAction.size();
                    for(int i=0;i<size;i++)
                    {
                        Message message=null;
                        try
                        {
                            message = mReceiveAction.take();
                        } catch (Exception e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                        if(message!=null)
                        {
                            switch (message.what)
                            {
                                case msgtype_control:
                                {
                                    mStatus=cancel;
                                    message.what=tellFeedback;
                                    message.arg2=99;
                                    publishProgress(message);
                                    break;
                                }
                            }
                        }

                    }
                }

                if(mStatus==ok||mStatus==cancel||mStatus==erroraa)
                {
                    Message message=new Message();
                    message.what=tellResult;
                    message.arg1=mStatus;
                    publishProgress(message);
                    break;
                }

                if(mStatus==downloading)
                {
                    try
                    {
                        Thread.sleep(1000);
                        progress += 10;
                        Message message=new Message();
                        message.what=tellProgress;
                        message.arg1=progress;
                        publishProgress(message);
                    } catch (Exception e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                }
                else if(mStatus==pauseaa)
                {
                    try
                    {
                        Thread.sleep(500);
                        pauseCount+=500;
                    } catch (Exception e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                }
                if (progress >= 100)
                {
                    mStatus = ok;
                }
                else
                {
                    if(pauseCount>=20000)
                    {
                        mStatus=erroraa;
                    }
                }
            }
            return null;
        }
    }
    //endregion
}