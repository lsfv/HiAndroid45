package com.linson.android.hiandroid2.Intent;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.io.Serializable;
import java.net.URI;


//intent是解决Android应用的各项组件之间的通讯。根据场景不同，创建intent有很多不同的构造函数。
//activity有3种，直接指明组件名，或者action+cateory 系统的activiy就是，name+uri，如打电话界面。
//广播一般 name 就可以。
//服务，指明组件名。Intent intent=new Intent(this, CaculateServices.class);startService(intent);
//content，很少用。
//具体方式如下。
//1.name+category activity的注册和启动。应该可以应用到所有组件
// xml注册时，添加上intent-filter节点。
// 启动 Intent intent=new Intent("actionName");intent.addCategory("Category Name");startActivity(intent);
//2.name注册和启动
//注册广播意图Intent sendBroadcastIntent=new Intent("mmbroadcast");sendBroadcastIntent.putExtra("test", 2);
//启动广播sendBroadcast(sendBroadcastIntent);
//注册接收广播意图 IntentFilter intent_recieve=new IntentFilter("mmbroadcast");
//启动接收广播registerReceiver(new BroadcastReceiver()
//3 name+data
//启动系统的activity。Intent telIntent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086"));
//4组件名字
//activity 和service都是一样。Intent intent=new Intent(this, CaculateServices.class);startService(intent);
public class Index extends LSBaseActivity implements View.OnClickListener
{
    private Button mBtnIntent4;
    private Button mBtnIntent3;
    private Button mBtnIntent2;
    private Button mBtnSericallback;
    private Button mBtnTel;
    private Button mBtnDynamic;
    private Button mBtnStatic;
    private Button mBtnIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index3);

        findControls();
    }

    private void findControls()
    {
        mBtnIntent4 = (Button) findViewById(R.id.btn_intent4);
        mBtnIntent3 = (Button) findViewById(R.id.btn_intent3);
        mBtnIntent2 = (Button) findViewById(R.id.btn_intent2);
        mBtnSericallback = (Button) findViewById(R.id.btn_sericallback);
        mBtnTel = (Button) findViewById(R.id.btn_tel);
        mBtnDynamic = (Button) findViewById(R.id.btn_dynamic);
        mBtnStatic = (Button) findViewById(R.id.btn_static);
        mBtnIntent = (Button) findViewById(R.id.btn_intent);

        mBtnDynamic.setOnClickListener(this);
        mBtnStatic.setOnClickListener(this);
        mBtnTel.setOnClickListener(this);
        mBtnSericallback.setOnClickListener(this);
        mBtnIntent.setOnClickListener(this);
        mBtnIntent2.setOnClickListener(this);
        mBtnIntent3.setOnClickListener(this);
        mBtnIntent4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_dynamic:
            {
                    Intent intent=new Intent("TESTDynamic");
                    intent.addCategory("CUSTOM_DY1");
                    startActivity(intent);
                break;
            }
            case R.id.btn_static:
            {
                Intent intent=new Intent(this, StaticIntent.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_tel:
            {
                Intent telIntent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086"));
                startActivity(telIntent);
                break;
            }
            case R.id.btn_sericallback:
            {
                book csharp = new book("c++", 2);
                guester guester = new guester("linson", 1);
                StaticIntent.createInstance(this,1, csharp, guester);
                break;
            }
            case R.id.btn_intent:
            {
                Toast.makeText(this, "按后面2个", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_intent2:
            {
                break;
            }
            case R.id.btn_intent3:
            {
                IntentFilter intent_recieve=new IntentFilter("mmbroadcast");
                registerReceiver(new BroadcastReceiver()
                {
                    @Override
                    public void onReceive(Context context, Intent intent)
                    {
                        int data=intent.getIntExtra("test", 1);
                        Toast.makeText(Index.this,"actionname 就可以。receive:"+data ,Toast.LENGTH_SHORT ).show();
                    }
                }, intent_recieve);

                Intent sendBroadcastIntent=new Intent("mmbroadcast");
                sendBroadcastIntent.putExtra("test", 2);
                sendBroadcast(sendBroadcastIntent);
                break;
            }
            case R.id.btn_intent4:
            {

                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode==1)
        {
            if(data!=null)
            {
                book res = (book) data.getSerializableExtra("returnbooks");
                LSComponentsHelper.Log_INFO("rcode:" + requestCode + ". book name:" + res.name);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    static class book implements Serializable
    {
         String name;
         Integer id;
         book(String a ,Integer b)
        {
            name=a;
            id=b;
        }
    }

    static class guester implements Serializable
    {
        String name;
        Integer sex;

        guester(String a ,Integer b)
        {
            name=a;
            sex=b;
        }
    }
}