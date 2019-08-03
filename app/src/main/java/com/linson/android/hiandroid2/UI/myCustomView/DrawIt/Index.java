package com.linson.android.hiandroid2.UI.myCustomView.DrawIt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.UI.myCustomView.MySignalRV;

public class Index extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnBasePaint3;
    private Button mBtnBasePaint2;
    private Button mBtnBasePaint;



    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnBasePaint3 = (Button) findViewById(R.id.btn_basePaint3);
        mBtnBasePaint2 = (Button) findViewById(R.id.btn_basePaint2);
        mBtnBasePaint = (Button) findViewById(R.id.btn_basePaint);

        //set event handler
        mBtnBasePaint.setOnClickListener(this);
        mBtnBasePaint2.setOnClickListener(this);
        mBtnBasePaint3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_basePaint:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, BasePaint.class);
                break;
            }
            case R.id.btn_basePaint2:
            {
                LSComponentsHelper.LS_Activity.startActivity(this, AdvancePraice.class);
                break;
            }
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
        setContentView(R.layout.activity_index18);
        findControls();
    }
}