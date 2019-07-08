package com.linson.android.hiandroid2.DesignPattern;

import java.util.ArrayList;
import java.util.List;

public class Prototype implements Cloneable
{
    public String mname;
    public Integer mid;
    public List<Integer> mYears=new ArrayList<>();

//    @Override
//    protected Object clone() throws CloneNotSupportedException
//    {
//        return super.clone();
//    }


    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        Prototype prototype=new Prototype();
        prototype.mid=mid;
        prototype.mname=mname;
        prototype.mYears=new ArrayList<>(mYears);

        return prototype;
    }
}