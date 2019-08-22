package com.linson.android.hiandroid2.UI.NewCustomView.MyPaint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.linson.LSLibrary.CustomUI.LSCircleImageFinalV2;
import com.linson.android.hiandroid2.R;


//图片的组合，路径的组合，画布的移动。虚线。
public class ComplexPaint extends AppCompatActivity
{
    private LSCircleImageFinalV2 mCi1;
    private ImageView mIvTest1;

    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mCi1 = (LSCircleImageFinalV2) findViewById(R.id.ci1);
        mIvTest1 = (ImageView) findViewById(R.id.iv_test1);
    }

    //endregion

    //member variable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_paint);

        findControls();
        Bitmap bitmap_test=Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap_test);
        canvas.drawText("image", 20, 20, new Paint());
        mIvTest1.setBackground(new BitmapDrawable(this.getResources(),bitmap_test));
    }
}