package com.linson.android.hiandroid2.UI.myCustomView;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

public class MyLayoutBig extends ConstraintLayout
{
    public MyLayoutBig(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    //True if the event was handled, false otherwise.
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        LSComponentsHelper.LS_Log.Log_INFO(event.toString());
        boolean evetnok=super.onTouchEvent(event);
        LSComponentsHelper.LS_Log.Log_INFO("return:"+evetnok);
        return evetnok;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        LSComponentsHelper.LS_Log.Log_INFO(ev.toString());
        boolean dispatch=super.dispatchTouchEvent(ev);
        //dispatch=true;
        //dispatch=false;
        LSComponentsHelper.LS_Log.Log_INFO("return:"+dispatch);
        return dispatch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        boolean intercept=super.onInterceptTouchEvent(ev);
        //intercept=true;
        //intercept=false;
        LSComponentsHelper.LS_Log.Log_INFO("return:"+intercept+"."+ev.toString());
        return intercept;
    }
}