package com.linson.android.hiandroid2.Services;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.BackServices.MP3Services;
import com.linson.android.hiandroid2.R;

import java.util.ArrayList;
import java.util.List;


//还差一个stop Services.
public class Index2 extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnStop;
    private Button mBtnUnbindALL;
    private Button mButton11;
    private Button mBtnBind;
    private Button mButton9;




    private void findcontrols()
    {
        mBtnStop = (Button) findViewById(R.id.btn_Stop);
        mBtnUnbindALL = (Button) findViewById(R.id.btn_unbindALL);
        mButton11 = (Button) findViewById(R.id.button11);
        mBtnBind = (Button) findViewById(R.id.btn_bind);
        mButton9 = (Button) findViewById(R.id.button9);
    }


    private List<ServiceConnection> tempConnection=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index10);

        findcontrols();
        setEvent();
    }

    private void setEvent()
    {
        mBtnBind.setOnClickListener(this);
        mButton9.setOnClickListener(this);
        mButton11.setOnClickListener(this);
        mBtnUnbindALL.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    private com.linson.android.hiandroid2.Services.Index.Mp3Connection connection;
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button11://unbind
            {
                try
                {
                    unbindService(connection);
                }
                catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                break;
            }
            case R.id.btn_bind://bind
            {
                try
                {
                    Intent intent=new Intent(this, MP3Services.class);
                    connection=new Index.Mp3Connection();
                    tempConnection.add(connection);
                    bindService(intent,connection ,Context.BIND_AUTO_CREATE);
                    LSComponentsHelper.LS_Log.Log_INFO("on click bind.conn:" + connection.toString() );
                }
                catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                break;
            }
            case R.id.button9://start
            {
                Intent intent=new Intent(this, MP3Services.class);
                startService(intent);
                break;
            }
            case R.id.btn_unbindALL:
            {
                for(ServiceConnection connection :tempConnection)
                {
                    try
                    {
                        unbindService(connection);
                    }
                    catch (Exception e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                }
            }
            case R.id.btn_Stop:
            {
                Intent intent=new Intent(this, MP3Services.class);
                stopService(intent);
                break;
            }
        }
    }
}