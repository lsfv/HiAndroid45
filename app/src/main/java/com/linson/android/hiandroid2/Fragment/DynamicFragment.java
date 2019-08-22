package com.linson.android.hiandroid2.Fragment;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

public class DynamicFragment extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnPopupstack;
    private Button mBtnSecond;
    private ConstraintLayout mFragmentHolder;
    private Button mBtnFrist;


    private void findcontrols()
    {
        mBtnPopupstack = (Button) findViewById(R.id.btn_popupstack);
        mBtnSecond = (Button) findViewById(R.id.btn_second);
        mFragmentHolder = (ConstraintLayout) findViewById(R.id.fragmentHolder);
        mBtnFrist = (Button) findViewById(R.id.btn_frist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        LSComponentsHelper.LS_Log.Log_INFO("activity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment);

        findcontrols();
        setControlsEvent();
        loadfragment();
    }

    private void setControlsEvent()
    {
        mBtnFrist.setOnClickListener(this);
        mBtnSecond.setOnClickListener(this);
        mBtnPopupstack.setOnClickListener(this);
    }


    private void loadfragment()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentHolder, new FirstFragment());
        int id=fragmentTransaction.commit();
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_frist:
            {
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, new FirstFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;
            }
            case R.id.btn_second:
            {
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentHolder, new secondFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            }
            case R.id.btn_popupstack:
            {
                FragmentManager fragmentManager=getSupportFragmentManager();
                if(fragmentManager.getBackStackEntryCount()>0)
                {
                    fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId() ,FragmentManager.POP_BACK_STACK_INCLUSIVE );
                }
                break;
            }
        }
    }
}