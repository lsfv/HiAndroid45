package com.linson.LSLibrary.AndroidHelper;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public abstract class LSAnimation
{
    //踢飞旋转效果
    public static AnimationSet selfRotate_translate(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta,float startDegrees,float endDegrees,long duration)
    {
        //each animation
        Animation animation_translate2=new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        Animation animation_rotate=selfRotate(startDegrees, endDegrees);
        animation_rotate.setInterpolator(new AccelerateInterpolator());

        Animation animation_scroll=new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_SELF,0.5f ,Animation.RELATIVE_TO_SELF , 0.5f);

        Animation animation_apach=new AlphaAnimation(1, 0.1f);
        animation_apach.setInterpolator(new AnticipateInterpolator());

        //set add
        AnimationSet animationSet_set1=new AnimationSet(false);//变速器是否统一.
        animationSet_set1.addAnimation(animation_rotate);
        animationSet_set1.addAnimation(animation_translate2);
        animationSet_set1.addAnimation(animation_scroll);
        animationSet_set1.addAnimation(animation_apach);
        //set atrr
        animationSet_set1.setDuration(duration);
        animationSet_set1.setFillAfter(true);
        animationSet_set1.setFillBefore(false);

        return animationSet_set1;
    }

    //无限旋转
    public static AnimationSet selfRotateForever()
    {
        AnimationSet animationSet_set1=new AnimationSet(false);//变速器是否统一.

        Animation animation_rotate=new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF ,0.5f , Animation.RELATIVE_TO_SELF ,0.5f );
        animation_rotate.setRepeatCount(Animation.INFINITE);
        animation_rotate.setDuration(2000);
        animation_rotate.setInterpolator(new LinearInterpolator());//默认是加速的

        animationSet_set1.addAnimation(animation_rotate);

        return animationSet_set1;
    }

    //波扩散效果
    public static AnimationSet waveSpread()
    {
        AnimationSet animationSet_set1=new AnimationSet(true);//变速器是否统一.

        Animation animation_scale=new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f  );
        animation_scale.setDuration(9000);
        animation_scale.setRepeatCount(Animation.INFINITE);

        Animation animation_apache=new AlphaAnimation(1.0f, 0.1f);
        animation_apache.setDuration(9000);
        animation_apache.setRepeatCount(Animation.INFINITE);

        animationSet_set1.addAnimation(animation_scale);
        animationSet_set1.addAnimation(animation_apache);
        animationSet_set1.setFillAfter(true);
        animationSet_set1.setFillBefore(false);

        return animationSet_set1;
    }


    private static Animation selfRotate(float startDegrees,float endDegrees)
    {
        return new RotateAnimation(startDegrees, endDegrees, Animation.RELATIVE_TO_SELF,0.5f ,Animation.RELATIVE_TO_SELF ,0.5f );
    }


}