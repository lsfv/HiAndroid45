package com.linson.android.hiandroid2.UI.myCustomView.Animotion;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSAnimation;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.UI.myCustomView.Animotion.ValueAnimation.VAIndex;


//view animation 都是作用在view上的，
//Tween 是直接作用于view，而frame也是作用于view，不过有点特别，就是view必须是图片，而且通过imageview展示出来。
public class Index extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnFrameAnimotion2;
    private Button mBtnFrameAnimotion;
    private Button mBtnTween3;
    private Button mBtnTween2;
    private Button mBtnTween;
    private ImageView mImageView3;
    private ImageView mImageView4;
    private ImageView mImageView5;
    private ImageView mImageView6;
    private TextView mTextView7;
    private TextView mTextView10;
    private ImageView mImageView7;





    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnFrameAnimotion2 = (Button) findViewById(R.id.btn_FrameAnimotion2);
        mBtnFrameAnimotion = (Button) findViewById(R.id.btn_FrameAnimotion);
        mBtnTween3 = (Button) findViewById(R.id.btn_tween3);
        mBtnTween2 = (Button) findViewById(R.id.btn_tween2);
        mBtnTween = (Button) findViewById(R.id.btn_tween);
        mImageView3 = (ImageView) findViewById(R.id.imageView3);
        mImageView4 = (ImageView) findViewById(R.id.imageView4);
        mImageView5 = (ImageView) findViewById(R.id.imageView5);
        mImageView6 = (ImageView) findViewById(R.id.imageView6);
        mTextView7 = (TextView) findViewById(R.id.textView7);
        mTextView10 = (TextView) findViewById(R.id.textView10);
        mImageView7 = (ImageView) findViewById(R.id.imageView7);

        //set event handler
        mBtnFrameAnimotion2.setOnClickListener(this);
        mBtnTween.setOnClickListener(this);
        mBtnTween2.setOnClickListener(this);
        mBtnTween3.setOnClickListener(this);
        mBtnFrameAnimotion.setOnClickListener(this);
        mImageView3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LSComponentsHelper.LS_Log.Log_INFO("click img");
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_tween:
            {
                tween1();
                break;
            }
            case R.id.btn_tween2:
            {
                tween2();
                break;
            }
            case R.id.btn_tween3:
            {
                tween3();
                break;
            }
            case R.id.btn_FrameAnimotion:
            {
                frame();
                break;
            }
            case R.id.btn_FrameAnimotion2:
            {
                LSComponentsHelper.startActivity(this, VAIndex.class);
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
        setContentView(R.layout.activity_index19);
        findControls();
    }


    public void frame()
    {
        //建立动画，设置给图片的BG，
        AnimationDrawable animation_frame=new AnimationDrawable();
        int pic=getResources().getIdentifier("mypic", "drawable", getPackageName());//模拟一般是很多规律图名，必须通过名字循环找。
        animation_frame.addFrame(getResources().getDrawable(pic),3000);
        animation_frame.addFrame(getResources().getDrawable(R.drawable.back),3000);
        animation_frame.setOneShot(false);

        mImageView7.setImageDrawable(animation_frame);
        animation_frame.start();

    }

    public void tween2()
    {
        AnimationSet animationSet_srt=LSAnimation.selfRotateForever();
        mImageView3.startAnimation(animationSet_srt);
    }

    public void tween3()
    {
        AnimationSet animationSet_srt=LSAnimation.waveSpread();

        AnimationSet animationSet_srt2=LSAnimation.waveSpread();
        animationSet_srt2.setStartOffset(3000);

        AnimationSet animationSet_srt3=LSAnimation.waveSpread();
        animationSet_srt3.setStartOffset(6000);


        mImageView5.startAnimation(animationSet_srt3);
        mImageView3.startAnimation(animationSet_srt);
        mImageView4.startAnimation(animationSet_srt2);
    }

    public void tween1()
    {
//        //load xml
//        Animation animation_translate=AnimationUtils.loadAnimation(this, R.anim.translate);
//        mImageView3.startAnimation(animation_translate);
//        //code
//        Animation animation_translate2=new TranslateAnimation(0, 400, 0, -400);
//        animation_translate2.setDuration(3000);
//        animation_translate2.setFillAfter(true);
//        animation_translate2.setFillBefore(false);
//        mImageView3.startAnimation(animation_translate2);

//        //load xml
//        Animation animation_translate=AnimationUtils.loadAnimation(this, R.anim.set_test);
//        mImageView3.startAnimation(animation_translate);


        AnimationSet animationSet_srt=LSAnimation.selfRotate_translate(0, 400, 0, -400, 0, 3600, 6000);
        mImageView3.startAnimation(animationSet_srt);
    }
}