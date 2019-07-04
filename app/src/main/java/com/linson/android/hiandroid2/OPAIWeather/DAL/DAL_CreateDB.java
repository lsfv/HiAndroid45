package com.linson.android.hiandroid2.OPAIWeather.DAL;

import android.content.Context;
import android.os.Message;

import org.jetbrains.annotations.NotNull;

public class DAL_CreateDB
{
    private Context mContext;

    public DAL_CreateDB(@NotNull Context context)
    {
        mContext=context;
    }

    //返回是否已经存在或需要创建.
    public boolean isExistOtherwiseCreate(IOncreateDBHandle ionCreateDB)
    {
        DataBaseAccess db=new DataBaseAccess(mContext, DataBaseAccess.db_weatherName,null,DataBaseAccess.db_weatherversion);
        DataBaseAccess.IonCreateDB realImplemation=new OncreateDBHandler(ionCreateDB);
        db.setIonCreateDB(realImplemation);
        db.getReadableDatabase();//mExist默认是true.只有oncreate才会设置为false。所以必须执行getReadableDatabase。
        if(db.mIsInvokeCreate)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public interface IOncreateDBHandle
    {
        public void OngetProcess(Message progress);
    }

    //典型的代理模式:因为封闭性，分层的需要。
    private class OncreateDBHandler implements DataBaseAccess.IonCreateDB
    {
        private IOncreateDBHandle mIOncreateDBHandle;

        public OncreateDBHandler(IOncreateDBHandle oncreateDBHandle)
        {
            mIOncreateDBHandle=oncreateDBHandle;
        }

        @Override
        public void OnCreateDB(Message progress)
        {
            mIOncreateDBHandle.OngetProcess(progress);
        }
    }

    public void deletedb()
    {
        mContext.deleteDatabase(DataBaseAccess.db_weatherName);
    }
}