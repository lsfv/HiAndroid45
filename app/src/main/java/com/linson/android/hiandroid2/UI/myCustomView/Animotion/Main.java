package com.linson.android.hiandroid2.UI.myCustomView.Animotion;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.linson.LSLibrary.AndroidHelper.LSAnimation;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;
import java.util.ArrayList;
import java.util.List;


//!!!!!!支付成功的 路径动画没有去做。
//obj:转奖盘，波扩散，马奔跑。抛物线，支付成功，电话震动
//总结，能view实现就view。因为最简洁。但是需要属性，或者同个功能需要连接进行不同效果，那就不能用view动画了。
//如果可以，优先一个value的动画进展中实现所有动画，因为分开的话顺序不同，测试发现效果有有分别。
//最后能效果稍微复杂，也可以考虑用frame的hardcode的方法，来实现动画，是一个很简单粗暴的方式。
//value 派生出来的object animation .使用过程中，感觉就是一个半成品，定义一个属性的变化，要靠程序员，分别拼写正确类型名字和字段类型。
//简直无语。要不把常用属性，单独写出各自方法。也比这好啊.感觉不如自己用value封装出几个常用属性。来简化，也比object 动画好。
public class Main extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnHouse1;
    private Button mBtnHouse2;
    private Button mBtnHouse3;
    private Button mBtnThrow1;
    private Button mBtnThrow2;
    private Button mBtnThrow3;
    private Button mBtnLoopPan3;
    private Button mBtnWave3;
    private Button mBtnLoopPan2;
    private Button mBtnWave2;
    private Button mBtnWave;
    private ImageView mImageView12;
    private ImageView mImageView13;
    private Button mBtnLoopPan;
    private ImageView mWave1;
    private ImageView mWave2;
    private ImageView mWave3;
    private ImageView mImageView14;
    private TextView mTvMsg;
    private ImageView mImageView15;
    private ImageView mIvHouse;
    private ImageView mImageView16;


    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnHouse1 = (Button) findViewById(R.id.btn_house1);
        mBtnHouse2 = (Button) findViewById(R.id.btn_house2);
        mBtnHouse3 = (Button) findViewById(R.id.btn_house3);
        mBtnThrow1 = (Button) findViewById(R.id.btn_throw1);
        mBtnThrow2 = (Button) findViewById(R.id.btn_throw2);
        mBtnThrow3 = (Button) findViewById(R.id.btn_throw3);
        mBtnLoopPan3 = (Button) findViewById(R.id.btn_loopPan3);
        mBtnWave3 = (Button) findViewById(R.id.btn_Wave3);
        mBtnLoopPan2 = (Button) findViewById(R.id.btn_loopPan2);
        mBtnWave2 = (Button) findViewById(R.id.btn_Wave2);
        mBtnWave = (Button) findViewById(R.id.btn_Wave);
        mImageView12 = (ImageView) findViewById(R.id.imageView12);
        mImageView13 = (ImageView) findViewById(R.id.imageView13);
        mBtnLoopPan = (Button) findViewById(R.id.btn_loopPan);
        mWave1 = (ImageView) findViewById(R.id.wave1);
        mWave2 = (ImageView) findViewById(R.id.wave2);
        mWave3 = (ImageView) findViewById(R.id.wave3);
        mImageView14 = (ImageView) findViewById(R.id.imageView14);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mImageView15 = (ImageView) findViewById(R.id.imageView15);
        mIvHouse = (ImageView) findViewById(R.id.iv_house);
        mImageView16 = (ImageView) findViewById(R.id.imageView16);

        //set event handler
        mBtnLoopPan2.setOnClickListener(this);
        mBtnLoopPan.setOnClickListener(this);
        mBtnLoopPan3.setOnClickListener(this);
        mBtnWave.setOnClickListener(this);
        mBtnWave2.setOnClickListener(this);
        mBtnWave3.setOnClickListener(this);
        mBtnThrow1.setOnClickListener(this);
        mBtnThrow2.setOnClickListener(this);
        mBtnThrow3.setOnClickListener(this);
        mBtnHouse1.setOnClickListener(this);
        mBtnHouse2.setOnClickListener(this);
        mBtnHouse3.setOnClickListener(this);
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
            case R.id.btn_Wave2:
            {
                wave2();
                break;
            }
            case R.id.btn_Wave3:
            {
                wave3();
                break;
            }
            case R.id.btn_throw1:
            {
                throw1();break;
            }
            case R.id.btn_throw2:
            {
                throw2(2);break;
            }
            case R.id.btn_throw3:
            {
                throw3();break;
            }
            case R.id.btn_house1:
            {
                house1();break;
            }
            case R.id.btn_house2:
            {
                house2();break;
            }
            case R.id.btn_house3:
            {
                house3();break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //member variable
    private List<Object> gcobj=new ArrayList<>();//处理需要手动清除的资源。如这里的无线循环的动画，必须手动停止。

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

    //region wave 有个特别的就是如果组合动画的话，可以考虑proterty的value动画，因为不需要动画组合，所有属性的变化都写在属性更新的单个处理回调里面。优先view动画，满足不了那就object属性，特别复杂可以考虑value动画。
    //测试property 动画没有这个问题，无语。view动画的延迟函数是每次都会延迟。不知道是否是bug还是我这里没处理好。
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

    public void wave2()
    {
        ValueAnimator valueAnimator_wave=ValueAnimator.ofFloat(1,2);
        valueAnimator_wave.setDuration(9000);
        valueAnimator_wave.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_wave.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float current_size=(Float) animation.getAnimatedValue();
                mWave1.setScaleX(current_size);
                mWave1.setScaleY(current_size);
                mWave1.setImageAlpha((int)((2-current_size)*255));
            }
        });
        valueAnimator_wave.start();

        final ValueAnimator valueAnimator_wave2=ValueAnimator.ofFloat(1,2);
        valueAnimator_wave2.setDuration(9000);
        valueAnimator_wave2.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_wave2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float current_size=(Float) animation.getAnimatedValue();
                mWave2.setScaleX(current_size);
                mWave2.setScaleY(current_size);
                mWave2.setImageAlpha((int)((2-current_size)*255));
            }
        });

        valueAnimator_wave2.setStartDelay(3000);
        valueAnimator_wave2.start();


        final ValueAnimator valueAnimator_wave3=ValueAnimator.ofFloat(1,2);
        valueAnimator_wave3.setDuration(9000);
        valueAnimator_wave3.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_wave3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float current_size=(Float) animation.getAnimatedValue();
                mWave3.setScaleX(current_size);
                mWave3.setScaleY(current_size);
                mWave3.setImageAlpha((int)((2-current_size)*255));
            }
        });

        valueAnimator_wave3.setStartDelay(6000);
        valueAnimator_wave3.start();
    }

    public void wave3()
    {
        LSComponentsHelper.LS_Log.Log_INFO("wave3");
        AnimatorSet animatorSet_wave=LSAnimation.waveSpread(mWave1);
        animatorSet_wave.start();

        AnimatorSet animatorSet_wave2=LSAnimation.waveSpread(mWave2);
        animatorSet_wave2.setStartDelay(3000);
        animatorSet_wave2.start();

        AnimatorSet animatorSet_wave3=LSAnimation.waveSpread(mWave3);
        animatorSet_wave3.setStartDelay(6000);
        animatorSet_wave3.start();

    }
    //endregion

    //region 抛物线.这里是value 非常适合。  ofObject,offloat 都非常适合。
    // 基本上evacator作为自定义控件的必要的工具。否则object动画 要废了。

    private void throw1()
    {
        Toast.makeText(this, "view 好像实现不了,只能直线行走,好像最少需要21，才有个path interpalator.", Toast.LENGTH_SHORT).show();
    }

    //实现起来很简洁，代码也非常清晰流畅。good job.
    private void throw2(int type)
    {
        if(type==1)
        {
            throw2_1();
        }
        else
        {
            throw2_2();
        }
    }

    private void throw2_1()
    {
        ValueAnimator valueAnimator_throw=ValueAnimator.ofInt(0,100);
        valueAnimator_throw.setDuration(5000);
        valueAnimator_throw.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int xvalue=(int)animation.getAnimatedValue();
                int yValue=-(xvalue-50)*(xvalue-50)/25;

                mImageView15.setTranslationX(xvalue);
                mImageView15.setTranslationY(yValue);
            }
        });
        valueAnimator_throw.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_throw.start();
    }

    //如果没有复用，从简洁上来说比不过直接用单个value，如float，但是好处就是可以把计算放在evaluator中，这样复用的话，简洁程度是更好了。
    //设置变化的自定义对象，那么设置起始2个对象，一般是设置部分属性值。而其他属性值在EVAATOR中进行计算得出。
    private void throw2_2()
    {
        TypeEvaluator<PointF> evaluator=new TypeEvaluator<PointF>()
        {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue)
            {
                float xvalue=startValue.x+ fraction*( endValue.x-startValue.x);
                float yvalue=-(xvalue-50)*(xvalue-50)/25;
                PointF pointF=new PointF(xvalue,yvalue );
                return pointF;
            }
        };
        PointF startPointF=new PointF(0, 0);
        PointF endPointF=new PointF(100,0);
        ValueAnimator valueAnimator_throw=ValueAnimator.ofObject(evaluator,startPointF,endPointF);
        valueAnimator_throw.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float x=((PointF)animation.getAnimatedValue()).x;
                float y=((PointF)animation.getAnimatedValue()).y;
                LSComponentsHelper.LS_Log.Log_INFO(x+"."+y+".");
                mImageView15.setTranslationX(x);
                mImageView15.setTranslationY(y);
            }
        });
        valueAnimator_throw.setDuration(5000);
        valueAnimator_throw.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_throw.start();
        gcobj.add(valueAnimator_throw);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        for(Object obc :gcobj)
        {
            if(obc instanceof Animator)
            {
                ((Animator) obc).cancel();
            }
        }
    }

    //属性全部分开，在这种情况下，非常不简洁。基本属于不考虑的方案。因为对应关系的属性，被分开做动画，由set来保证他们之间的变化关系。如抛物线的xy，分开变化很不合理。
    private void throw3()
    {
        ObjectAnimator objectAnimator_throw=ObjectAnimator.ofFloat(mImageView15, "translationX",0,100);
        objectAnimator_throw.setDuration(5000);
        objectAnimator_throw.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator objectAnimator_throwy=ObjectAnimator.ofFloat(mImageView15, "translationY", 0,100);
        objectAnimator_throwy.setDuration(5000);
        objectAnimator_throwy.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator_throwy.setEvaluator(new FloatEvaluator()
        {
            @Override
            public Float evaluate(float fraction, Number startValue, Number endValue)
            {
                float startFloat = startValue.floatValue();
                float xvalue= startFloat + fraction * (endValue.floatValue() - startFloat);

                float yValue=-(xvalue-50)*(xvalue-50)/25;
                return ((float) yValue);
            }
        });

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(objectAnimator_throw,objectAnimator_throwy);
        animatorSet.start();
    }
    //endregion

    //region house
    //drawabale+view animation. 简单，清晰。
    private void house1()
    {
        AnimationDrawable animationDrawable_house=new AnimationDrawable();
        for(int i=1;i<6;i++)
        {
            String name="h"+i;
            int drawableid = getResources().getIdentifier(name, "drawable", getPackageName());
            animationDrawable_house.addFrame(getResources().getDrawable(drawableid), 200);
        }

        animationDrawable_house.setOneShot(false);
        mIvHouse.setImageDrawable(animationDrawable_house);
        animationDrawable_house.start();

        Animation animation_tr=new TranslateAnimation(0, 400, 0, -50);
        animation_tr.setDuration(5000);
        animation_tr.setRepeatCount(Animation.INFINITE);
        animation_tr.setInterpolator(new LinearInterpolator());
        mIvHouse.startAnimation(animation_tr);
    }


    //非常明显，复杂多了。而且发生，调用先后顺序不同。效果不同的情况。这样的话，还是一个 value流程，集合多个动画。
    private void house2()
    {
        //
        ValueAnimator valueAnimator_horse=ValueAnimator.ofInt(0,1000);
        valueAnimator_horse.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                int time=(int)animation.getAnimatedValue();

                int i=time/200+1;

                String name="h"+i;
                int drawableid = getResources().getIdentifier(name, "drawable", getPackageName());
                mIvHouse.setImageDrawable(getResources().getDrawable(drawableid));
            }
        });
        valueAnimator_horse.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_horse.setDuration(1000);


        TypeEvaluator<Point> typeEvaluator=new TypeEvaluator<Point>()
        {
            @Override
            public Point evaluate(float fraction, Point startValue, Point endValue)
            {
                Point point=new Point(startValue.x+(int)(fraction*(endValue.x-startValue.x)), startValue.y+(int)(fraction*(endValue.y-startValue.y)));
                return point;
            }
        };

        Point sp=new Point(0,0);
        Point ep=new Point(400,-50);
        ValueAnimator valueAnimator_move=ValueAnimator.ofObject(typeEvaluator,sp,ep);
        valueAnimator_move.setDuration(5000);
        valueAnimator_move.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator_move.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                Point point=(Point)animation.getAnimatedValue();
                mIvHouse.setTranslationX(point.x);
                mIvHouse.setTranslationY(point.y);
            }
        });


        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(valueAnimator_move,valueAnimator_horse);
        animatorSet.start();

    }

    //object 来做跑马，实在比value没多大新意，实现个电话响铃效果。
    //object 来做响铃。虽然是繁琐，但是也是必须的。 整个value 动画。就是多了几个特性，如动画集合，并可设置集合类型，是合并还是先后。并且是可以插入动画和动画集合的。这就让
    //再复杂的都成为了可能。当然如何去实现evater.会更简洁的多。
    private void house3()
    {
        //基本就是旋转和大小。很多方式都可以实现。用最基本的view animation 试下。顺时针60度，30度，60，30，再逆时针，60，30，60，30
        //发现不行。同个效果，没办法连接进行。那就用value 试下了。
        ObjectAnimator objectAnimator1=ObjectAnimator.ofFloat(mImageView16, "rotation", 0,60);
        objectAnimator1.setDuration(100);

        ObjectAnimator objectAnimator2=ObjectAnimator.ofFloat(mImageView16, "rotation", 60,30);
        objectAnimator2.setDuration(50);

        ObjectAnimator objectAnimator3=ObjectAnimator.ofFloat(mImageView16, "rotation", 30,60);
        objectAnimator3.setDuration(50);

        ObjectAnimator objectAnimator4=ObjectAnimator.ofFloat(mImageView16, "rotation", 60,30);
        objectAnimator4.setDuration(50);

        ObjectAnimator objectAnimator5=ObjectAnimator.ofFloat(mImageView16, "rotation", 30,60);
        objectAnimator5.setDuration(50);

        ObjectAnimator objectAnimator6=ObjectAnimator.ofFloat(mImageView16, "rotation", 60,-60);
        objectAnimator6.setDuration(100);

        ObjectAnimator objectAnimator7=ObjectAnimator.ofFloat(mImageView16, "rotation", -60,-30);
        objectAnimator7.setDuration(50);

        ObjectAnimator objectAnimator8=ObjectAnimator.ofFloat(mImageView16, "rotation", -30,-60);
        objectAnimator8.setDuration(50);

        ObjectAnimator objectAnimator9=ObjectAnimator.ofFloat(mImageView16, "rotation", -60,-30);
        objectAnimator9.setDuration(50);

        ObjectAnimator objectAnimator10=ObjectAnimator.ofFloat(mImageView16, "rotation", -30,-60);
        objectAnimator10.setDuration(50);

        ObjectAnimator objectAnimator11=ObjectAnimator.ofFloat(mImageView16, "rotation", -60,0);
        objectAnimator11.setDuration(100);

        ObjectAnimator objectAnimator101=ObjectAnimator.ofFloat(mImageView16, "scaleX", 1f,1.5f);
        objectAnimator101.setDuration(100);

        ObjectAnimator objectAnimator102=ObjectAnimator.ofFloat(mImageView16, "scaleY", 1f,1.5f);
        objectAnimator102.setDuration(100);


        ObjectAnimator objectAnimator103=ObjectAnimator.ofFloat(mImageView16, "scaleX", 1.5f,1f);
        objectAnimator101.setDuration(100);

        ObjectAnimator objectAnimator104=ObjectAnimator.ofFloat(mImageView16, "scaleY", 1.5f,1f);
        objectAnimator102.setDuration(100);

        AnimatorSet animatorSet_start=new AnimatorSet();
        animatorSet_start.playTogether(objectAnimator1,objectAnimator101,objectAnimator102);

        AnimatorSet animatorSet_end=new AnimatorSet();
        animatorSet_end.playTogether(objectAnimator11,objectAnimator103,objectAnimator104);



        AnimatorSet clockAnimation=new AnimatorSet();
        clockAnimation.playSequentially(animatorSet_start,objectAnimator2,objectAnimator3,objectAnimator4,objectAnimator5,objectAnimator6,objectAnimator7
                ,objectAnimator8,objectAnimator9,objectAnimator10,animatorSet_end);
        clockAnimation.start();

    }
    //endregion
}