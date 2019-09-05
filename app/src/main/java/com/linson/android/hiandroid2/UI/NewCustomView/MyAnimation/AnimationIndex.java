package com.linson.android.hiandroid2.UI.NewCustomView.MyAnimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSAnimation;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.util.List;

// view animation:1.转盘2.wave,
// value 3.抛物线
// frame 4.跑马，
// object5，电话响铃。
public class AnimationIndex extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnPan6;
    private Button mBtnPan4;
    private Button mBtnPan;
    private Button mBtnPan5;
    private Button mBtnPan3;
    private ImageView mImageView19;
    private TextView mTextView16;
    private ImageView mImageView21;
    private ImageView mImageView22;
    private ImageView mImageView23;
    private ImageView mImageView20;
    private ImageView mIvHorse;
    private ImageView mIvPhone;

    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnPan6 = (Button) findViewById(R.id.btn_pan6);
        mBtnPan4 = (Button) findViewById(R.id.btn_pan4);
        mBtnPan = (Button) findViewById(R.id.btn_pan);
        mBtnPan5 = (Button) findViewById(R.id.btn_pan5);
        mBtnPan3 = (Button) findViewById(R.id.btn_pan3);
        mImageView19 = (ImageView) findViewById(R.id.imageView19);
        mTextView16 = (TextView) findViewById(R.id.textView16);
        mImageView21 = (ImageView) findViewById(R.id.imageView21);
        mImageView22 = (ImageView) findViewById(R.id.imageView22);
        mImageView23 = (ImageView) findViewById(R.id.imageView23);
        mImageView20 = (ImageView) findViewById(R.id.imageView20);
        mIvHorse = (ImageView) findViewById(R.id.iv_horse);
        mIvPhone = (ImageView) findViewById(R.id.iv_phone);

        //set event handler
        mBtnPan.setOnClickListener(this);
        mBtnPan3.setOnClickListener(this);
        mBtnPan4.setOnClickListener(this);
        mBtnPan5.setOnClickListener(this);
        mBtnPan6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_pan:
            {
                rotation();
                break;
            }
            case R.id.btn_pan3:
            {
                wave();
                break;
            }
            case R.id.btn_pan4:
            {
                throwline();
                break;
            }
            case R.id.btn_pan5:
            {
                frameanimation();
                break;
            }
            case R.id.btn_pan6:
            {
                ring();
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //region other member variable
    //endregion



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_index);

        findControls();
    }

    private void rotation()
    {
        int drgree=-350;
        int degreecount=-((int)(drgree/(360f/8f))-1);
        RotateAnimation rotateAnimation_pan=new RotateAnimation(0, drgree, Animation.RELATIVE_TO_SELF,0.5f ,Animation.RELATIVE_TO_SELF,0.5f );
        rotateAnimation_pan.setDuration(5000);
        rotateAnimation_pan.setFillAfter(true);
        rotateAnimation_pan.setFillBefore(false);

        mImageView19.startAnimation(rotateAnimation_pan);

        mTextView16.setText("转到了"+degreecount);
    }

    //无限的话，暂时没简单方法，唯一成功的方法就似乎开线程延迟启动，setAnimation,还不知道如何用,要设置父控件什么的，真是尴尬。
    private void wave()
    {
        AnimationSet animationSet=LSAnimation.waveSpread2();
        AnimationSet animationSet2=LSAnimation.waveSpread2();
        AnimationSet animationSet3=LSAnimation.waveSpread2();

        animationSet2.setStartOffset(3000);
        animationSet3.setStartOffset(6000);
        mImageView21.startAnimation(animationSet);
        mImageView22.startAnimation(animationSet2);
        mImageView23.startAnimation(animationSet3);
    }

    private void throwline()
    {
        //RealThrow(mImageView19);
        RealThrow2(mImageView19);
    }

    //y=x*x/800 x ={0,400} 抛物线的方程式。
    //非常明显变化的不光是x的速率，还有y的速率。合在一起就是曲线的斜率。
    private void RealThrow(final View view)
    {
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,400);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int x= (int)animation.getAnimatedValue();
                int y=x*x/800;
                view.setTranslationX(x);
                view.setTranslationY(y);
            }
        });
        valueAnimator.start();;
    }


    private void RealThrow2(final View view)
    {
        Point startp=new Point(0, 0);
        Point endp=new Point(400,0);
        ValueAnimator valueAnimator=ValueAnimator.ofObject(new TypeEvaluator()
        {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue)
            {
                int startx=((Point)startValue).x;
                int endx=((Point)endValue).x;
                int currentX=(int)(startx+(endx-startx)*fraction);
                int currenty=currentX*currentX/800;
                return  new Point(currentX,currenty );
            }
        },startp,endp);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                Point point=(Point) animation.getAnimatedValue();
                view.setTranslationX(point.x);
                view.setTranslationY(point.y);
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();
    }


    private void frameanimation()
    {
        AnimationDrawable animationDrawable=new AnimationDrawable();
        for(int i=1;i<6;i++)
        {
            String name="h"+i;
            int drawableid = getResources().getIdentifier(name, "drawable", getPackageName());
            animationDrawable.addFrame(getResources().getDrawable(drawableid), 1000);
        }

        mIvHorse.setImageDrawable(animationDrawable);
        animationDrawable.start();
    }


    //顺时针60度，30度，60，30，再逆时针，60，30，60，30
    private void ring()
    {
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(mIvPhone, "rotation", 0,60);
        objectAnimator.setDuration(100);

        ObjectAnimator objectAnimator1=ObjectAnimator.ofFloat(mIvPhone, "rotation", 60,30);
        objectAnimator.setDuration(50);

        ObjectAnimator objectAnimator2=ObjectAnimator.ofFloat(mIvPhone, "rotation", 30,60);
        objectAnimator.setDuration(50);


        ObjectAnimator objectAnimator3=ObjectAnimator.ofFloat(mIvPhone, "rotation", 0,-60);
        objectAnimator.setDuration(100);

        ObjectAnimator objectAnimator4=ObjectAnimator.ofFloat(mIvPhone, "rotation", -60,-30);
        objectAnimator.setDuration(50);

        ObjectAnimator objectAnimator5=ObjectAnimator.ofFloat(mIvPhone, "rotation", -30,-60);
        objectAnimator.setDuration(50);

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playSequentially(objectAnimator,objectAnimator1,objectAnimator2,objectAnimator3,objectAnimator4,objectAnimator5);

        animatorSet.start();
    }


}