package com.linson.android.hiandroid2.UI.myCustomView.Animotion.ValueAnimation;

import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

//value animation 开始之后属性可随时修改，修改后的效果以当前的属性作为参照。
//自定义效果，如果是evulator和interpolator都可以实现，还是用evulater，毕竟返回的是使用值，而不是需要中转的进度值。
//例子：踢飞，自旋，波扩散，马奔跑。抛物线，支付成功，电话震动,动态菜单。
//view animation   tween:踢飞，自旋，波扩散，sin线，马奔跑。抛物线，电话震动
//                 frame :支付成功。frame可以固定套路实现任何功能
//propoty          value:踢飞，自旋，波扩散，sin线，马奔跑。抛物线，支付成功，电话震动（keyframe）
//                 obj:转奖盘，波扩散，sin线，马奔跑。抛物线，支付成功，电话震动
public class VAIndex extends AppCompatActivity implements View.OnClickListener
{
    private ImageView mImageView11;
    private Button mBtnObject;
    private Button mBtnOfObject;
    private Button mBtnEvalutor;
    private Button mBtnSimpleValue;
    private ImageView mImageView8;
    private ImageView mImageView9;
    private ImageView mImageView10;


    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mImageView11 = (ImageView) findViewById(R.id.imageView11);
        mBtnObject = (Button) findViewById(R.id.btn_Object);
        mBtnOfObject = (Button) findViewById(R.id.btn_OfObject);
        mBtnEvalutor = (Button) findViewById(R.id.btn_Evalutor);
        mBtnSimpleValue = (Button) findViewById(R.id.btn_simpleValue);
        mImageView8 = (ImageView) findViewById(R.id.imageView8);
        mImageView9 = (ImageView) findViewById(R.id.imageView9);
        mImageView10 = (ImageView) findViewById(R.id.imageView10);

