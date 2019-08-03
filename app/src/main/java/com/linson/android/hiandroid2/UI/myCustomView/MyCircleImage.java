package com.linson.android.hiandroid2.UI.myCustomView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.CustomUI.LSCircleImageFinal;
import com.linson.android.hiandroid2.R;

public class MyCircleImage extends AppCompatActivity implements View.OnClickListener
{
    private LSCircleImageFinal mMyhead;
    private TextView mTvEe;

    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mMyhead = (LSCircleImageFinal) findViewById(R.id.myhead);
        mTvEe = (TextView) findViewById(R.id.tv_ee);

        //set event handler
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            default:
            {
                break;
            }
        }
    }
    //endregion

    //member variable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_circle_image);
        findControls();


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        LSComponentsHelper.LS_Log.Log_INFO("ml:%d,pl:%d,left:%d",3,mTvEe.getPaddingLeft(),mTvEe.getLeft());
    }
}