package com.linson.android.hiandroid2.UI.myCustomView;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

public class ScrollerPractice1 extends AppCompatActivity implements View.OnClickListener
{
    //region difine controls. Auto generate don't modify
    private Button mBtnShow3;
    private ConstraintLayout mConstraintLayout;
    private TextView mTextView6;
    private Button mBtnShow;
    private Button mBtnShow2;
    private TextView mTvInfo;
    private TextView mTvMsg;
    private scroller1 mMytestview;
    //endregion

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mBtnShow3 = (Button) findViewById(R.id.btn_show3);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        mTextView6 = (TextView) findViewById(R.id.textView6);
        mBtnShow = (Button) findViewById(R.id.btn_show);
        mBtnShow2 = (Button) findViewById(R.id.btn_show2);
        mTvInfo = (TextView) findViewById(R.id.tv_info);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mMytestview = (scroller1) findViewById(R.id.mytestview);

        //set event handler
        mBtnShow.setOnClickListener(this);
        mBtnShow2.setOnClickListener(this);
        mBtnShow3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_show:
            {
                show();
                break;
            }
            case R.id.btn_show2:
            {
                show2();
                break;
            }
            case R.id.btn_show3:
            {
                show3();
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
    private void show()
    {
        mTextView6.scrollBy(10, 10);
    }
    private void show2()
    {
        mTextView6.setTranslationX(mTextView6.getTranslationX()+10);
        mTextView6.setTranslationY(mTextView6.getTranslationX()+10);
    }
    private void show3()
    {
        mMytestview.smoothScroll(100,200);
    }
    //endregion

    //member variable
    showtask myshowtask;
    boolean mrun=true;
    VelocityTracker velocityTracker;
    GestureDetector mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_practice1);
        findControls();
        trackTextview();

        LSComponentsHelper.LS_Log.Log_INFO("滑动最小距离:"+ViewConfiguration.get(this.getApplicationContext()).getScaledTouchSlop()+"");
        mGestureDetector=new GestureDetector(this, new MyGestureListen());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        velocityTracker =VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(1000);
        //LSComponentsHelper.LS_Log.Log_INFO(velocityTracker.getXVelocity()+"."+velocityTracker.getYVelocity());

        boolean res=mGestureDetector.onTouchEvent(event);
        return res; //super.onTouchEvent(event);//default false;
    }

    private void trackTextview()
    {
        myshowtask=new showtask();
        myshowtask.execute();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        try
        {
            mrun = false;
            myshowtask.cancel(true);
            myshowtask = null;
            velocityTracker.clear();
            velocityTracker.recycle();
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }

    }

    //region asyntask
    private  class showtask extends AsyncTask<Void,String,Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            while (mrun)
            {
                String tt="left:%d. top:%d  \n x:%f.   y:%f, \n tsltx:%f,   tslty:%f. \n ScrollX:%d  ScrollY%d.\n getScaleX:%f.";
                String info=String.format(tt,mTextView6.getLeft(),mTextView6.getTop(), mTextView6.getX(),mTextView6.getY(),
                        mTextView6.getTranslationX(),mTextView6.getTranslationY(),mTextView6.getScrollX(),mTextView6.getScrollY(),mTextView6.getScaleX());
                publishProgress(info);
                try
                {
                    Thread.sleep(500);
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            mTvInfo.setText(values[0]);
        }
    }
    //endregion

    //region gesture listener
    public class MyGestureListen implements GestureDetector.OnGestureListener
    {
        @Override
        public boolean onDown(MotionEvent e)
        {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e)
        {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            LSComponentsHelper.LS_Log.Log_INFO("activity:single tapup");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e)
        {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            return true;
        }
    }
    //endregion
}