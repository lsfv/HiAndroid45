package com.linson.android.hiandroid2.UI.NewCustomView.MyView.viewLib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import com.linson.android.hiandroid2.R;
import static com.linson.LSLibrary.AndroidHelper.LSComponentsHelper.LS_CustomViewHelper.*;


//属性文件<resources>  <declare-styleable name="CircleImage"> name意思是这个是哪个控件的属性，ide会智能提示，但是测试发现用强行用别的控件的属性
//也是可以的，除了没有智能提示.
//再做自定义控件的时候，原则就是尽量让流程简洁，尽量让人性化放到外部，因为我们不是做完美通用控件。
//如这里，所有图片直接一步缩放到控件的大小。
public class CircleImage extends View
{
    private float radius=0;
    private int picID=0;
    private static int mDefaultRadius=100;
    private Paint mPaint;

    public CircleImage(Context context,AttributeSet attrs)
    {
        super(context, attrs);
        int[] attrsnames=R.styleable.CircleImage;
        TypedArray attrsValues= context.obtainStyledAttributes(attrs, attrsnames);
        int radiusID=R.styleable.CircleImage_CircleImage_radius;
        int imgID=R.styleable.CircleImage_CircleImage_img;
        radius=attrsValues.getDimension(radiusID, radius);
        picID=attrsValues.getResourceId(imgID, picID);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        SuggestMeasure suggestMeasure=getCommonMeasure(widthMeasureSpec, heightMeasureSpec, mDefaultRadius, mDefaultRadius, Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(picID!=0)
        {
            //获得图片，直接缩放到控件大小,起中心点为圆心，短边为参考半径，用户定义的半径如果大于0，小于参考半径那么为合理半径。
            //画笔的shader直接为缩放后的图.
            int width=getWidth();
            int height=getHeight();
            Bitmap bitmap_source = BitmapFactory.decodeResource(getResources(), picID);
            Bitmap bitmap_scale=Bitmap.createScaledBitmap(bitmap_source,width,height,true);
            int centerX=width/2;
            int centerY=height/2;
            int preradius=Math.min(centerX, centerY);
            if(radius<=0 || radius>preradius)
            {
                radius=preradius;
            }
            BitmapShader bitmapShader=new BitmapShader(bitmap_scale, Shader.TileMode.CLAMP  ,Shader.TileMode.CLAMP);
            mPaint.setShader(bitmapShader);
            canvas.drawCircle(centerX, centerY, radius, mPaint);
        }
        else
        {
            super.onDraw(canvas);
        }
    }
}