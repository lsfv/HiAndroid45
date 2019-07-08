package com.linson.android.hiandroid2.DesignPattern;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//感觉叫订阅者更恰当，计算机太多翻译，太不精确。或者可能是语言文化差异。自己看。
//老板我订购2个上好的灵芝，到了通知我。很正确的沟通。
//老板我观察2个上好的灵芝，到了通知我。有病吧！！！！
//订阅者模式是回调的多人版。而回调是事件的核心手段，而事件是现代编程的核心。所以回调和订阅者非常常见。
public class MyObserver
{
    public void Run()
    {
        MedicaShop medicaShop=new MedicaShop();
        FatMan guest=new FatMan();
        medicaShop.addObserver(guest);
        Beauty zhouhuiming=new Beauty();
        medicaShop.addObserver(zhouhuiming);

        medicaShop.getItem("juhua");
        medicaShop.getItem("3");
        medicaShop.getItem("good linzhi");
        medicaShop.getItem("normal linzhi");
    }

    public class MedicaShop
    {
        private List<IObserver> mObservers=new LinkedList<>();
        public void addObserver(IObserver guest)
        {
            mObservers.add(guest);
        }
        public void cancelObserver(IObserver guest)
        {
            mObservers.remove(guest);
        }

        public void getItem(String item)
        {
            if(item=="good linzhi")
            {
                for(IObserver eachitem :mObservers)
                {
                    eachitem.CallMe("good item");
                }
            }
        }
    }

    public interface IObserver
    {
        public void CallMe(String msg);
    }

    public class FatMan implements IObserver
    {
        @Override
        public void CallMe(String msg)
        {
            LSComponentsHelper.LS_Log.Log_INFO("maid,please goto shop for my baobei.");
        }
    }

    public class Beauty implements IObserver
    {
        @Override
        public void CallMe(String msg)
        {
            LSComponentsHelper.LS_Log.Log_INFO("honi,please goto shop for my goodthing:."+msg);
        }
    }

}