package com.linson.android.DAL.AIDL;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable
{
    public int mBookID;
    public String mBookName;

    public Book(int bookID,String bookName)
    {
        mBookID=bookID;
        mBookName=bookName;
    }

    //region Add Parcelable
    protected Book(Parcel in)
    {
        mBookID = in.readInt();
        mBookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>()
    {
        @Override
        public Book createFromParcel(Parcel in)
        {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size)
        {
            return new Book[size];
        }
    };
    //endregion

    //region implement Parcelabel
    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(mBookID);
        dest.writeString(mBookName);
    }
    //endregion
}