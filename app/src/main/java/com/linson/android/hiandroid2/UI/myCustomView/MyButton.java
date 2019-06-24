package com.linson.android.hiandroid2.UI.myCustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

public class MyButton extends android.support.v7.widget.AppCompatButton
{
    public MyButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean res=false;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                res= true;
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                res= true;
                break;
            }
        }

        return res;
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