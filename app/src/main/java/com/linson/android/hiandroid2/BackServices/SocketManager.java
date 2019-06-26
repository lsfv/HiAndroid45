package com.linson.android.hiandroid2.BackServices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SocketManager extends Service
{
    private ServerSocket serverSocket;
    private boolean running=true;
    private CopyOnWriteArrayList<Socket> mSockets=new CopyOnWriteArrayList<>();
    @Override
    public void onCreate()
    {
        new Thread(
                new Runnable()
                {
                    @Override
                    public void run()
                    {

                        LSComponentsHelper.LS_Log.Log_INFO("start service...");

                        try
                        {
                            serverSocket = new ServerSocket(8080);
                        } catch (Exception e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }

                        Thread progressThread=new Thread(new dotask());
                        progressThread.start();

                        while (running)
                        {
                            try
                            {
                                LSComponentsHelper.LS_Log.Log_INFO("accept.wait...");
                                Socket tempClient = serverSocket.accept();
                                mSockets.add(tempClient);

                            } catch (Exception e)
                            {
                                LSComponentsHelper.LS_Log.Log_Exception(e);
                            }
                        }
                        try
                        {
                            serverSocket.close();
                        } catch (Exception e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    }
                }


        ).start();


    }

    private class dotask implements Runnable
    {
        @Override
        public void run()
        {
            while (running)
            {
                for (Socket item : mSockets)
                {
                    try
                    {
                        InputStream inputStream = item.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        int info = inputStreamReader.read();
                        if (info > 0)
                        {
                            LSComponentsHelper.LS_Log.Log_INFO(info+"");
                        }
                    } catch (Exception e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                        mSockets.remove(item);
                        try
                        {
                            item.close();
                        } catch (Exception e2)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e2);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        running=intent.getBooleanExtra("running", true);
        return super.onStartCommand(intent, flags, startId);
    }

    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}