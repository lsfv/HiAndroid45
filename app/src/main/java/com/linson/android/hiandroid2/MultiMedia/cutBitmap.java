package com.linson.android.hiandroid2.MultiMedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.CustomUI.LSDrawable_circleMask;
import com.linson.android.hiandroid2.R;

public class cutBitmap extends AppCompatActivity
{
    private ImageView mImageView2;
    private View mHaha;
    private Button mBtnOk;



    public static void showMe(Activity activity, Bitmap bitmap, int requestCode)
    {
        Intent intent=new Intent();
        //intent.putExtra("pic", bitmap);
        //activity.startActivityForResult(intent, requestCode);
        LSComponentsHelper.startActivity(activity, cutBitmap.class);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_bitmap);

        findControls();
        mHaha.setBackground(new LSDrawable_circleMask());
    }

    private void findControls()
    {
        mImageView2 = (ImageView) findViewById(R.id.imageView2);
        mHaha = (View) findViewById(R.id.haha);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
    }
}