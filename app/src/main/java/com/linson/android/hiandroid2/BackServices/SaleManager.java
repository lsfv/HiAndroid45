package com.linson.android.hiandroid2.BackServices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.DAL.AIDL.AIDL_Sale;
import com.linson.android.DAL.AIDL.IOnCallBack;
import com.linson.android.DAL.AIDL.ISaleHandler;
import com.linson.android.Model.Sale;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SaleManager extends Service
{
    private CopyOnWriteArrayList<AIDL_Sale> mSales=new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnCallBack> mCallBacks=new RemoteCallbackList<>();

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
//            for(IOnCallBack item :mCallBacks)
//            {
//                item.OnAddSale(sale.mId, sale.mName);
//            }
            final int callbackSum=mCallBacks.beginBroadcast();
            for(int i=0;i<callbackSum;i++)
            {
                IOnCallBack item= mCallBacks.getBroadcastItem(i);
                if(item!=null)
                {
                    item.OnAddSale(sale.mId, sale.mName);
                }
            }

            mCallBacks.finishBroadcast();
        }

        @Override
        public List<AIDL_Sale> getList() throws RemoteException
        {
            return mSales;
        }

        @Override
        public void register(IOnCallBack hander) throws RemoteException
        {
            mCallBacks.register(hander);
        }

        @Override
        public void unRegister(IOnCallBack hander) throws RemoteException
        {
           boolean res= mCallBacks.unregister(hander);
            LSComponentsHelper.LS_Log.Log_INFO("server, delete:"+res);
        }
    }

}