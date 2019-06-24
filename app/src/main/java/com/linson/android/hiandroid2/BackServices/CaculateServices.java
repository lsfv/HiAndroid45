package com.linson.android.hiandroid2.BackServices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;

public class CaculateServices extends Service
{
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return new BinderCaculate(this);
    }

    public int add(int a,int b)
    {
        return a+b;
    }


    public static class BinderCaculate extends Binder
    {
        public CaculateServices mCaculateServices;

        public BinderCaculate(@NonNull CaculateServices cs)
        {
            mCaculateServices=cs;
        }

        public int add(int a,int b)
        {
            return mCaculateServices.add(a, b);
        }
    }
}