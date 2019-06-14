package com.linson.android.hiandroid2.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.android.hiandroid2.R;

public class dynamic2 extends LSBaseActivity
{

    private Button mBtnClose;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic2);

        mBtnClose = (Button) findViewById(R.id.btn_close);

        mBtnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LSBaseActivity.BackStack.ClearActivity();
            }
        });
    }
}