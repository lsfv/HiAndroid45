package com.linson.android.hiandroid2.DesignPattern;

//基本很少用工厂，有什么是new不能解决的？
//1.参数太复杂，不想直接用new。
// 什么！！！难道不会提供一个简单的构造函数，使用默认值去调用复杂的构造函数吗？
//2.产品太多。到达上百，几千，几万个。new 一个东西。想不起来叫什么名字。
//这个倒是有需要。用工厂的静态方法就可以。没有必要到工厂方法。
//3.创建太复杂，需要先找很多信息。才能new.
//基本不可信，还是一样.用一个方法包一下就可以，就可以为什么要工厂？

//个人总结，基本碰不到必要用工厂模式，很多更简单的方法可以代替, 最多用工厂静态方法，来应对成百上千种对象，而记不住名字。
//如果使用工厂方法，倒是对修改关闭了。关闭了属性的扩展，但是这样一来是需要记住很多工厂的名字啊。

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.security.MessageDigest;

//但是例子还是要做。以创建书为例子，
//书的种类太多，假设有1000种。科幻,经济，历史，，等等等等等等等等，
//每种类别有自己特有的信息，字段。但都要提供描述方法。
public class Factory
{
    public static void Run()
    {
        //想要生成一本二战的历史书，但是不记得类名。
        //BaseBook secondWar=new ？？？？？
        //用静态方法，依靠编译器的提示功能来选择需要建立的类。
        BaseBook secondWar=FactoryBooks.createHistoryBook(1, "the secord world war", 2001);
        LSComponentsHelper.LS_Log.Log_INFO(secondWar.introduceMe());
        //或者彻底对修改关闭。使用工厂方法,加一本书，就加一个工厂。而不是到内部加一个方法。
        //没有太大必要。已经是方法分离了，
        // 对修改关闭原子。把修改从方法内部，变成了方法外部。而工厂方法是要把变化从方法外部，变成类的外部。
        //为什么不把修改放到文件外部呢？为什么不放到程序外部呢？为什么不放到地球外部呢？
        FactoryB factoryhisotry=new FactoryHistroy(2, "the secord world war", 2009);
        BaseBook secondWar2=factoryhisotry.create();
        LSComponentsHelper.LS_Log.Log_INFO(secondWar2.introduceMe());
    }

    public static abstract class BaseBook
    {
        public int mID;
        public String mName;
        public abstract String introduceMe();
    }

    public static class ScienceBook extends BaseBook
    {
        public String mCrazy="";

        public ScienceBook(String crazy,int id,String name)
        {
            mCrazy=crazy;
            mID=id;
            mName=name;
        }

        @Override
        public String introduceMe()
        {
            return "it is Science Book:"+mName+".id:"+mID+".crazy level:"+mCrazy;
        }
    }

    public static class HistoryBook extends BaseBook
    {
        public int mYear;

        public HistoryBook(int year,int id,String name)
        {
            mYear=year;
            mID=id;
            mName=name;
        }

        @Override
        public String introduceMe()
        {
            return "it is Science Book:"+mName+".id:"+mID+".year:"+mYear;
        }
    }

    //region factory function

    public static interface FactoryB
    {
        public BaseBook create();
    }

    public static class FactoryHistroy implements FactoryB
    {
        public int mID;
        public String mName;
        public int mYear;
        public FactoryHistroy(int id,String name,int year)
        {
            mID=id;
            mName=name;
            mYear=year;
        }

        @Override
        public BaseBook create()
        {
            return new HistoryBook(mYear, mID, mName);
        }
    }
//endregion


    //region static fun
    public static class FactoryBooks
    {
        public static HistoryBook createHistoryBook(int id,String name,int year)
        {
            HistoryBook historyBook=new HistoryBook(year, id, name);
            return historyBook;
        }
        //public static HistoryBook createxxxxBook(int id,String name,int year)
        //public static HistoryBook createxxxxBook(int id,String name,int year)
        //public static HistoryBook createxxxxBook(int id,String name,int year)
        //public static HistoryBook createxxxxBook(int id,String name,int year)

    }
    //endregion
}