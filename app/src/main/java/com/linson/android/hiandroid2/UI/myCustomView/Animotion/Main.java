package com.linson.android.hiandroid2.UI.myCustomView.Animotion;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSAnimation;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.util.Random;


public class Main extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnLoopPan3;
    private Button mBtnWave3;
    private Button mBtnLoopPan2;
    private Button mBtnWave2;
    private Button mBtnWave;
    private ImageView mImageView12;
    private ImageView mImageView13;
    private Button mBtnLoopPan;
    private ImageView mImageView14;
    private ImageView mWave1;
    private ImageView mWave2;
    private ImageView mWave3;
    private TextView mTvMsg;





    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnLoopPan3 = (Button) findViewById(R.id.btn_loopPan3);
        mBtnWave3 = (Button) findViewById(R.id.btn_Wave3);
        mBtnLoopPan2 = (Button) findViewById(R.id.btn_loopPan2);
        mBtnWave2 = (Button) findViewById(R.id.btn_Wave2);
        mBtnWave = (Button) findViewById(R.id.btn_Wave);
        mImageView12 = (ImageView) findViewById(R.id.imageView12);
        mImageView13 = (ImageView) findViewById(R.id.imageView13);
        mBtnLoopPan = (Button) findViewById(R.id.btn_loopPan);
        mImageView14 = (ImageView) findViewById(R.id.imageView14);
        mWave1 = (ImageView) findViewById(R.id.wave1);
        mWave2 = (ImageView) findViewById(R.id.wave2);
        mWave3 = (ImageView) findViewById(R.id.wave3);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);

        //set event handler
        mBtnLoopPan2.setOnClickListener(this);
        mBtnLoopPan.setOnClickListener(this);
        mBtnLoopPan3.setOnClickListener(this);
        mBtnWave.setOnClickListener(this);
        mBtnWave2.setOnClickListener(this);
        mBtnWave3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_loopPan:
            {
                loopPan();
                break;
            }
            case R.id.btn_loopPan2:
            {
                loopPan2();
                break;
            }
            case R.id.btn_loopPan3:
            {
                loopPan3();
                break;
            }
            case R.id.btn_Wave:
            {
                wave1();
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //member variable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        findControls();
    }

    //region loop 旋转.view 动画能做的，尽量用view做。简洁。 并且尽量不要2类效果混合。
    //tween animation :书写比较简洁，清晰。
    //测试发现：这2种动画，如果是停留在动画的结束，那么下次动画会返回到动画的初始状态，但是2类动画，有各自的初始状态。
    //如果混用动画，那么开始某类动画，都只会回撤本类动画。也就是从另外一类动画的结尾开始进行动画。所以本例加了复原动作。
    //但是一般是不应该混用。这里只是做个例子，并给出方法，不知道是否有其他更简洁方式。
    private void loopPan()
    {
        //随机角度，得出结果，显示动画，显示结果。
        int randomDegree=-50;
        final float result=(float)((float)randomDegree/360)*8;
        LSComponentsHelper.LS_Log.Log_INFO(result+"..");

        final RotateAnimation animation_rotation=new RotateAnimation(0, randomDegree, Animation.RELATIVE_TO_SELF,0.5f ,Animation.RELATIVE_TO_SELF , 0.5f);
        animation_rotation.setDuration(5000);
//        animation_rotation.setFillAfter(true);
//        animation_rotation.setFillBefore(false);

        animation_rotation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            { }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                mTvMsg.setText("resulta:"+(int)(Math.abs(result)+1));
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            { }
        });
        mImageView12.startAnimation(animation_rotation);

    }

    //property。书写虽然也简短，但是因为是自定义更改属性，所以需要了解属性之间的相关性，因为并没有专门为动画设置统一方法。
    //如旋转，并像没有view animation 提供旋转的各种方式的直接调用。而是必须了解一些属性的默认值。
    private void loopPan2()
    {
        final int randomDegree=-30;
        final float result=(float)((float)randomDegree/360)*8;
        ValueAnimator valueAnimator_int=ValueAnimator.ofInt(0,randomDegree);
        valueAnimator_int.setDuration(5000);
        valueAnimator_int.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int vv=(int)animation.getAnimatedValue();
                mImageView12.setRotation((float)(vv));
            }
        });
        valueAnimator_int.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            { }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                mTvMsg.setText("resultb:"+(int)(Math.abs(result)+1));
                //做一个复原的动画，以免影响view动画的初始数值。
                ObjectAnimator objectAnimator_rotator=ObjectAnimator.ofFloat(mImageView12, "rotation", 0,0);
                objectAnimator_rotator.start();

            }

            @Override
            public void onAnimationCancel(Animator animation)
            { }

            @Override
            public void onAnimationRepeat(Animator animation)
            { }
        });
        valueAnimator_int.start();
    }


    //首先属性和value类型必须匹配，否则编译不通过。书写和view animotion 一样简洁。
    private void loopPan3()
    {
        final int randomDegree=-280;
        final float result=(float)((float)randomDegree/360)*8;
        final ObjectAnimator objectAnimator_rotator=ObjectAnimator.ofFloat(mImageView12, "rotation", 0,randomDegree);
        objectAnimator_rotator.setDuration(5000);
        objectAnimator_rotator.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            { }
            @Override
            public void onAnimationEnd(Animator animation)
            {
                mTvMsg.setText("resultc:"+(int)(Math.abs(result)+1));
                //做一个复原的动画，以免影响view动画的初始数值。
                ObjectAnimator objectAnimator_rotator=ObjectAnimator.ofFloat(mImageView12, "rotation", 0,0);
                objectAnimator_rotator.start();
            }
            @Override
            public void onAnimationCancel(Animator animation)
            { }

            @Override
            public void onAnimationRepeat(Animator animation)
            { }
        });
        objectAnimator_rotator.start();
    }

    //endregion

    //region wave 如果需要无限波浪，动画的延迟函数是每次都会延迟。这必须要设置为只需第一次延迟。取用新线程，睡眠，并赚到ui启动动画。
    public void wave1()
    {
        AnimationSet AnimationSet_wave=LSAnimation.waveSpread();
        mWave1.startAnimation(AnimationSet_wave);

        final AnimationSet AnimationSet_wave2=LSAnimation.waveSpread();
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mWave2.startAnimation(AnimationSet_wave2);
                        }
                    });
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }).start();



        final AnimationSet AnimationSet_wave3=LSAnimation.waveSpread();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(6000);
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            mWave3.startAnimation(AnimationSet_wave3);
                        }
                    });
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }).start();

    }
    //endregion

    //region

    //endregion

}