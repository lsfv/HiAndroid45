package com.linson.android.hiandroid2.UI.myCustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import okhttp3.Interceptor;

//scroller 是指内容滑动。做一些效果的。如划页。把内容移动下，作为一个滑动的效果。我去。有意思吗。没意思。
//这里是一个textview.测试默认的情况下是不处理单击事件的。
public class scroller1 extends android.support.v7.widget.AppCompatTextView
{
    Scroller mScroller = new Scroller(getContext());
    public scroller1(Context context, @android.support.annotation.Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void smoothScroll(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        mScroller.startScroll(scrollX, 0, deltaX, 0, 5000);
        invalidate();
    }

    @Override
    public void computeScroll()
    {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }
}