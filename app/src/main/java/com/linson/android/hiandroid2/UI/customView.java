package com.linson.android.hiandroid2.UI;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.CustomUI.LSCircleImagePlus;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.UI.NewCustomView.MyAnimation.AnimationIndex;
import com.linson.android.hiandroid2.UI.NewCustomView.MyPaint.PaintIndex;
import com.linson.android.hiandroid2.UI.NewCustomView.MyView.ViewIndex;
import com.linson.android.hiandroid2.UI.myCustomView.scroller1;


//画图
//定制控件
//动画
public class customView extends LSBaseActivity implements View.OnClickListener
{
    private Button mBtnAnimation;
    private Button mBtnCustomView;
    private Button mBtnPaint;


    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnAnimation = (Button) findViewById(R.id.btn_animation);
        mBtnCustomView = (Button) findViewById(R.id.btn_customView);
        mBtnPaint = (Button) findViewById(R.id.btn_paint);

        //set event handler
        mBtnPaint.setOnClickListener(this);
        mBtnCustomView.setOnClickListener(this);
        mBtnAnimation.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_paint:
            {
                LSComponentsHelper.startActivity(this, PaintIndex.class);
                break;
            }
            case R.id.btn_customView:
            {
                LSComponentsHelper.startActivity(this, ViewIndex.class);
                break;
            }
            case R.id.btn_animation:
            {
                LSComponentsHelper.startActivity(this, AnimationIndex.class);
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
        setContentView(R.layout.activity_custom_view);

        findControls();
    }
}