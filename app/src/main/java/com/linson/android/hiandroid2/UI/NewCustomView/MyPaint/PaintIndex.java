package com.linson.android.hiandroid2.UI.NewCustomView.MyPaint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;


//基本画图:文字和基本图形
public class PaintIndex extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnBp5;
    private Button mBtnBp4;
    private Button mBtnBp3;
    private Button mBtnBp2;
    private Button mBtnBp;

    //region  findcontrols and bind click event.
    private void findControls()
    {
        //findControls
        mBtnBp5 = (Button) findViewById(R.id.btn_bp5);
        mBtnBp4 = (Button) findViewById(R.id.btn_bp4);
        mBtnBp3 = (Button) findViewById(R.id.btn_bp3);
        mBtnBp2 = (Button) findViewById(R.id.btn_bp2);
        mBtnBp = (Button) findViewById(R.id.btn_bp);

        //set event handler
        mBtnBp.setOnClickListener(this);
        mBtnBp2.setOnClickListener(this);
        mBtnBp3.setOnClickListener(this);
        mBtnBp4.setOnClickListener(this);
        mBtnBp5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_bp:
            {
                LSComponentsHelper.startActivity(this, BasePaint.class);
                break;
            }
            case R.id.btn_bp2:
            {
                LSComponentsHelper.startActivity(this, ComplexPaint.class);
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
        setContentView(R.layout.activity_paint_index);

        findControls();
    }
}