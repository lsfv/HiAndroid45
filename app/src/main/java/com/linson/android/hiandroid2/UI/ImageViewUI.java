package com.linson.android.hiandroid2.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.CustomUI.LSCircleImage;
import com.linson.LSLibrary.CustomUI.LSDrawable_CircleImage;
import com.linson.android.hiandroid2.R;

public class ImageViewUI extends LSBaseActivity
{
    private ImageView mImageView4;
    private LSCircleImage mImageView5;
    private ImageView mImageView6;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        findcontrols();

        image1();//normal
        image4();
        image5();//custom view
        image6();//custom drawable
    }



    private void image1()
    {
        mImageView.setImageResource(R.drawable.back);
    }

    private void image4()
    {

    }

    private void image5()
    {
       //mImageView5.setmDrawable(R.drawable.mypic);
//        Bitmap aa=BitmapFactory.decodeResource(getResources(), R.drawable.back);
//        mImageView5.setBitmap(aa);
    }

    private void image6()
    {
//        LSDrawable_CircleImage lsDrawable_circleImage=new LSDrawable_CircleImage(BitmapFactory.decodeResource(getResources(), R.drawable.wxfix));
        LSDrawable_CircleImage lsDrawable_circleImage=new LSDrawable_CircleImage(this.getResources(), R.drawable.wxfix);
        mImageView6.setImageDrawable(lsDrawable_circleImage);
    }


    private void findcontrols()
    {
        mImageView4 = (ImageView) findViewById(R.id.imageView4);
        mImageView5 = (LSCircleImage) findViewById(R.id.imageView5);
        mImageView6 = (ImageView) findViewById(R.id.imageView6);
        mImageView = (ImageView) findViewById(R.id.imageView);
    }
}