package com.linson.android.hiandroid2.UI.myCustomView;

import android.content.Context;
import android.gesture.Gesture;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

public class ScrollTextView extends AppCompatTextView
{
    private GestureDetector mGestureDetector;
    private int mLastAction=0;
    private IonScrollLeftRight mIonScrollLeftRight;
    public ScrollTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mGestureDetector=new GestureDetector(this.getContext(), new MyTouchListenHandler());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean consume=mGestureDetector.onTouchEvent(event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
            {
                LSComponentsHelper.LS_Log.Log_INFO("invode"+mLastAction);
                if(mIonScrollLeftRight!=null)
                {
                    if (mLastAction == 1)//left .2right
                    {
                        mIonScrollLeftRight.onScrollLeftOrRight(this,true);
                    }
                    else if(mLastAction==2)
                    {
                        mIonScrollLeftRight.onScrollLeftOrRight(this,false);
                    }
                }
                mLastAction=0;//up之后要清掉.
                break;
            }
        }
        return consume;
        //return super.onTouchEvent(event);
    }

    public void setIonScrollLeftRight(@NonNull IonScrollLeftRight handler)
    {
        mIonScrollLeftRight=handler;
    }

    //region listener
    private class MyTouchListenHandler implements GestureDetector.OnGestureListener
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
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            boolean bUpdown=Math.abs(distanceY)>Math.abs(distanceX)?true:false;
            boolean bUpLeft=false;
            if((bUpdown && distanceY>0) || (bUpdown==false && distanceX>0))
            {
                bUpLeft=true;
            }
            LSComponentsHelper.LS_Log.Log_INFO("is updown:"+bUpdown+". is upleft:"+bUpLeft);
            if(bUpdown==false)
            {
                if(bUpLeft)
                {
                    mLastAction=1;//left
                }
                else
                {
                    mLastAction=2;//right
                }
                return true;
            }
            else
            {
                return false;
            }
        }

        @Override
        public void onLongPress(MotionEvent e)
        {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            return false;
        }


    }
    //endregion

    //region interface
    public interface IonScrollLeftRight
    {
        public void onScrollLeftOrRight(ScrollTextView who,boolean isLeft);
    }
    //endregion
}
