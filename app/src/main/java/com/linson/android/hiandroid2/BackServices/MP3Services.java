package com.linson.android.hiandroid2.BackServices;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.io.File;
import java.io.IOException;

public class MP3Services extends Service
{
    public MP3Binder mMp3Binder=new MP3Binder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        LSComponentsHelper.LS_Log.Log_INFO("onStartCommand. service"+this.toString());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate()
    {
        LSComponentsHelper.LS_Log.Log_INFO("onCreate. service"+this.toString());
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        LSComponentsHelper.LS_Log.Log_INFO("onDestroy. service"+this.toString());
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        LSComponentsHelper.LS_Log.Log_INFO("onBind. binder"+mMp3Binder.toString());
        return mMp3Binder;
    }

    //inner class
    //it's only public interface for this server. so dont public any other class.
    public static class MP3Binder extends android.os.Binder
    {
        public Mp3Player mMp3player;
        private String mmp3name="abc.mp3";
        public MP3Binder()
        {
            mMp3player=new Mp3Player();

        }

        public void Start()
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
        }

        public void pause()
        {
            if(mMp3player.mState==1)
            {
                mMp3player.pause();
            }
            else
            {
                mMp3player.replay();
            }
        }

        public void stop()
        {
            mMp3player.stop();
        }

        public int getState()
        {
            return mMp3player.getmState();
        }

    }

    private static class Mp3Player
    {
        private MediaPlayer mMediaPlayer;
        private int mState=0;//0 stop. 1playing 2.pause
        private String mMp3Name="";
        private int lastPlay=0;

        public Mp3Player()
        {
            mMediaPlayer=new MediaPlayer();
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
        }
        public void  replay()
        {
            mMediaPlayer.start();
            mState=1;
        }
        public void stop()
        {
            mMediaPlayer.reset();
            mState=0;
        }

        public int getmState()
        {
            return mState;
        }

    }

}