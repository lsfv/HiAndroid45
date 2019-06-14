package com.linson.android.hiandroid2.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

//开始设置样式。并记得记录布局的关键点到背诵笔记。
public class TextViewButtom extends LSBaseActivity implements View.OnClickListener
{

    private TextView mTvFramework;
    private Button mBtSolid;
    private TextView mTvSolid;
    private Button mButtonSubmit;
    private EditText mEtTest;
    private ProgressBar mPbTest;
    private Button mButtonProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);


        findControls();
    }

    private void findControls()
    {
        mTvFramework = (TextView) findViewById(R.id.tv_framework);
        mBtSolid = (Button) findViewById(R.id.bt_solid);
        mTvSolid = (TextView) findViewById(R.id.tv_solid);
        mButtonSubmit = (Button) findViewById(R.id.button_submit);
        mEtTest = (EditText) findViewById(R.id.et_test);
        mPbTest = (ProgressBar) findViewById(R.id.pb_test);
        mButtonProgress = (Button) findViewById(R.id.button_progress);

        mBtSolid.setOnClickListener(this);
        mButtonSubmit.setOnClickListener(this);
        mButtonProgress.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.bt_solid:
            {
                LSComponentsHelper.Log_INFO(mEtTest.getText().toString());
                break;
            }
            case R.id.button_submit:
            {
                LSComponentsHelper.Log_INFO(mEtTest.getText().toString());
                break;
            }
            case R.id.button_progress:
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        for(int i=1;i<=10;i++)
                        {
                            try
                            {
                                Thread.sleep(1000);
                            }
                            catch (InterruptedException e)
                            {
                                LSComponentsHelper.Log_Exception(e);
                            }
                            mPbTest.setProgress(i*10);
                        }
                    }
                }).start();
                break;
            }
        }
    }
}