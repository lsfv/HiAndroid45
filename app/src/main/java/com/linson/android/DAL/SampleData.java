package com.linson.android.DAL;

import java.util.ArrayList;
import java.util.Random;

public abstract class SampleData
{
    public static String[] getSubjects()
    {
        return  new String[]{"语文", "数学", "英语", "化学", "生物", "物理", "体育"};
    }

    public static ArrayList<ShopInfo> getShopes()
    {
        ArrayList<ShopInfo> res=new ArrayList<>();
        ShopInfo s1=new ShopInfo("美丽城市", 3, "pic1", "美丽新天地");
        ShopInfo s2=new ShopInfo("亲亲世界", 1, "pic2", "最美丽的亲亲");
        ShopInfo s3=new ShopInfo("世纪之爱", 5, "pic3", "永恒才是经典");
        res.add(s1);
        res.add(s2);
        res.add(s3);

        return res;
    }

    private static String getRandomLength(String a)
    {
        String res=a;

        int dd=(int)(1+Math.random()*(10-1+1));

        for(int i=0;i<dd;i++)
        {
            res+=a;
        }

        return res;
    }

    public static ArrayList<ShopInfo> getShopes2()
    {
        ArrayList<ShopInfo> res=new ArrayList<>();
        ShopInfo s1=new ShopInfo(getRandomLength( "美丽城市2"), 3, "pic1", "美丽新天地2");
        ShopInfo s2=new ShopInfo(getRandomLength( "亲亲世界2"), 1, "pic2", "最美丽的亲亲2");
        ShopInfo s3=new ShopInfo(getRandomLength( "世纪之爱2"), 5, "pic3", "永恒才是经典2");
        ShopInfo s4=new ShopInfo(getRandomLength( "美丽城市3"), 3, "pic1", "美丽新天地3");
        ShopInfo s5=new ShopInfo(getRandomLength( "亲亲世界3"), 1, "pic2", "最美丽的亲亲3");
        ShopInfo s6=new ShopInfo(getRandomLength( "世纪之爱3"), 5, "pic3", "永恒才是经典3");
        ShopInfo s7=new ShopInfo(getRandomLength( "世纪之爱3"), 5, "pic3", "永恒才是经典3");
        ShopInfo s8=new ShopInfo(getRandomLength( "世纪之爱3"), 5, "pic3", "永恒才是经典3");
        res.add(s1);
        res.add(s2);
        res.add(s3);
        res.add(s4);
        res.add(s5);
        res.add(s6);
        res.add(s7);
        res.add(s8);

        return res;
    }

    public static class ShopInfo
    {
        public String mname;
        public Integer msocre;
        public String mpic;
        public String maddress;

        public ShopInfo(String name,Integer socre,String pic,String address)
        {
            mname=name;
            msocre=socre;
            mpic=pic;
            maddress=address;
        }
    }
}
