package com.linson.android.hiandroid2.DesignPattern;

public class Signal
{
    public int version;
    public String SysName;
    public static Signal mSignal;

    private Signal()
    {

    }

    public static Signal getInstance()
    {
        if(mSignal==null)
        {
            mSignal=new Signal();
        }
        return mSignal;
    }
}