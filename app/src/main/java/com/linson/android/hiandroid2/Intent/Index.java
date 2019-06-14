package com.linson.android.hiandroid2.Intent;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.io.Serializable;
import java.net.URI;

public class Index extends LSBaseActivity implements View.OnClickListener
{
    private Button mBtnSericallback;
    private Button mBtnTel;
    private Button mBtnDynamic;
    private Button mBtnStatic;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index3);

        findControls();
    }

    private void findControls()
    {
        mBtnSericallback = (Button) findViewById(R.id.btn_sericallback);
        mBtnTel = (Button) findViewById(R.id.btn_tel);
        mBtnDynamic = (Button) findViewById(R.id.btn_dynamic);
        mBtnStatic = (Button) findViewById(R.id.btn_static);

        mBtnDynamic.setOnClickListener(this);
        mBtnStatic.setOnClickListener(this);
        mBtnTel.setOnClickListener(this);
        mBtnSericallback.setOnClickListener(this);
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