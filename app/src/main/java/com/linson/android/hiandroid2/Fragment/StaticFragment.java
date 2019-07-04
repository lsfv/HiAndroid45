package com.linson.android.hiandroid2.Fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;


public class StaticFragment extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        LSComponentsHelper.LS_Log.Log_INFO("activity before super oncreate");
        super.onCreate(savedInstanceState);
        LSComponentsHelper.LS_Log.Log_INFO("activity after super oncreate");
        setContentView(R.layout.activity_static_fragment);
    }

    @Override
    protected void onStart()
    {
        LSComponentsHelper.LS_Log.Log_INFO("activity onstart");
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        LSComponentsHelper.LS_Log.Log_INFO("activity onResume");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        LSComponentsHelper.LS_Log.Log_INFO("activity onpause");
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        LSComponentsHelper.LS_Log.Log_INFO("activity onstop");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        LSComponentsHelper.LS_Log.Log_INFO("activity ondestroy");
        super.onDestroy();
    }
}