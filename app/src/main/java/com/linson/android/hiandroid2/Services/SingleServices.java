package com.linson.android.hiandroid2.Services;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.DAL.AIDL.Book;
import com.linson.android.DAL.AIDL.IBookManager;
import com.linson.android.hiandroid2.BackServices.CaculateServices;
import com.linson.android.hiandroid2.R;

import java.util.List;

public class SingleServices extends AppCompatActivity implements View.OnClickListener
{
    private EditText mEditText3;
    private Button mButton10;
    private EditText mEditText;

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mEditText3 = (EditText) findViewById(R.id.editText3);
        mButton10 = (Button) findViewById(R.id.button10);
        mEditText = (EditText) findViewById(R.id.editText);

        //set event handler
        mButton10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button10:
            {
                if(mConnectionCaculate!=null && mConnectionCaculate.mBinderCaculate!=null)
                {
                    Book book=new Book(1, "java");
                    try
                    {
                        mConnectionCaculate.mBinderCaculate.addBook(book);
                    } catch (Exception e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                    try
                    {
                        List<Book> res = mConnectionCaculate.mBinderCaculate.getList();
                        LSComponentsHelper.LS_Log.Log_INFO("size"+res.size()+".name"+((Book)res.get(0)).mBookName);
                    } catch (Exception e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                }
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion
    //region functions of click event
    //endregion

    //member variable



    private ConnectionCaculate mConnectionCaculate;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_services);

        findControls();
        bindServices();
    }

    private void bindServices()
    {
        LSComponentsHelper.LS_Log.Log_INFO("bind services");
        Intent intent=new Intent(this, CaculateServices.class);
        startService(intent);
        mConnectionCaculate=new ConnectionCaculate();
        bindService(intent, mConnectionCaculate, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(mConnectionCaculate);
    }

    //region connection
    private class ConnectionCaculate implements ServiceConnection
    {
        public IBookManager mBinderCaculate;
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            //通过Stub的asInterface得到其代理类Stub.Proxy
            mBinderCaculate=IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    }
    //endregion
}