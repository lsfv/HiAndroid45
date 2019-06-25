package com.linson.android.hiandroid2.Services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.DAL.AIDL.AIDL_Sale;
import com.linson.android.DAL.AIDL.ISaleHandler;
import com.linson.android.Model.Sale;
import com.linson.android.hiandroid2.BackServices.SaleManager;
import com.linson.android.hiandroid2.R;

import java.util.List;

public class SaleQuery extends AppCompatActivity implements View.OnClickListener
{
    private EditText mEtName;
    private Button mBtnAdd;
    private Button mBtnSearch;
    private TextView mTvMessage;

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mEtName = (EditText) findViewById(R.id.et_name);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnSearch = (Button) findViewById(R.id.btn_search);
        mTvMessage = (TextView) findViewById(R.id.tv_message);

        //set event handler
        mBtnAdd.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_add:
            {
                add();
                break;
            }
            case R.id.btn_search:
            {
                search();
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
    private Connection_Sale mConnection_sale;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_query);
        findControls();
        bindServices();
    }

    private void bindServices()
    {
        Intent intent=new Intent(this, SaleManager.class);
        startService(intent);
        mConnection_sale=new Connection_Sale();
        bindService(intent, mConnection_sale, BIND_AUTO_CREATE);
    }

    private void add()
    {
        String name=mEtName.getText().toString();
        if(name!=null&& name.length()!=0)
        {
            AIDL_Sale aidl_sale=new AIDL_Sale((int)System.currentTimeMillis(),name);
            if(mConnection_sale!=null && mConnection_sale.mHandler!=null)
            {
                try
                {
                    mConnection_sale.mHandler.addSale(aidl_sale);
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }
    }
    private void search()
    {
        String name=mEtName.getText().toString();
        if(name!=null&& name.length()!=0)
        {
            AIDL_Sale aidl_sale=new AIDL_Sale((int)System.currentTimeMillis(),name);
            if(mConnection_sale!=null && mConnection_sale.mHandler!=null)
            {
                try
                {
                  List<AIDL_Sale> res= mConnection_sale.mHandler.getList();
                  boolean con=false;
                  for(int i=0;i<res.size();i++)
                  {
                      LSComponentsHelper.LS_Log.Log_INFO(res.get(i).mName+" ... "+name);
                      if(res.get(i).mName==name)
                      {
                          con=true;
                          break;
                      }
                  }
                  mTvMessage.setText("contain:"+con+".size"+res.size());
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }
    }

    //region ServicesConnection
    public static class Connection_Sale implements ServiceConnection
    {
        public ISaleHandler mHandler;

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mHandler=ISaleHandler.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    }
    //endregion

}