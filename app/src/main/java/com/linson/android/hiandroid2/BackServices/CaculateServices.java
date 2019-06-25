package com.linson.android.hiandroid2.BackServices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.DAL.AIDL.Book;
import com.linson.android.DAL.AIDL.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CaculateServices extends Service
{
    private CopyOnWriteArrayList<Book> mBooks=new CopyOnWriteArrayList<>();

    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return new BinderCaculate(this);
    }

    private void addBook(Book book)
    {
        try
        {
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
        mBooks.add(book);
    }

    private List<Book> getList()
    {
        return mBooks;
    }

    public static class BinderCaculate extends IBookManager.Stub
    {
        private CaculateServices mCaculateServices;
        public BinderCaculate(CaculateServices cc)
        {
            mCaculateServices=new CaculateServices();
        }

        @Override
        public void addBook(Book book) throws RemoteException
        {
            mCaculateServices.addBook(book);
        }

        @Override
        public List<Book> getList() throws RemoteException
        {
            return mCaculateServices.getList();
        }
    }
}