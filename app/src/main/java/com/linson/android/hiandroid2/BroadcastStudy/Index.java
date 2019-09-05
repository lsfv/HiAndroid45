package com.linson.android.hiandroid2.BroadcastStudy;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;


//1，广播实现不允许开线程。 意图非常明显，广播就是一个轻量级的组件。目的就是发送通知，比如启动一个服务，等调用其他模块而已。
//2.
// 1.发送
//Intent sendBroadcastIntent=new Intent("customboradcast");
// sendBroadcast(sendBroadcastIntent);
// 2.接收
// IntentFilter intentFilter=new IntentFilter();
// intentFilter.addAction("customboradcast");
// registerReceiver(new CustomReceive(),intentFilter); (private class CustomReceive extends BroadcastReceiver)

//3.local boradcast 基本一样。只是由contextWraper 作为管理实体，改为了localbroadcastManager.
public class Index extends AppCompatActivity implements View.OnClickListener
{
    private TextView mTvMsg;
    private TextView mTextView2;
    private Button mBtnSendboradcast;
    private int tempInt=0;


    private String mNameOfVolumeBoradcast="android.media.VOLUME_CHANGED_ACTION";
    private String MNameOfCustom="custom";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index5);

        findControls();

        registerListen_Volumn();
        registerListen_Custom();
    }


    private void registerListen_Volumn()
    {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(mNameOfVolumeBoradcast);
        registerReceiver(new VolumnRecriver(),intentFilter);
    }

    private void registerListen_Custom()
    {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(MNameOfCustom);
        registerReceiver(new CustomReceive(),intentFilter);
    }

    private void findControls()
    {
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mTextView2 = (TextView) findViewById(R.id.textView2);
        mBtnSendboradcast = (Button) findViewById(R.id.btn_sendboradcast);
        mBtnSendboradcast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_sendboradcast:
            {
                Intent sendBroadcastIntent=new Intent(MNameOfCustom);
                sendBroadcastIntent.putExtra("test", tempInt);
                sendBroadcast(sendBroadcastIntent);
                tempInt++;
                break;
            }
        }
    }

    //innder class
    //从广播的回调函数可以看出，广播是个精简方案，并不会默认传递大量数据，只是发送通知而已，附加信息全靠intent来。因此系统广播没法附加额外信息。因为无法和接受方沟通参数。
    private  class VolumnRecriver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                AudioManager service=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int currentVolumn=service.getStreamVolume(AudioManager.STREAM_RING);
                int maxVolumn=service.getStreamMaxVolume(AudioManager.STREAM_RING);
                mTvMsg.setText("Volumn change:"+currentVolumn+"/"+maxVolumn);
            }
            else
            {
                mTvMsg.setText("Volumn change:?/?");
            }
            //mTvMsg.setText();
        }
    }

    private class CustomReceive extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            mTextView2.setText("receive:"+intent.getIntExtra("test", -1));
        }
    }
}