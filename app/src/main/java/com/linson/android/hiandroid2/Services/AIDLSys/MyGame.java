package com.linson.android.hiandroid2.Services.AIDLSys;

import android.os.Parcel;
import android.os.Parcelable;

public class MyGame implements Parcelable
{
    public int id;
    public String name;

    public MyGame(){}

    public MyGame(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static final Creator<MyGame> CREATOR = new Creator<MyGame>()
    {
        @Override
        public MyGame createFromParcel(Parcel in)
        {
            return new MyGame(in);
        }

        @Override
        public MyGame[] newArray(int size)
        {
            return new MyGame[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }


}
