package com.linson.android.hiandroid2.UI.NewCustomView.MyPaint.customViewLib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper.LS_CustomViewHelper;
import com.linson.android.hiandroid2.R;

import java.util.Map;

//画线，虚线，空心圆，实心圆，三角，四边，n度弧，n边形，扇形，

//画笔的宽度strokewidth，宽度就是描边的宽度。也决定点的大小和线宽以及描边的宽。 setStyle决定空心，实心。
//ondraw, paint一定要重设，因为ondraw会执行多次,不 reset，画笔是默认初始为使用执行一次后最后的效果。
//画笔设置了shader后，会从0，0开始画，所以要想在定点话，必须先移动到某个点，再从0，0画。或者本意就是话局部,如把画切割为圆
public class basepaintPractice extends View
{
    private Paint mPaint;
    private Paint mPaint_stroke;
    public basepaintPractice(Context context,  AttributeSet attrs)
    {
        super(context, attrs);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);

        mPaint_stroke=new Paint();
        mPaint_stroke.setStyle(Paint.Style.STROKE);
        mPaint_stroke.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LS_CustomViewHelper.SuggestMeasure suggestMeasure= LS_CustomViewHelper.getCommonMeasure(widthMeasureSpec, heightMeasureSpec, 1000, 2000,LSComponentsHelper.LS_CustomViewHelper.Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        //点
        super.onDraw(canvas);
        mPaint.reset();// 一定要重设，因为ondraw会执行多次
        mPaint_stroke.reset();
        int x=50,y=50;
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(x, y, mPaint);
        //文字
        mPaint.setTextSize(50);
        canvas.drawText("0x,100y", 0, 100, mPaint);
        canvas.drawText("0x,0y", 0, 0, mPaint);
        //线
        canvas.drawLine(0, 120, 300, 120, mPaint);
        //三角
        //四边
        mPaint.setColor(Color.RED);
        canvas.drawRect(0, 150, 100, 250, mPaint);
        //circle
        canvas.drawCircle(400, 100, 50, mPaint);
        //arc弧
        RectF rectF=new RectF(20, 300, 220, 500);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF,mPaint );

        mPaint.setColor(Color.GREEN);
        canvas.drawArc(rectF, 300, 90, false, mPaint);

        canvas.drawArc(rectF, 180, 30, true, mPaint);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(rectF, 220, 30, true, mPaint);
        canvas.drawArc(rectF, 90, 60, false, mPaint);

        //画bitmap图1
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.wxfix);
        canvas.drawBitmap(bitmap,0 ,550 , mPaint);

        //用路径画虚线
        Path linepath=new Path();
        linepath.moveTo(200, 400);
        linepath.rLineTo(300, 0);
        PathEffect pathEffect=new DashPathEffect(new float[]{3,2}, 2);//3个单位实线。2个单位空白，2个单位偏移。
        mPaint_stroke.setPathEffect(pathEffect);
        canvas.drawPath(linepath, mPaint_stroke);
        //mPaint_stroke.setPathEffect(null);

        //路径画三角形。
        Path sanjiaoxing=new Path();
        sanjiaoxing.moveTo(150, 500);
        sanjiaoxing.rLineTo(100, 0);
        sanjiaoxing.rLineTo(-20, 100);
        sanjiaoxing.close();
        canvas.drawPath(sanjiaoxing,mPaint_stroke );

    }
}