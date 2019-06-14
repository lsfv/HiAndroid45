package com.linson.android.hiandroid2.MultiMedia;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Index extends AppCompatActivity implements View.OnClickListener,LSComponentsHelper.VoidHandler,LSComponentsHelper.Mp3Handler
{
    private Button mBtnMusicstart4;
    private Button mBtnMusicpause4;
    private Button mBtnMusicstop4;
    private Button mBtnPhotopic2;
    private Button mBtnMusicstop;
    private Button mBtnMusicpause;
    private Button mBtnMusicstart;
    private Button mBtnPhotopic;
    private Button mBtnNotification;
    private TextView mTvNotification;
    private ImageView mIvHead;
    private VideoView mVideoView;




    private Uri mCashImageUri;
    private String mCachPhotoGraphName="photograph.jpg";
    private String mFileProviderAuthority="com.linson.android.hiandroid2.phtograph";
    private String mAction_openAlbum="android.intent.action.GET_CONTENT";
    private Mp3Player mMp3player;
    private String mmp3name="abc.mp3";
    private VidioPlayer mVidioP;
    private String mmovename="abc.mp4";

    //code
    private final int mActivity_photographCode=1;
    private final int mActivity_Request_openAlbum=2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index7);

        findControls();
        mMp3player=new Mp3Player(this);
        mVidioP=new VidioPlayer(this, mVideoView);
    }

    private void findControls()
    {
        mBtnMusicstart4 = (Button) findViewById(R.id.btn_musicstart4);
        mBtnMusicpause4 = (Button) findViewById(R.id.btn_musicpause4);
        mBtnMusicstop4 = (Button) findViewById(R.id.btn_musicstop4);
        mBtnPhotopic2 = (Button) findViewById(R.id.btn_photopic2);
        mBtnMusicstop = (Button) findViewById(R.id.btn_musicstop);
        mBtnMusicpause = (Button) findViewById(R.id.btn_musicpause);
        mBtnMusicstart = (Button) findViewById(R.id.btn_musicstart);
        mBtnPhotopic = (Button) findViewById(R.id.btn_photopic);
        mBtnNotification = (Button) findViewById(R.id.btn_notification);
        mTvNotification = (TextView) findViewById(R.id.tv_notification);
        mIvHead = (ImageView) findViewById(R.id.iv_head);
        mVideoView = (VideoView) findViewById(R.id.videoView);

        mBtnMusicstop.setOnClickListener(this);
        mBtnMusicpause.setOnClickListener(this);
        mBtnMusicstart.setOnClickListener(this);
        mBtnPhotopic.setOnClickListener(this);
        mBtnNotification.setOnClickListener(this);
        mBtnPhotopic2.setOnClickListener(this);
        mBtnMusicstop4.setOnClickListener(this);
        mBtnMusicpause4.setOnClickListener(this);
        mBtnMusicstart4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_notification:
            {
                notification();
                break;
            }
            case R.id.btn_photopic:
            {
                photograph();
                break;
            }
            case R.id.btn_photopic2:
            {
                LSComponentsHelper.LS_Other.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, this);
                break;
            }
            case R.id.btn_musicstart:
            {
                try
                {
                    mMp3player.setFile(mmp3name);
                    if(mMp3player.mState!=0)
                    {
                        mMp3player.stop();
                    }
                    mMp3player.play();
                }
                catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                break;
            }
            case R.id.btn_musicpause:
            {
                if(mMp3player.mState==1)
                {
                    mMp3player.pause();
                }
                else
                {
                    mMp3player.replay();
                }
                break;
            }
            case R.id.btn_musicstop:
            {
                mMp3player.stop();
                break;
            }

            case R.id.btn_musicstart4:
            {
                mVidioP.setFile(mmovename);
                mVidioP.play();
                break;
            }
            case R.id.btn_musicpause4:
            {
                if(mVidioP.mState==1)
                {
                    mVidioP.pause();
                }
                else
                {
                    mVidioP.replay();
                }
                break;
            }
            case R.id.btn_musicstop4:
            {
                mVidioP.stop();
                break;
            }

        }
    }

    @Override
    public void OnchangeState(int state,int id)
    {
        if(id==33)
        {
            switch (state)
            {
                case 0://stop
                {
                    mBtnMusicstart.setEnabled(true);
                    mBtnMusicpause.setEnabled(false);
                    mBtnMusicstop.setEnabled(false);
                    mBtnMusicpause.setText("pause");
                    break;
                }
                case 1://playing
                {
                    mBtnMusicstart.setEnabled(true);
                    mBtnMusicpause.setEnabled(true);
                    mBtnMusicstop.setEnabled(true);
                    mBtnMusicpause.setText("pause");
                    break;
                }
                case 2://pause
                {
                    mBtnMusicstart.setEnabled(true);
                    mBtnMusicpause.setEnabled(true);
                    mBtnMusicstop.setEnabled(true);
                    mBtnMusicpause.setText("replay");
                    break;
                }
            }
        }
        else
        {
            switch (state)
            {
                case 0://stop
                {
                    mBtnMusicstart4.setEnabled(true);
                    mBtnMusicpause4.setEnabled(false);
                    mBtnMusicstop4.setEnabled(false);
                    mBtnMusicpause4.setText("pause");
                    break;
                }
                case 1://playing
                {
                    mBtnMusicstart4.setEnabled(true);
                    mBtnMusicpause4.setEnabled(true);
                    mBtnMusicstop4.setEnabled(true);
                    mBtnMusicpause4.setText("pause");
                    break;
                }
                case 2://pause
                {
                    mBtnMusicstart4.setEnabled(true);
                    mBtnMusicpause4.setEnabled(true);
                    mBtnMusicstop4.setEnabled(true);
                    mBtnMusicpause4.setText("replay");
                    break;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    doti();
                }
                else
                {
                    LSComponentsHelper.Log_INFO("refuse");
                }
                break;
            }
        }
    }

    private void photograph()
    {
        ///storage/emulated/0/Android/data/com.linson.android.hiandroid2/cache
        //调用系统提供的可视化的照相界面。并采用 forcallback方法。2.覆写处理 返回数据方法。
        //采用匹配调用方式，提供action+data(uri) .[action+category]
        String phtographAction="android.media.action.IMAGE_CAPTURE";
        Intent phtographIntent=new Intent(phtographAction);
        startActivityForResult(phtographIntent, mActivity_photographCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case mActivity_photographCode:
            {
                try
                {
                    Bitmap photo=(Bitmap) data.getExtras().get("data");//null exception.
                    mIvHead.setImageBitmap(photo);
                }
                catch (Exception e)
                {
                    LSComponentsHelper.Log_Exception(e);
                }

                break;
            }
            case mActivity_Request_openAlbum:
            {
                mIvHead.setImageBitmap(LSComponentsHelper.MultiMedia.getBitmap(this, data));
                break;
            }
        }
    }

    private void notification()
    {
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent=new Intent(this, com.linson.android.hiandroid2.UI.Index.class);
        PendingIntent pi=PendingIntent.getActivity(this,1,intent,0);

        Notification notification=new Notification.Builder(this)
                .setContentTitle("mynotifi")
                .setContentText("hi")
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.wxfix))
                .setSmallIcon(-1)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build();


        notificationManager.notify(1, notification);
    }

    @Override
    public void doti()
    {
        Intent intent=new Intent(mAction_openAlbum);
        intent.setType("image/*");
        startActivityForResult(intent, mActivity_Request_openAlbum);
    }

    //inner class
    public static class Mp3Player
    {
        private MediaPlayer mMediaPlayer;
        public int mState=0;//0 stop. 1playing 2.pause
        private String mMp3Name="";
        private int lastPlay=0;
        //private LSComponentsHelper.Mp3Handler mMp3Handler;

        public Mp3Player(LSComponentsHelper.Mp3Handler handler)
        {
            mMediaPlayer=new MediaPlayer();
            //mMp3Handler=handler;
        }
        public  void  setFile(String file)
        {
            mMp3Name=file;
        }

        public void play()
        {
            File music=new File(Environment.getExternalStorageDirectory(), mMp3Name);
            try
            {
                mMediaPlayer.setDataSource(music.getPath());
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                mState=1;

                //mMp3Handler.OnchangeState(mState,33);
            }
            catch (IOException e)
            {
                LSComponentsHelper.LS_Log.Log_Exception(e);
            }
        }

        public void pause()
        {
            mMediaPlayer.pause();
            mState=2;
            //mMp3Handler.OnchangeState(mState,33);
        }
        public void  replay()
        {
            mMediaPlayer.start();
            mState=1;
            //mMp3Handler.OnchangeState(mState,33);
        }
        public void stop()
        {
            mMediaPlayer.reset();
            mState=0;
            //mMp3Handler.OnchangeState(mState,33);
        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(mMp3player!=null)
        {
            mMp3player.stop();
        }
        if(mVideoView!=null)
        {
            mVideoView.suspend();
        }
    }

    //
    private static class VidioPlayer
    {
        private VideoView mMediaPlayer;
        private int mState=0;//0 stop. 1playing 2.pause
        private String mMp3Name="";
        private int lastPlay=0;
        private LSComponentsHelper.Mp3Handler mMp3Handler;

        public VidioPlayer(LSComponentsHelper.Mp3Handler handler,VideoView vidioPlayer)
        {
            mMediaPlayer=vidioPlayer;
            mMp3Handler=handler;
        }
        public  void  setFile(String file)
        {
            mMp3Name=file;
        }

        public void play()
        {
            File vedio=new File(Environment.getExternalStorageDirectory(), mMp3Name);
            try
            {
                mMediaPlayer.setVideoPath(vedio.getPath());
                mMediaPlayer.start();
                mState=1;
                mMp3Handler.OnchangeState(mState,mMediaPlayer.getId());
            }
            catch (Exception e)
            {
                LSComponentsHelper.LS_Log.Log_Exception(e);
            }
        }

        public void pause()
        {
            mMediaPlayer.pause();
            mState=2;
            mMp3Handler.OnchangeState(mState,mMediaPlayer.getId());
        }
        public void  replay()
        {
            mMediaPlayer.start();
            mState=1;
            mMp3Handler.OnchangeState(mState,mMediaPlayer.getId());
        }
        public void stop()
        {
            mMediaPlayer.pause();
            mState=0;
            mMp3Handler.OnchangeState(mState,mMediaPlayer.getId());
        }
    }

}