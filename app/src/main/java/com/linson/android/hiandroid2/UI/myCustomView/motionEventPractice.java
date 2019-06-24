package com.linson.android.hiandroid2.UI.myCustomView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

public class motionEventPractice extends AppCompatActivity implements View.OnClickListener
{

    private MyLayout mLayout1;
    private MySmallLayout mLayout2;
    private MyButton mButton13;
    private MyTextView mTextView9;

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mLayout1 = (MyLayout) findViewById(R.id.layout1);
        mLayout2 = (MySmallLayout) findViewById(R.id.layout2);
        mButton13 = (MyButton) findViewById(R.id.button13);
        mTextView9 = (MyTextView) findViewById(R.id.textView9);

        //set event handler
        mButton13.setOnClickListener(this);
        //mLayout1.setOnClickListener(this);
        //mLayout2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout2:
            {
                LSComponentsHelper.LS_Log.Log_INFO("layoutsmall click");
                break;
            }
            case R.id.layout1:
            {
                LSComponentsHelper.LS_Log.Log_INFO("layoutmiddle click");
                break;
            }
            case R.id.button13:
            {
                LSComponentsHelper.LS_Log.Log_INFO("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!btn click");
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //region functions of click event


    //endregion

    //member variable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event_practice);
        findControls();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        LSComponentsHelper.LS_Log.Log_INFO(event.toString());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        LSComponentsHelper.LS_Log.Log_INFO(ev.toString());
        return super.dispatchTouchEvent(ev);
    }


}