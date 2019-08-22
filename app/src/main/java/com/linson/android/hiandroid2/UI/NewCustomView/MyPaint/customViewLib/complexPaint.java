package com.linson.android.hiandroid2.UI.NewCustomView.MyPaint.customViewLib;

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
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.util.ArrayList;
import java.util.List;

//1.路径组合和图片组合的实际效果要到手机看，ide的效果是不准确的,甚至显示不出来。
//2.画虚线的画笔当时的环境测试需要描边的画笔。！！！
//3.canvace 是一个独立的类，可以在任何地方建立，并可以用它画任何东西，并从bitmap输出。
//图片的组合，路径的组合，画布的移动。虚线。水印。多边形
public class complexPaint extends View
{
    private Paint mPaint_fillGreen;
    private Paint mPaint_strokeRed;
    public complexPaint(Context context,AttributeSet attrs)
    {
        super(context, attrs);
        Init_Paint_fillGreen();
        Init_Paint_stroke();
    }

    private void Init_Paint_fillGreen()
    {
        if(mPaint_fillGreen==null)
        {
            mPaint_fillGreen=new Paint();
        }
        else
        {
            mPaint_fillGreen.reset();
        }
        mPaint_fillGreen.setColor(Color.GREEN);
        mPaint_fillGreen.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void Init_Paint_stroke()
    {
        if(mPaint_strokeRed==null)
        {
            mPaint_strokeRed=new Paint();
        }
        else
        {
            mPaint_strokeRed.reset();
        }
        mPaint_strokeRed.setColor(Color.RED);
        mPaint_strokeRed.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        LSComponentsHelper.LS_CustomViewHelper.SuggestMeasure suggestMeasure= LSComponentsHelper.LS_CustomViewHelper.getCommonMeasure(widthMeasureSpec, heightMeasureSpec, 1000, 2000,LSComponentsHelper.LS_CustomViewHelper.Enum_MeasureType.rate);
        setMeasuredDimension(suggestMeasure.width, suggestMeasure.height);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        Init_Paint_fillGreen();
        Init_Paint_stroke();

        //图片叠加,加水印，能外部实现的效果优先外部，如这里透明水印，可以做透明字，而不是用代码实现透明字体。
        Bitmap bitmap_wx=BitmapFactory.decodeResource(getResources(), R.drawable.wxfix);
        canvas.drawBitmap(bitmap_wx, 0, 0, mPaint_fillGreen);
        Bitmap bitmap_circleMasking=BitmapFactory.decodeResource(getResources(), R.drawable.waterprint);
        mPaint_fillGreen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        canvas.drawBitmap(bitmap_circleMasking, 0, 0, mPaint_fillGreen);


        //路径叠加
        Path path_circle=new Path();
        path_circle.addCircle(350, 50, 50, Path.Direction.CW);

        Path path_sqare=new Path();
        RectF rect_1=new RectF(350, 50, 400, 100);
        path_sqare.addRect(rect_1,Path.Direction.CCW);
        path_sqare.op(path_circle, Path.Op.INTERSECT);
        canvas.drawPath(path_sqare, mPaint_fillGreen);


        //虚线
        Path linepath=new Path();
        linepath.moveTo(0, 380);
        linepath.rLineTo(300, 0);
        PathEffect pathEffect=new DashPathEffect(new float[]{5,5}, 2);//3个单位实线。2个单位空白，2个单位偏移。
        Init_Paint_fillGreen();
        mPaint_strokeRed.setPathEffect(pathEffect);
        mPaint_strokeRed.setStrokeWidth(15);
        canvas.drawPath(linepath, mPaint_strokeRed);


        //bezier  曲线
        canvas.save();
        canvas.translate(0, 300);
        Path path_bezier=new Path();
        path_bezier.moveTo(0, 100);
        path_bezier.quadTo(100, 50, 200, 100);
        path_bezier.quadTo(300, 150, 400, 100);
        Init_Paint_stroke();
        canvas.drawPath(path_bezier, mPaint_strokeRed);
        canvas.restore();


        //完美圆形头像

        //正N边形,思路不是从多边形的2边的夹角处理。而是圆中的多边形把圆分割成等分的弧的思路，
        //最简便开始，取一个多边形顶点，如中心的正上方，那么可以求出任何其他顶点的位置。
        canvas.save();
        canvas.translate(0, 400);
        Init_Paint_stroke();

        for(int i=0;i<4;i++)
        {

            Path path_nline = getNpath(new Point(100, 100), 100-i*20, 7);
            canvas.drawPath(path_nline, mPaint_strokeRed);
        }
        canvas.restore();

        //图像叠加的蒙版作用
        canvas.save();
        canvas.translate(0, 600);
        Bitmap bitmap_mountain=BitmapFactory.decodeResource(getResources(), R.drawable.mypic);
        Bitmap bitmap_masking=BitmapFactory.decodeResource(getResources(), R.drawable.circle4);

        Rect rect_img=new Rect(0, 0, bitmap_mountain.getWidth(), bitmap_mountain.getHeight());
        Rect rect_dst=new Rect(0, 0, bitmap_masking.getWidth(), bitmap_masking.getHeight());
        canvas.drawBitmap(bitmap_mountain  ,rect_img ,rect_dst , mPaint_strokeRed);

        mPaint_strokeRed.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bitmap_masking, 0, 0, mPaint_strokeRed);

        canvas.restore();
    }


    private Path getNpath(Point centerPoint,int radies,int n)
    {
        double arcDegree=2*Math.PI/n;
        Path path_nline=new Path();
        path_nline.moveTo(centerPoint.x, centerPoint.y-radies);

        for(int i=0;i<n-1;i++)
        {
            path_nline.lineTo((float) (Math.sin(arcDegree*(i+1))*radies+centerPoint.x),(float)(-Math.cos(arcDegree*(i+1))*radies+centerPoint.y));
        }
        path_nline.close();
        return path_nline;
    }
}