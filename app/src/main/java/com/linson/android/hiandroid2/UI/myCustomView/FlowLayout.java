package com.linson.android.hiandroid2.UI.myCustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import static com.linson.LSLibrary.AndroidHelper.LSComponentsHelper.*;
import static com.linson.LSLibrary.AndroidHelper.LSComponentsHelper.LS_CustomViewHelper.*;

public class FlowLayout extends ViewGroup
{
    private int defaultHeight=10;
    private int defaultWeight=10;
    public FlowLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LS_Log.Log_INFO(this.getId()+ "width"+getMeasureStr(widthMeasureSpec)+".."+"height"+getMeasureStr(heightMeasureSpec));

        //变量，累计高度，目前长度最大值。
        //得到所有控件开始循环处理
        //放入一个元素，计算他的宽高，并加上margin。
        int heightSum=0,maxLength=0;
        for(int i=0;i<this.getChildCount();i++)
        {
            View item=getChildAt(i);
            measureChild(item, widthMeasureSpec, heightMeasureSpec);
            int tempHeight=item.getMeasuredHeight();
            int tempWeight=item.getMeasuredWidth();
            heightSum+=tempHeight;
            maxLength=tempWeight>maxLength?tempWeight:maxLength;
        }


        heightSum=heightSum==0?defaultHeight:heightSum;
        maxLength=maxLength==0?defaultWeight:maxLength;

        SuggestMeasure suggestMeasure=getCommonMeasure(widthMeasureSpec, heightMeasureSpec, defaultWeight, defaultHeight,LSComponentsHelper.LS_CustomViewHelper.Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        //得到所有控件开始循环处理
        //top.left.默认为0，加入margin. ........,

        int top=0;
        for(int i=0;i<this.getChildCount();i++)
        {
            View item=getChildAt(i);
            //注意是布局子控件，千万别少了对象，否则死循环。
            item.layout(0, top, item.getMeasuredWidth(), top+item.getMeasuredHeight());
            top+=item.getMeasuredHeight();
        }
    }
}