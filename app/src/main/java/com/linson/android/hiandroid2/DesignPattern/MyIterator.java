package com.linson.android.hiandroid2.DesignPattern;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;


//需求：给一个数字，实现从这这个数字开始，到0结束。
//实现递减1的遍历。  再实现递减2的遍历。 再实现其他递减规则的遍历.再。。。。。。
//可以发现让容器实现迭代的接口，那么就需要很多迭代器。而用组合，包含一个实现了迭代器的对象。那么就把变化赶到了类的外边。
public class MyIterator
{
    public void Run()
    {
        TheSmartContainer smartContainer=new TheSmartContainer();
        while (smartContainer.mTheIterator.hasNext())
        {
            LSComponentsHelper.LS_Log.Log_INFO(smartContainer.mTheIterator.next()+"");
        }
    }

    //region 简单实现迭代器的接口，发现每一个需求，都必须继承一次。继承的痛点，完全不符合开闭原则.
    public interface TheIterator
    {
        public boolean hasNext();
        public Object next();
    }

    public class TheContainer implements TheIterator
    {
        private int theNum=0;

        public TheContainer(int a)
        {
            theNum=a;
        }


        @Override
        public boolean hasNext()
        {
            return theNum>=0;
        }

        @Override
        public Object next()
        {
            return theNum--;
        }
    }
    //endregion


    //region 不再直接实现迭代器接口，而让强制让迭代器容器包含一个迭代器。那么也就等于容器有了迭代功能，而且把迭代器的具体实现放入到外部。吻合开闭原则
    public interface ISmartContainer
    {
        public TheIterator getIterator();
    }

    public class TheSmartContainer implements ISmartContainer
    {
        private int mynum=10;
        public TheIterator mTheIterator;
        public TheSmartContainer()
        {
            mTheIterator=getIterator();
        }
        @Override
        public TheIterator getIterator()
        {
            //return new myiterator1();
            return new myiterator2(); //随时切换迭代逻辑。完美实现开闭原则
        }

        public class myiterator1 implements TheIterator
        {

            @Override
            public boolean hasNext()
            {
                return mynum>=0;
            }

            @Override
            public Object next()
            {
                return mynum--;
            }
        }

        public class myiterator2 implements TheIterator
        {

            @Override
            public boolean hasNext()
            {
                return mynum>0;
            }

            @Override
            public Object next()
            {
                mynum-=2;
                return mynum;
            }
        }
    }
    //endregion
}