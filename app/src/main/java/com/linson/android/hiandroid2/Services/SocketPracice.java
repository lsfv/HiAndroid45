package com.linson.android.hiandroid2.Services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.BackServices.SocketManager;
import com.linson.android.hiandroid2.R;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


//用sesrvice 做网络服务平台。
//客户端开多线程模拟多用户。
public class SocketPracice extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnSend;
    private Button mBtnStop;




    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnStop = (Button) findViewById(R.id.btn_stop);

        //set event handler
        mBtnSend.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_send:
            {
                send();
                break;
            }
            case R.id.btn_stop:
            {
                stop();
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
        setContentView(R.layout.activity_socket_pracice);

        findControls();

        Intent intent=new Intent(this, SocketManager.class);
        intent.putExtra("running", true);
        startService(intent);
    }

    private void send()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Socket socket=new Socket();
                try
                {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    SocketAddress socketAddress=new InetSocketAddress(inetAddress,8080 );
                    socket.connect(socketAddress, 3000);
                    OutputStream outputStream= socket.getOutputStream();
                    outputStream.write(33);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }).start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Socket socket=new Socket();
                try
                {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    SocketAddress socketAddress=new InetSocketAddress(inetAddress,8080 );
                    socket.connect(socketAddress, 3000);
                    OutputStream outputStream= socket.getOutputStream();
                    outputStream.write(12);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }).start();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Socket socket=new Socket();
                try
                {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    SocketAddress socketAddress=new InetSocketAddress(inetAddress,8080 );
                    socket.connect(socketAddress, 3000);
                    OutputStream outputStream= socket.getOutputStream();
                    outputStream.write(57890);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }).start();


    }

    private void stop()
    {
        Intent intent=new Intent(this, SocketManager.class);
        intent.putExtra("running", false);
        startService(intent);
    }
}