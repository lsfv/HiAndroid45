package com.linson.android.hiandroid2.Services;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//可以双向handler.
public class HandlerPractice extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnSquare;
    private Button mBtnStart;
    private TextView mTvMsg;
    private EditText mEditText2;

    //region  controls and click event.   remember call me in fun:onCreate!!!
    private void findControls()
    {
        //findControls
        mBtnSquare = (Button) findViewById(R.id.btn_square);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mEditText2 = (EditText) findViewById(R.id.editText2);

        //setEventHandler
        mBtnStart.setOnClickListener(this);
        mBtnSquare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_start:
            {
                start();
                break;
            }
            case R.id.btn_square:
            {
                square();
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
    private void start()
    {
        Thread backGroundThread=new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if(mMsgHandlerUI!=null)
                {
                    Message message=new Message();
                    message.what=MsgHandlerUI.caculateResult;
                    message.arg1=tempResult++;
                    mMsgHandlerUI.sendMessage(message);
                }
            }
        });
        backGroundThread.start();
    }


    private void square()
    {
        Message message = new Message();
        message.what = MyServices.MsgHandler_MyServices.add;
        message.arg1 = 3;
        message.arg2 = 4;
        myServices.mMsgHandler_myServices.sendMessage(message);
    }


    //endregion

    private MsgHandlerUI mMsgHandlerUI;
    private int tempResult=1;
    private MyServices myServices;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_practice);
        findControls();
        mMsgHandlerUI=new MsgHandlerUI();

        myServices = new MyServices(mMsgHandlerUI);
        myServices.StartThread();
    }

    //region innder class

    private  class MsgHandlerUI  extends Handler
    {
        public static final int caculateResult=1;

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case caculateResult:
                {
                    mTvMsg.setText(msg.arg1+"");
                }
            }
        }
    }

    private static class MyServices
    {
        private boolean run = true;
        private ReentrantLock mReentrantLock = new ReentrantLock();
        private Condition mCondition = mReentrantLock.newCondition();
        private boolean mwait = true;
        public MsgHandler_MyServices mMsgHandler_myServices = new MsgHandler_MyServices();
        private int a, b, type;
        private MsgHandlerUI mHandler;

        public MyServices(MsgHandlerUI handler)
        {
            mHandler=handler;
        }

        public void StartThread()
        {
            Thread temp = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    LSComponentsHelper.LS_Log.Log_INFO("services start");
                    while (run)
                    {
                        mReentrantLock.lock();
                        try
                        {
                            while (mwait)
                            {
                                LSComponentsHelper.LS_Log.Log_INFO("services await");
                                mCondition.await();
                            }
                            if (type == mMsgHandler_myServices.add)
                            {
                                LSComponentsHelper.LS_Log.Log_INFO("result:" + (a + b));
                                Message message=new Message();
                                message.what=MsgHandlerUI.caculateResult;
                                message.arg1=a+b;
                                mHandler.sendMessage(message);
                            } else if (type == mMsgHandler_myServices.mul)
                            {
                                LSComponentsHelper.LS_Log.Log_INFO("result:" + (a * b));
                            }
                            mwait = true;
                        } catch (Exception e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        } finally
                        {
                            mReentrantLock.unlock();
                        }
                    }
                }
            });
            temp.start();
        }

        public class MsgHandler_MyServices extends Handler
        {
            public static final int add = 1;
            public static final int mul = 2;

            @Override
            public void handleMessage(Message msg)
            {
                a = msg.arg1;
                b = msg.arg2;
                type = msg.what;
                mwait = false;

                mReentrantLock.lock();
                try
                {
                    mCondition.signalAll();
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                } finally
                {
                    mReentrantLock.unlock();
                }
            }
        }
    }

    //endregion
}