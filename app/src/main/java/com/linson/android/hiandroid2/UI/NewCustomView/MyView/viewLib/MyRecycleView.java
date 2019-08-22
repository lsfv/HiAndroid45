package com.linson.android.hiandroid2.UI.NewCustomView.MyView.viewLib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


//必须知道描绘item的时候的几个重要参数，index. firstshowIndex。 showSum.
public class MyRecycleView extends RecyclerView
{

    public MyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }


    @NonNull
    @Override
    public ItemDecoration getItemDecorationAt(int index)
    {
        return super.getItemDecorationAt(index);
    }


}