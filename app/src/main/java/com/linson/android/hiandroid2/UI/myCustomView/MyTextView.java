package com.linson.android.hiandroid2.UI.myCustomView;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

public class MyTextView extends AppCompatTextView
{
    public MyTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        LSComponentsHelper.LS_Log.Log_INFO(event.toString());
        boolean consume=super.onTouchEvent(event);
        //dispatch=true;
        //dispatch=false;
        LSComponentsHelper.LS_Log.Log_INFO("return:"+consume);
        return consume;
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
}