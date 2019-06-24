package com.linson.android.hiandroid2.UI.myCustomView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

public class index extends AppCompatActivity implements View.OnClickListener
{

    private Button mBtnEvent;
    private Button mBtnScrolleer2;
    private Button mBtnScrolleer1;

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mBtnEvent = (Button) findViewById(R.id.btn_event);
        mBtnScrolleer2 = (Button) findViewById(R.id.btn_scrolleer2);
        mBtnScrolleer1 = (Button) findViewById(R.id.btn_scrolleer1);

        //set event handler
        mBtnEvent.setOnClickListener(this);
        mBtnScrolleer2.setOnClickListener(this);
        mBtnScrolleer1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_scrolleer1:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, ScrollerPractice1.class);
                break;
            }
            case R.id.btn_event:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, motionEventPractice.class);
                break;
            }
            case R.id.btn_scrolleer2:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, ScrollPage.class);
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
        setContentView(R.layout.activity_index13);
        findControls();
    }
}