package com.linson.android.hiandroid2.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.CustomUI.LSCircleImagePlus;
import com.linson.android.hiandroid2.R;

public class customView extends LSBaseActivity implements View.OnClickListener
{
    private LSCircleImagePlus mLSCircleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        findcontrols();
    }

    private void findcontrols()
    {
        mLSCircleImage = (LSCircleImagePlus) findViewById(R.id.LSCircleImage);
        mLSCircleImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.LSCircleImage:
            {
                LSComponentsHelper.Log_INFO("click:");
                break;
            }
        }
    }
}