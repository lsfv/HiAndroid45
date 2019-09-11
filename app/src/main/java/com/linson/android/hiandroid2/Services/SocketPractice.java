package com.linson.android.hiandroid2.Services;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import app.lslibrary.androidHelper.LSLog;


//再练习一次而已。
//先复习下socket,  socket是一个五元组来标识的文件描述符：<源地址，源端口，目的地址，目的端口，使用的协议>
//服务端好像是：开新线程 建立端口，开始监听。循环阻塞式接收到的socket，放入一个线程安全的地方。
//这里就模拟短连接吧。客户端连接后，马上发送信息，并发送关闭信号。新线程循环处理每个连接的socket。并返回信息。并回复发送close信息。


//1.特别的，Java居然把socket分为了普通socket和服务端socket。而且普通socket不能监听。又为语言分歧做出了努力。棒！
//  这里监听，接受是一个线程 。之后再开一个线程处理客户数据。 客户发送也是一个线程，因为android规定不能再主线程进行任何网络动作。

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
    private ServerSocket mServerSocket;
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
        Thread thread_service=new Thread(new service());
        thread_service.start();

        Thread thread_msg=new Thread(new service_msg());
        thread_msg.start();
    }


    private void stopServices()
    {
        if(mServerSocket!=null && mServerSocket.isClosed()==false)
        {
            try
            {
                mServerSocket.close();
            } catch (final Exception e)
            {
                LSLog.Log_Exception(e);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mTvMsg.setText(mTvMsg.getText()+"\r\n"+e.toString());
                    }
                });
            }
        }
    }

    private void clientSendMsg()
    {
        Thread thread=new Thread(new clientsend());
        thread.start();
    }


    private class  clientsend implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                Socket client_temp=new Socket();

                InetAddress inetAddress = InetAddress.getLocalHost();
                SocketAddress serverAddress=new InetSocketAddress(inetAddress,8090);

                client_temp.connect(serverAddress, 5000);
                String msg="hi,i am:"+client_temp.getLocalPort();
                OutputStream wstream= client_temp.getOutputStream();
                wstream.write(msg.getBytes());
                wstream.flush();//这里别关闭流，否则下句就出错了。因为shutdownOutput其实是发送一个空字符过去。关闭了就无法发送了。
                client_temp.shutdownOutput();

            } catch (final Exception e)
            {
                LSLog.Log_Exception(e);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mTvMsg.setText(mTvMsg.getText()+"\r\n"+e.toString());
                    }
                });
            }
        }
    }


    //we just use thread simple.
    private class service implements Runnable
    {
        @Override
        public void run()
        {
            InetAddress localAddress=InetAddress.getLoopbackAddress();
            try
            {
                mServerSocket = new ServerSocket(8090);

                while (mServerSocket.isClosed()==false)
                {
                    Socket client= mServerSocket.accept();//block
                    LSLog.Log_INFO("server :accept "+client.getPort());
                    mConnectedSocket.add(client);
                }
            } catch (final Exception e)
            {
                LSLog.Log_Exception(e);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mTvMsg.setText(mTvMsg.getText()+"\r\n"+e.toString());
                    }
                });
            }
        }
    }


    private class service_msg implements Runnable
    {
        @Override
        public void run()
        {
            while (mConnectedSocket.size()>0 || mServerSocket.isClosed()==false)
            {
                    try
                    {
                        if (mConnectedSocket.size() > 0)
                        {
                            final Socket socket = mConnectedSocket.take();
                            final InputStream stream= socket.getInputStream();

                            InputStreamReader inputStreamReader=new InputStreamReader(stream);
                            char[] tt=new char[100];
                            inputStreamReader.read(tt);
                            final String msg=new String(tt);
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    mTvMsg.setText(mTvMsg.getText() + "\r\n" + "server:client msg "+msg);
                                    mTvMsg.setText(mTvMsg.getText() + "\r\n" + "server:client outputdown:"+socket.isOutputShutdown()+"."+".inputdown:"+socket.isInputShutdown());
                                }
                            });
                            socket.close();//模拟短连接。连接，接收，关闭。

                        } else
                        {
                            Thread.sleep(300);
                        }
                    }
                    catch (final Exception e)
                    {
                        LSLog.Log_Exception(e);
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                mTvMsg.setText(mTvMsg.getText() + "\r\n" + e.toString());
                            }
                        });
                    }
            }
        }
    }
}