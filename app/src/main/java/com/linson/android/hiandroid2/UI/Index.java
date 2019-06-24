package com.linson.android.hiandroid2.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.UI.myCustomView.*;

public class Index extends LSBaseActivity implements View.OnClickListener
{
    private Button mBtnAdvanceview;
    private Button mBtnUi6;
    private Button mBtnUi5;
    private Button mBtnUi4;
    private Button mBtnUi3;
    private Button mBtnUi2;
    private Button mBtnUi;
    private Button mBtnUi1;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index4);

        findcontrols();
    }

    private void findcontrols()
    {
        mBtnAdvanceview = (Button) findViewById(R.id.btn_advanceview);
        mBtnUi6 = (Button) findViewById(R.id.btn_ui6);
        mBtnUi5 = (Button) findViewById(R.id.btn_ui5);
        mBtnUi4 = (Button) findViewById(R.id.btn_ui4);
        mBtnUi3 = (Button) findViewById(R.id.btn_ui3);
        mBtnUi2 = (Button) findViewById(R.id.btn_ui2);
        mBtnUi = (Button) findViewById(R.id.btn_ui);
        mBtnUi1 = (Button) findViewById(R.id.btn_ui1);

        mBtnUi.setOnClickListener(this);
        mBtnUi1.setOnClickListener(this);
        mBtnUi2.setOnClickListener(this);
        mBtnUi3.setOnClickListener(this);
        mBtnUi4.setOnClickListener(this);
        mBtnUi5.setOnClickListener(this);
        mBtnAdvanceview.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_ui1:
            {
                LSComponentsHelper.startActivity(this, TextViewButtom.class);
                break;
            }
            case R.id.btn_ui:
            {
                LSComponentsHelper.startActivity(this, ImageViewUI.class);
                break;
            }
            case R.id.btn_ui2:
            {
                LSComponentsHelper.startActivity(this, alertdialog.class);
                break;
            }
            case R.id.btn_ui3:
            {
                LSComponentsHelper.startActivity(this, layout.class);
                break;
            }
            case R.id.btn_ui4:
            {
                LSComponentsHelper.startActivity(this, listView.class);
                break;
            }
            case R.id.btn_ui5:
            {
                LSComponentsHelper.startActivity(this, customView.class);
                break;
            }
            case R.id.btn_advanceview:
            {
                LSComponentsHelper.startActivity(this,index.class);
                break;
            }
        }
    }
}