package com.linson.android.hiandroid2.UI.myCustomView.cc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import static com.linson.LSLibrary.AndroidHelper.LSComponentsHelper.*;
import static com.linson.LSLibrary.AndroidHelper.LSComponentsHelper.LS_CustomViewHelper.*;

//画线，虚线，空心圆，实心圆，三角，四边，n度弧，n边形，扇形，路径叠加.
public class basepp extends View
{
    public basepp(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=600;
        int height=1900;
        SuggestMeasure suggestMeasure=getCommonMeasure(widthMeasureSpec, heightMeasureSpec, width, height,Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);


        Paint RedSolid=new Paint();
        RedSolid.setColor(Color.RED);
        RedSolid.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        canvas.drawLine(0, 50, 100, 100, paint);//线

        paint.setStyle(Paint.Style.STROKE);//中空
        canvas.drawCircle(250, 150, 100, paint);

        paint.setStyle(Paint.Style.FILL);//实心
        canvas.drawCircle(500, 150, 100, paint);

        //三角形。
        int startY=150;
        int startx=0;
        Point point1=new Point(0,startY );
        Point point2=new Point(100,startY );
        Point point3=new Point(50,startY +50);
        Path path=new Path();
        path.moveTo(point1.x, point1.y);
        path.lineTo(point2.x, point2.y);
        path.lineTo(point3.x, point3.y);

        canvas.drawPath(path, paint);
        startx+=0;
        startY+=150;

        //长方形
         point1=new Point(startx,startY );
         point2=new Point(startx+100,startY );
         point3=new Point(startx+100,startY +100);
        Point point4=new Point(startx,startY +100);
        Path path2=new Path();
        path2.moveTo(point1.x, point1.y);
        path2.lineTo(point2.x, point2.y);
        path2.lineTo(point3.x, point3.y);
        path2.lineTo(point4.x, point4.y);
        canvas.drawPath(path2, paint);
        startx+=150;

        //point
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(90);
        canvas.drawPoint(startx+10, startY+50, paint);
        paint.setStrokeWidth(2);
        startx+=50;

        //dash line 虚线
        paint.setStyle(Paint.Style.STROKE);
        Path linepath=new Path();
        linepath.moveTo(startx, startY);
        linepath.rLineTo(100, 0);
        PathEffect pathEffect=new DashPathEffect(new float[]{3,2}, 2);//3个单位实线。2个单位空白，2个单位偏移。
        paint.setPathEffect(pathEffect);
        canvas.drawPath(linepath, paint);

        startx=0;
        startY+=100;
        //n度弧
        float arcDu=100;
        paint=new Paint();
        //先画一个四边形，来看看和椭圆的关系。
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF=new RectF(startx+80, startY+80, startx+380, startY+280);
        canvas.drawRect(rectF, paint);

        //很清楚看到画弧是以四边形为外框的椭圆为基础来切割，以四边形中心为新坐标系来计算角度，
        Path arcPaht=new Path();
        arcPaht.addArc(rectF, 0, arcDu);
        canvas.drawPath(arcPaht, paint);

        //所以画一个扇形也是很简单。
        Path shanxing=new Path();
        shanxing.moveTo(startx+230, startY+180);
        shanxing.arcTo(rectF, 180, 60);
        shanxing.close();//自动闭合路径

        canvas.drawPath(shanxing, RedSolid);

        startx=0;
        startY+=200;

        //画半圆有很多办法,1弧度。2.path 相交
        RectF rectF22=new RectF(startx, startY, startx+200, startY+200);

        Path halfCircle=new Path();
        halfCircle.moveTo(startx+100, startY+100);
        halfCircle.arcTo(rectF22,180,180);
        halfCircle.close();//自动闭合路径

        canvas.drawPath(halfCircle, RedSolid);
        startx+=250;


        //2.path or region op.intersect.
        startx=0;
        startY+=150;
        Path path_circle=new Path();
        path_circle.addCircle(startx+100, startY+100, 100, Path.Direction.CW);
        Path path_circle2=new Path();
        path_circle2.addRect(startx,startY,startx+200,startY+100, Path.Direction.CW);

        Path newpath=new Path();
        newpath.op(path_circle, path_circle2, Path.Op.INTERSECT);
        canvas.drawPath(newpath, RedSolid);
    }
}