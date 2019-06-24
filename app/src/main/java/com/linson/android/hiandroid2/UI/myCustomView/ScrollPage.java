package com.linson.android.hiandroid2.UI.myCustomView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;

public class ScrollPage extends AppCompatActivity implements View.OnClickListener
{

    private TextView mTvInbulid;
    private ScrollTextView mMytextviewcc;




    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mTvInbulid = (TextView) findViewById(R.id.tv_inbulid);
        mMytextviewcc = (ScrollTextView) findViewById(R.id.mytextviewcc);

        //set event handler
        mTvInbulid.setScrollbarFadingEnabled(false);//总是显示bar
        mTvInbulid.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvInbulid.setText(mAricle);
        mMytextviewcc.setIonScrollLeftRight(new ScrollHandler());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            default:
            {
                break;
            }
        }
    }
    //endregion
    //region functions of click event
    //endregion

    //member variable
    String mAricle="其实我一直准备写一篇关于Android事件分发机制的文章，从我的第一篇博客开始，就零零散散在好多地方使用到了Android事件分发的知识。也有好多朋友问过我各种问题，比如：onTouch和onTouchEvent有什么区别，又该如何使用？为什么给ListView引入了一个滑动菜单的功能，ListView就不能滚动了？为什么图片轮播器里的图片使用Button而不用ImageView？等等……对于这些问题，我并没有给出非常详细的回答，因为我知道如果想要彻底搞明白这些问题，掌握Android事件分发机制是必不可少的，而Android事件分发机制绝对不是三言两语就能说得清的\n" +
            "--------------------- \n" +
            "作者：guolin \n" +
            "来源：CSDN \n" +
            "原文：https://blog.csdn.net/guolin_blog/article/details/9097463 \n" +
            "版权声明：本文为博主原创文章，转载请附上博文链接！";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_page);
        findControls();
    }

    //region scroll
    private class ScrollHandler implements ScrollTextView.IonScrollLeftRight
    {
        @Override
        public void onScrollLeftOrRight(ScrollTextView who, boolean isLeft)
        {
            if(isLeft)
            {
                who.setText("left");
            }
            else
            {
                who.setText("right");
            }
        }
    }
    //endregion
}