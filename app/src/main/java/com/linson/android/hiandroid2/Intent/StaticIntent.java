package com.linson.android.hiandroid2.Intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

public class StaticIntent extends LSBaseActivity
{
    private static String keyName1="book";
    private static String keyname2="guest";

    //最好只有一个启动函数。要不然，接受参数代码就需要判断了。页面逻辑过于复杂.
    public static void createInstance(Activity activity, int requestcode, Index.book book, Index.guester guester)
    {
        Intent intent=new Intent(activity, StaticIntent.class);
        intent.putExtra(keyName1,book );
        intent.putExtra(keyname2, guester);
        activity.startActivityForResult(intent, requestcode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_intent);

        Intent intent1=getIntent();
        if(intent1.getSerializableExtra(keyName1)!=null && intent1.getSerializableExtra(keyName1) instanceof Index.book)
        {
            LSComponentsHelper.Log_INFO(((Index.book) intent1.getSerializableExtra(keyName1)).name);
        }
        if(intent1.getSerializableExtra(keyname2)!=null &&intent1.getSerializableExtra(keyname2) instanceof Index.guester)
        {
            LSComponentsHelper.Log_INFO(((Index.guester)intent1.getSerializableExtra(keyname2)).name);
        }

        Index.book book=new Index.book("java", 3);
        Intent intent=new Intent();
        intent.putExtra("returnbooks", book);
        setResult(RESULT_OK, intent);
    }

}