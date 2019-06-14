package com.linson.android.hiandroid2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

public class Index extends LSBaseActivity implements View.OnClickListener
{
    private Button mBtnDownService2;
    private Button mBtnDownService;
    private Button mBtnEnterservices2;
    private Button mBtnJavapractice13;
    private Button mBtnJavapractice10;
    private Button mBtnJavapractice11;
    private Button mBtnJavapractice12;
    private Button mBtnJavapractice7;
    private Button mBtnJavapractice8;
    private Button mBtnJavapractice9;
    private Button mBtnJavapractice4;
    private Button mBtnJavapractice5;
    private Button mBtnJavapractice6;
    private Button mBtnJavapractice3;
    private Button mBtnJavapractice2;
    private Button mBtnJavapractice;







    private void findControls()
    {
        mBtnDownService2 = (Button) findViewById(R.id.btn_downService2);
        mBtnDownService = (Button) findViewById(R.id.btn_downService);
        mBtnEnterservices2 = (Button) findViewById(R.id.btn_enterservices2);
        mBtnJavapractice13 = (Button) findViewById(R.id.btn_javapractice13);
        mBtnJavapractice10 = (Button) findViewById(R.id.btn_javapractice10);
        mBtnJavapractice11 = (Button) findViewById(R.id.btn_javapractice11);
        mBtnJavapractice12 = (Button) findViewById(R.id.btn_javapractice12);
        mBtnJavapractice7 = (Button) findViewById(R.id.btn_javapractice7);
        mBtnJavapractice8 = (Button) findViewById(R.id.btn_javapractice8);
        mBtnJavapractice9 = (Button) findViewById(R.id.btn_javapractice9);
        mBtnJavapractice4 = (Button) findViewById(R.id.btn_javapractice4);
        mBtnJavapractice5 = (Button) findViewById(R.id.btn_javapractice5);
        mBtnJavapractice6 = (Button) findViewById(R.id.btn_javapractice6);
        mBtnJavapractice3 = (Button) findViewById(R.id.btn_javapractice3);
        mBtnJavapractice2 = (Button) findViewById(R.id.btn_javapractice2);
        mBtnJavapractice = (Button) findViewById(R.id.btn_javapractice);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        findControls();
        setEvent();
    }

    private void setEvent()
    {
        mBtnJavapractice.setOnClickListener(this);
        mBtnJavapractice2.setOnClickListener(this);
        mBtnJavapractice3.setOnClickListener(this);
        mBtnJavapractice4.setOnClickListener(this);
        mBtnJavapractice5.setOnClickListener(this);
        mBtnJavapractice6.setOnClickListener(this);
        mBtnJavapractice7.setOnClickListener(this);
        mBtnJavapractice8.setOnClickListener(this);
        mBtnJavapractice9.setOnClickListener(this);
        mBtnJavapractice10.setOnClickListener(this);
        mBtnJavapractice11.setOnClickListener(this);
        mBtnJavapractice12.setOnClickListener(this);
        mBtnJavapractice13.setOnClickListener(this);
        mBtnEnterservices2.setOnClickListener(this);
        mBtnDownService.setOnClickListener(this);
        mBtnDownService2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_javapractice13:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.JavaPractice.Index.class);
                break;
            }
            case R.id.btn_javapractice:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Intent.Index.class);
                break;
            }
            case R.id.btn_javapractice2:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.UI.Index.class);
                break;
            }
            case R.id.btn_javapractice3://fragment
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Fragment.Index.class);
                break;
            }//broadcast
            case R.id.btn_javapractice4:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.BroadcastStudy.Index.class);
                break;
            }
            case R.id.btn_javapractice5:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Selialzation.Index.class);
                break;
            }
            case R.id.btn_javapractice6:
            {

                break;
            }
            case R.id.btn_javapractice7:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.MultiMedia.Index.class);
                break;
            }
            case R.id.btn_javapractice8:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.NetworkStudy.Index.class);
                break;
            }
            case R.id.btn_javapractice9:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.Index.class);
                break;
            }
            case R.id.btn_enterservices2:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.Index2.class);
                break;
            }
            case R.id.btn_downService:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.DownloadService.class);
                break;
            }
            case R.id.btn_downService2:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.DownloadService2.class);
                break;
            }
            case R.id.btn_javapractice10:
            {
                break;
            }
            case R.id.btn_javapractice11:
            {

                break;
            }
            case R.id.btn_javapractice12:
            {

                break;
            }

        }
    }
}