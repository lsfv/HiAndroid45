package com.linson.android.hiandroid2.UI.myCustomView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.UI.myCustomView.DrawIt.Index;

public class index extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnAnimotion;
    private Button mBtnSignalRv;
    private Button mBtnFlowlayout;
    private Button mBtnCircleImage;
    private Button mBtnEvent;
    private Button mBtnScrolleer2;
    private Button mBtnScrolleer1;
    private Button mBtnPaint;





    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mBtnAnimotion = (Button) findViewById(R.id.btn_Animotion);
        mBtnSignalRv = (Button) findViewById(R.id.btn_signalRv);
        mBtnFlowlayout = (Button) findViewById(R.id.btn_flowlayout);
        mBtnCircleImage = (Button) findViewById(R.id.btn_circleImage);
        mBtnEvent = (Button) findViewById(R.id.btn_event);
        mBtnScrolleer2 = (Button) findViewById(R.id.btn_scrolleer2);
        mBtnScrolleer1 = (Button) findViewById(R.id.btn_scrolleer1);
        mBtnPaint = (Button) findViewById(R.id.btn_Paint);

        //set event handler
        mBtnEvent.setOnClickListener(this);
        mBtnScrolleer2.setOnClickListener(this);
        mBtnScrolleer1.setOnClickListener(this);
        mBtnCircleImage.setOnClickListener(this);
        mBtnFlowlayout.setOnClickListener(this);
        mBtnSignalRv.setOnClickListener(this);
        mBtnPaint.setOnClickListener(this);
        mBtnAnimotion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_scrolleer1: {
                LSComponentsHelper.LS_Activity.startActivity(this, ScrollerPractice1.class);
                break;
            }
            case R.id.btn_event: {
                LSComponentsHelper.LS_Activity.startActivity(this, motionEventPractice.class);
                break;
            }
            case R.id.btn_scrolleer2: {
                LSComponentsHelper.LS_Activity.startActivity(this, ScrollPage.class);
                break;
            }
            case R.id.btn_circleImage: {
                LSComponentsHelper.LS_Activity.startActivity(this, MyCircleImage.class);
                break; }
            case R.id.btn_flowlayout:{
                LSComponentsHelper.LS_Activity.startActivity(this, MyFlowLayout.class);
                break;
            }
            case R.id.btn_signalRv:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, MySignalRV.class);
                break;
            }
            case R.id.btn_Paint:{
                LSComponentsHelper.LS_Activity.startActivity(this, Index.class);
                break;
            }
            case R.id.btn_Animotion:{
                LSComponentsHelper.LS_Activity.startActivity(this, com.linson.android.hiandroid2.UI.myCustomView.Animotion.Main.class);
                break;
            }

            default: {
                break; }
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
        setContentView(R.layout.activity_index13);
        findControls();
    }
}