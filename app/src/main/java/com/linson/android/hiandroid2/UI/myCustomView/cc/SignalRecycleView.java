package com.linson.android.hiandroid2.UI.myCustomView.cc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.linson.android.hiandroid2.R;

public class SignalRecycleView extends RecyclerView implements View.OnClickListener
{
    private Button mBtnBasePaint3;
    private Button mBtnBasePaint2;
    private Button mBtnBasePaint;


    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnBasePaint3 = (Button) findViewById(R.id.btn_basePaint3);
        mBtnBasePaint2 = (Button) findViewById(R.id.btn_basePaint2);
        mBtnBasePaint = (Button) findViewById(R.id.btn_basePaint);

        //set event handler
        mBtnBasePaint.setOnClickListener(this);
        mBtnBasePaint2.setOnClickListener(this);
        mBtnBasePaint3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_basePaint: {

                break;
            }
            case R.id.btn_basePaint2: {

                break;
            }
            case R.id.btn_basePaint3: {

                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //member variable

    public SignalRecycleView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        findControls();

    }


}