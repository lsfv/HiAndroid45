package com.linson.android.hiandroid2.Services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import app.lslibrary.androidHelper.LSLog;


//再练习一次而已。
//先复习下socket,  socket是一个五元组来标识的文件描述符：<源地址，源端口，目的地址，目的端口，使用的协议>
//服务端好像是：建立端口，开始监听。接收到的socket，放入一个线程安全的地方。
//这里就模拟短连接吧。客户端连接后，马上发送信息，并发送关闭信号。新线程循环处理每个连接的socket。并返回信息。并回复发送close信息。
public class SocketPractice extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnServer2;
    private Button mBtnServer;
    private TextView mTvMsg;
    private Button mBtnClient;


    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnServer2 = (Button) findViewById(R.id.btn_server2);
        mBtnServer = (Button) findViewById(R.id.btn_server);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mBtnClient = (Button) findViewById(R.id.btn_client);

        //set event handler
        mBtnServer.setOnClickListener(this);
        mBtnServer2.setOnClickListener(this);
        mBtnClient.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_server:
            {
                startServices();
                break;
            }
            case R.id.btn_server2:
            {
                stopServices();
                break;
            }
            case R.id.btn_client:
            {
                clientSendMsg();
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //region other member variable
    private BlockingQueue<Socket> mConnectedSocket=new LinkedBlockingDeque<>();
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_practice);

        findControls();
    }

    private void startServices()
    {
        InetAddress localAddress=InetAddress.getLoopbackAddress();
        try
        {
            Socket server_test = new Socket(localAddress, 8090);
        } catch (Exception e)
        {
            LSLog.Log_Exception(e);
        }
    }

    private void stopServices()
    {

    }

    private void clientSendMsg()
    {

    }
}