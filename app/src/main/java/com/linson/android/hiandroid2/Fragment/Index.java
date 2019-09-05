package com.linson.android.hiandroid2.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

public class Index extends AppCompatActivity implements View.OnClickListener
{
    //静态生命周期
    //动态加载，替换型。标题文章。fragment入栈的实践，引导注册。
    //双页面的入栈实践。测试。
    private Button mBtnDynamic;
    private Button mBtnStack;
    private Button mBtnComplexstack;
    private Button mBtnStatic;
    private void findControls()
    {
        mBtnDynamic = (Button) findViewById(R.id.btn_dynamic);
        mBtnStack = (Button) findViewById(R.id.btn_stack);
        mBtnComplexstack = (Button) findViewById(R.id.btn_complexstack);
        mBtnStatic = (Button) findViewById(R.id.btn_static);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index11);
        findControls();
        setControlsEvent();
    }

    private void setControlsEvent()
    {
        mBtnStatic.setOnClickListener(this);
        mBtnDynamic.setOnClickListener(this);
        mBtnStack.setOnClickListener(this);
        mBtnComplexstack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_static:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, StaticFragment.class);
                break;
            }
            case R.id.btn_dynamic:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, DynamicFragment.class);
                break;
            }
            case R.id.btn_stack:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, InterAction_index.class);
                break;
            }
            case R.id.btn_complexstack:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, complexfragment.class);
                break;
            }
        }
    }


}