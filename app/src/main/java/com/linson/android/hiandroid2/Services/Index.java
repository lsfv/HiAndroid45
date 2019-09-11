package com.linson.android.hiandroid2.Services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.Services.AIDLSys.SysAidl;

public class Index extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnAIDL2;
    private Button mBtnAIDL3;
    private Button mBtnSocket;
    private Button mBtnSingle;
    private Button mBtnDownloadservices;
    private Button mBtnHandler3;
    private Button mBtnAsync;
    private Button mBtnHandler;
    private Button mBtnAIDL;

    //region  controls and click event
    private void findControls()
    {
        mBtnAIDL2 = (Button) findViewById(R.id.btn_AIDL2);
        mBtnAIDL3 = (Button) findViewById(R.id.btn_AIDL3);
        mBtnSocket = (Button) findViewById(R.id.btn_Socket);
        mBtnSingle = (Button) findViewById(R.id.btn_single);
        mBtnDownloadservices = (Button) findViewById(R.id.btn_downloadservices);
        mBtnHandler3 = (Button) findViewById(R.id.btn_handler3);
        mBtnAsync = (Button) findViewById(R.id.btn_async);
        mBtnHandler = (Button) findViewById(R.id.btn_handler);
        mBtnAIDL = (Button) findViewById(R.id.btn_AIDL);

        mBtnDownloadservices.setOnClickListener(this);
        mBtnHandler3.setOnClickListener(this);
        mBtnAsync.setOnClickListener(this);
        mBtnHandler.setOnClickListener(this);
        mBtnSingle.setOnClickListener(this);
        mBtnAIDL.setOnClickListener(this);
        mBtnSocket.setOnClickListener(this);
        mBtnAIDL2.setOnClickListener(this);
        mBtnAIDL3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_handler:
            {
               // LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.HandlerPractice.class);
                break;
            }
            case R.id.btn_async:
            {
                LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.AsyncTaskPractice.class);
                break;
            }
            case R.id.btn_downloadservices:
            {
               // LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.DownloadService.class);
                break;
            }
            case R.id.btn_single:
            {
                //LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.SingleServices.class);
                break;
            }
            case R.id.btn_AIDL:
            {
                //LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.SaleQuery.class);
                break;
            }
            case R.id.btn_Socket:
            {
                //LSComponentsHelper.startActivity(this, com.linson.android.hiandroid2.Services.SocketPracice.class);
                LSComponentsHelper.startActivity(this, SocketPractice.class);
                break;
            }
            case R.id.btn_AIDL2:
            {
                LSComponentsHelper.startActivity(this, SysAidl.class);
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index12);
        findControls();
    }
}