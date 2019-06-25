package com.linson.android.Model;

import android.support.annotation.NonNull;

public class Sale
{
    public Integer mId;
    public String mName;


    public Sale(@NonNull Integer id,@NonNull String name)
    {
        mName=name;
        mId=id;
    }

    public Sale()
    {
        mId=0;
        mName="";
    }
}