package com.linson.android.hiandroid2.BackServices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.linson.android.DAL.AIDL.AIDL_Sale;
import com.linson.android.DAL.AIDL.ISaleHandler;
import com.linson.android.Model.Sale;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SaleManager extends Service
{
    private CopyOnWriteArrayList<AIDL_Sale> mSales=new CopyOnWriteArrayList<>();

    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return new BinderSale();
    }

    //不需要为静态了。把binder 看作是implement.而且还是service的功能分担者就好了。接口ibinder 已经定义好了。
    //ipc还有自动文件。内部可以跳过具体意图，把ibinder 当作意图就好了。
    public class BinderSale extends ISaleHandler.Stub
    {
        @Override
        public void addSale(AIDL_Sale sale) throws RemoteException
        {
            mSales.add(sale);
        }

        @Override
        public List<AIDL_Sale> getList() throws RemoteException
        {
            return mSales;
        }
    }
}