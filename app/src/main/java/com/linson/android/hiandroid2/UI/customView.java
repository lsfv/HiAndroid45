package com.linson.android.hiandroid2.UI;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.CustomUI.LSCircleImagePlus;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.UI.myCustomView.scroller1;

public class customView extends LSBaseActivity implements View.OnClickListener
{
    private LSCircleImagePlus mLSCircleImage;
    private scroller1 mTestv;
    private TextView mTvMessage;





    GestureDetectorCompat gestureDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        //gestureDetector.onTouchEvent(event);

//        switch (event.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                // 手指按下
//                LSComponentsHelper.LS_Log.Log_INFO("ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                VelocityTracker velocityTracker=VelocityTracker.obtain();
//                velocityTracker.addMovement(event);
//                velocityTracker.computeCurrentVelocity(10);
//                //LSComponentsHelper.LS_Log.Log_INFO(velocityTracker.getXVelocity()+"");
//                // 手指移动
//                //LSComponentsHelper.LS_Log.Log_INFO("ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                // 手指抬起
//                LSComponentsHelper.LS_Log.Log_INFO("ACTION_UP");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                // 事件被拦截
//                LSComponentsHelper.LS_Log.Log_INFO("ACTION_CANCEL");
//                break;
//            case MotionEvent.ACTION_OUTSIDE:
//                // 超出区域
//                LSComponentsHelper.LS_Log.Log_INFO("ACTION_OUTSIDE");
//                break;
//        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        findcontrols();
        gestureDetector=new GestureDetectorCompat(this,new gesturelisten());
    }

    private void findcontrols()
    {
        mLSCircleImage = (LSCircleImagePlus) findViewById(R.id.LSCircleImage);
        mTestv = (scroller1) findViewById(R.id.testv);
        mTvMessage = (TextView) findViewById(R.id.tv_message);


        mLSCircleImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.LSCircleImage:
            {
                mTestv.scrollBy(3,3);
                mTvMessage.scrollBy(3, 4);
                String msg=String.format("left:%d.x:%f.y:%f,tsltx:%f,tslty:%f.scrollx:%d.scalex%f",mTvMessage.getLeft(), mTvMessage.getX(),mTvMessage.getY(),
                        mTvMessage.getTranslationX(),mTvMessage.getTranslationY(),mTvMessage.getScrollX(),mTvMessage.getScaleX());
                LSComponentsHelper.Log_INFO("click:"+msg);

                   // mTestv.smoothScrollTo(300, 10);


                break;
            }
        }
    }


    //region gesture listen
    private static class gesturelisten implements GestureDetector.OnGestureListener
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
            LSComponentsHelper.LS_Log.Log_INFO("single click");
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