        //set event handler
        mBtnObject.setOnClickListener(this);
        mBtnOfObject.setOnClickListener(this);
        mBtnEvalutor.setOnClickListener(this);
        mBtnSimpleValue.setOnClickListener(this);
        mImageView8.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LSComponentsHelper.LS_Log.Log_INFO("click me");
            }
        });
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_simpleValue:
            {
                SimpleValue();
                break;
            }
            case R.id.btn_Evalutor:
            {
                Evalutor();
                break;
            }
            case R.id.btn_OfObject:
            {
                Object();
                break;
            }
            case R.id.btn_Object:
            {
                objectSet();
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //member variable
    private ValueAnimator valueAnimator_Transfer;
    private ValueAnimator mValueAnimator_sin;
    private ValueAnimator mValueAnimator_sin2;
    private ValueAnimator mValueAnimator_siny;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaindex);
        findControls();
    }

    private void objectSet()
    {
        //移动效果
        ValueAnimator valueAnimator_rgb=ValueAnimator.ofFloat(0,100);
        valueAnimator_rgb.setDuration(5000);
        valueAnimator_rgb.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_rgb.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float value= (float)animation.getAnimatedValue();
                mImageView9.setScrollX((int)value);
            }
        });
        //向下效果。
        ObjectAnimator objectAnimator_tr=ObjectAnimator.ofFloat(mImageView9,"translationY",0f,100f);
        objectAnimator_tr.setDuration(5000);
        objectAnimator_tr.setRepeatCount(ValueAnimator.INFINITE);

        //组合可以混合组合value和object.
        //感觉大部分效果，由value就可以了，不需要object和set，最灵活的value.除非每个效果要的时间段不统一，基本不常见。
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(valueAnimator_rgb,objectAnimator_tr);
        animatorSet.start();
    }

    private void Object()
    {
        //惊呆了，google居然靠开发软件来检测"translationY"，这个是否输入正确，而不是在想办法在语法层面实现，ObjectAnimator还是尽量少用！
        ObjectAnimator objectAnimator_img=ObjectAnimator.ofFloat(mImageView11, "translationX",0f,100f);
        objectAnimator_img.setDuration(5000);
        objectAnimator_img.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator_img.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator_img.start();
    }

    private void Evalutor()
    {
        mValueAnimator_siny=ValueAnimator.ofFloat(0,(float) Math.PI*2);
        mValueAnimator_siny.setDuration(5000);
        mValueAnimator_siny.setEvaluator(new TypeEvaluator<Float>()
        {
            @Override
            public Float evaluate(float fraction, Float startValue, Float endValue)
            {
                double arch=(double) (startValue+ fraction*(endValue-startValue));
                return ((float) Math.sin(arch));
            }
        });
        mValueAnimator_siny.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float y=(Float) animation.getAnimatedValue();
                mImageView11.setTranslationX(0);
                mImageView11.setTranslationY(y*100);
            }
        });
        mValueAnimator_siny.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator_siny.start();
    }

    private void SimpleValue()
    {
        //简单值动画：建立值动画，并设置和实现其回调接口。
        valueAnimator_Transfer=ValueAnimator.ofInt(0,400);
        valueAnimator_Transfer.setDuration(5000);
        valueAnimator_Transfer.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                Integer changeingValue= (Integer)animation.getAnimatedValue();
                //LSComponentsHelper.LS_Log.Log_INFO(changeingValue+"...");
                mImageView8.setTranslationX(changeingValue);
                mImageView8.setTranslationY(-changeingValue);
                //mImageView8.layout((int)mImageView8.getLeft()+changeingValue, (int)mImageView8.getTop()-changeingValue, (int)mImageView8.getLeft()+changeingValue+mImageView8.getWidth(), (int)mImageView8.getTop()-changeingValue+mImageView8.getHeight());
            }
        });
        valueAnimator_Transfer.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        valueAnimator_Transfer.setRepeatMode(ValueAnimator.REVERSE);//反向重复
        valueAnimator_Transfer.start();//开始之后属性可随时修改，修改后的效果以当前的属性作为参照。

        //简单值动画，实现稍微复杂效果。sin
        mValueAnimator_sin=ValueAnimator.ofFloat(0,(float) Math.PI*2);
        mValueAnimator_sin.setDuration(5000);
        mValueAnimator_sin.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float arch=(float)animation.getAnimatedValue();
                mImageView10.setTranslationX(arch*100);
                float sinarch=(float)Math.sin((double)arch);
                mImageView10.setTranslationY(-sinarch*100);
                //LSComponentsHelper.LS_Log.Log_INFO(arch+"a");
            }
        });
        mValueAnimator_sin.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator_sin.start();

//        //自定义变速器,提早1半,从180度开始。但是感觉一般还是去自定义Evaluater, 毕竟最终使用的还是evaluater所返回的值。
//        mValueAnimator_sin2=ValueAnimator.ofFloat(0,(float) Math.PI*2);
//        mValueAnimator_sin2.setDuration(5000);
//        mValueAnimator_sin2.setInterpolator(new InterPolator_Sin());
//        mValueAnimator_sin2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
//        {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation)
//            {
//                float arch=(float)animation.getAnimatedValue();
//                mImageView11.setTranslationX(arch*100);
//                float sinarch=(float)Math.sin((double)arch);
//                mImageView11.setTranslationY(-sinarch*100);
//            }
//        });
//        mValueAnimator_sin2.setRepeatCount(ValueAnimator.INFINITE);
//        mValueAnimator_sin2.start();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        valueAnimator_Transfer.cancel();
        mValueAnimator_sin.cancel();
        mValueAnimator_sin2.cancel();
    }

    //region interpolator
    //入参input表示的是动画的时间进度，范围0～1，需要返回实际想要的进度，可以低0，超1.
    private static class InterPolator_Sin implements TimeInterpolator
    {
        @Override
        public float getInterpolation(float input)
        {
            return input+0.5f;
        }
    }
    //endregion
}