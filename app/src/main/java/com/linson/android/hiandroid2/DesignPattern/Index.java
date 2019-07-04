package com.linson.android.hiandroid2.DesignPattern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;

public class Index extends AppCompatActivity
{
    private TextView mTvMsg;

    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
    }
    //endregion

    //member variable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index17);
        findControls();
    }
}