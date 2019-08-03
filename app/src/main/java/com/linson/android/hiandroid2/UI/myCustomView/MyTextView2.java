package com.linson.android.hiandroid2.UI.myCustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import static com.linson.LSLibrary.AndroidHelper.LSComponentsHelper.LS_CustomViewHelper.getModeStr;

public class MyTextView2 extends android.support.v7.widget.AppCompatTextView
{
    public MyTextView2(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//    {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width=MeasureSpec.getSize(widthMeasureSpec);
//        int wMode=MeasureSpec.getMode(widthMeasureSpec);
//
//        int height=MeasureSpec.getSize(heightMeasureSpec);
//        int hMode=MeasureSpec.getMode(heightMeasureSpec);
//
//
//        LSComponentsHelper.LS_Log.Log_INFO(String.format(this.getId()+ "width:%d,mode:%s,heigh:%d,hmode:%s",width,getModeStr(wMode) ,height,getModeStr(hMode)));
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh)
//    {
//        super.onSizeChanged(w, h, oldw, oldh);
//        LSComponentsHelper.LS_Log.Log_INFO(this.getId()+ "oldw:%d,oldh:%d,w:%d,h:%d.",oldw,oldh,w,h);
//    }
}
