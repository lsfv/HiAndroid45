package com.linson.android.DAL.AIDL;

import android.os.Parcel;
import android.os.Parcelable;

import com.linson.android.Model.Sale;

public class AIDL_Sale extends Sale implements Parcelable
{
    public AIDL_Sale(int id,String na)
    {
        super(id,na);
    }

    protected AIDL_Sale(Parcel in)
    {
        mId=in.readInt();
        mName=in.readString();
    }

    public static final Creator<AIDL_Sale> CREATOR = new Creator<AIDL_Sale>()
    {
        @Override
        public AIDL_Sale createFromParcel(Parcel in)
        {
            return new AIDL_Sale(in);
        }

        @Override
        public AIDL_Sale[] newArray(int size)
        {
            return new AIDL_Sale[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(mId);
        dest.writeString(mName);
    }
}