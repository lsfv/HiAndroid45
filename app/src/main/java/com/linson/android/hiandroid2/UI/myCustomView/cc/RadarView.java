package com.linson.android.hiandroid2.UI.myCustomView.cc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import static com.linson.LSLibrary.AndroidHelper.LSComponentsHelper.LS_CustomViewHelper.getCommonMeasure;

public class RadarView extends View
{
    private int mDefaultWidth=400;
    private int mDefaultHeight=400;
    private Paint mPaint;
    private Path path;
    public RadarView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        path=new Path();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LSComponentsHelper.LS_CustomViewHelper.SuggestMeasure suggestMeasure=getCommonMeasure(widthMeasureSpec, heightMeasureSpec, mDefaultWidth, mDefaultHeight,LSComponentsHelper.LS_CustomViewHelper.Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //正6边型比较好画，中心坐标x和边长一样长。假设画5个正6边型，边长分别为100,80,60,40,20

        mPaint.setColor(Color.BLACK);
        drawGeo(6, 100, canvas, mPaint,200,200,path);

        mPaint.setColor(Color.RED);
        drawGeo(6, 80, canvas, mPaint,200,200,path);

        mPaint.setColor(Color.GREEN);
        drawGeo(6, 60, canvas, mPaint,200,200,path);

        mPaint.setColor(Color.YELLOW);
        drawGeo(6, 40, canvas, mPaint,200,200,path);

        mPaint.setColor(Color.BLUE);
        drawGeo(6, 20, canvas, mPaint,200,200,path);

    }

    //画正n边形,应该检测下中心，中心必须可以画下图形。
    private void drawGeo(int LinesCount,int length,Canvas canvas,Paint paint,int centerX,int centerY,Path GeoPath)
    {
        //求每条边的角度，并计算坐标，并画出
        double arcDegree=(360/LinesCount)*Math.PI/180;

        GeoPath.reset();
        GeoPath.rewind();
        GeoPath.moveTo(centerX-length, centerY);
        for(int i=1;i<LinesCount;i++)
        {
            double tempDegree=arcDegree*i;
            int tempX=(int) (centerX-length*Math.cos(tempDegree));
            int tempY=centerY-(int) (length*Math.sin(tempDegree));
            LSComponentsHelper.LS_Log.Log_INFO(tempX+"."+tempY+"."+tempDegree+"cos"+Math.cos(tempDegree)+".sin:"+Math.sin(tempDegree));
            GeoPath.lineTo(tempX, tempY);
        }
        GeoPath.close();

        canvas.drawPath(GeoPath, paint);
    }
}