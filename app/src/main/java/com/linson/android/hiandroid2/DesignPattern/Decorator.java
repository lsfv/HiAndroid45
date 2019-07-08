package com.linson.android.hiandroid2.DesignPattern;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.security.PublicKey;

public class Decorator
{
    //假如我想在showme这个效果上，加点其他效果。第一个想到的便是继承。
    //但是如果效果有上10种。而且排列不同，效果不同，那么继承就基本没办法了。
    //想象3种效果。 单是组合就有3×2种可能。那么就要6个派生类了。这还没有无限叠加效果。
    //当然可以用其他简单方法,直接在类里面加方法，这个类里面的addFire。等方法。简直就是简单到令人发指。
    //但是别忘记了设计模式的原则。 对修改关闭。在一个类中加方法来达到扩展这种做法。比格不高。必须把修改踢到类外部。
    //所以才有了装饰模式。
    //使用不是很常见，因为普通青年，并没有 严苛到 对于很少概率的修改，都把代码提升到设计模式的高度。


    //简单又高效的类，对于一个战士，想加什么特效自己动手，但是比格不高（没有对修改关闭）
    public class Soldier
    {
        public String showMe()
        {
            return "...";
        }
        public String addFire(String show)
        {
            return "$"+show+"$";
        }
        public String addWater(String show)
        {
            return "~"+show+"~";
        }
        public String addSword(String show)
        {
            return "-"+show+"-";
        }
    }



    public class HeightBSolider
    {
        public String showMe()
        {
            return "...";
        }
    }

    //装饰模式，本意是装饰，不改变对象
    //所以，1.必须是输入和输出都是原来的对象。
    //其次，2.一般是对某个方法的复写。
    //所以最直观的是继承。但是不灵活,2中变化，排列要4个类。
    //所以整来整去，装饰类必须继承被装饰类，才能同时满足1，2. 2个条件。
    //而且装饰类必须包含一个被装饰类，这样当装饰类复写方法的时候，不是调用基类的方法，而是调用成员变量的方法。
    //好处在哪里呢？原来固定的多重继承的顺序才能实现的效果，经过包含一个变量，并使用变量的方法来复写。由固定变成了灵活的组合。
    //最终1.装饰类必须继承被装饰类2.构造函数需要传递一个被装饰类，并设置为成员变量。3复写某个需要装饰的方法时，调用类型是被装饰类，而且是自己的成员变量的的方法，
    //来代替基类。
    public class AddFire extends HeightBSolider
    {
        @Override
        public String showMe()
        {
            return "$"+super.showMe()+"$";
        }
    }

    public class AddFirePlus extends HeightBSolider
    {
        @Override
        public String showMe()
        {
            return "$"+super.showMe()+"$";
        }
    }

    public abstract class ABSDecorator extends HeightBSolider
    {
        public HeightBSolider mHeightBSolider;
        public ABSDecorator(HeightBSolider heightBSolider)
        {
            mHeightBSolider=heightBSolider;
        }
        @Override
        public String showMe()
        {
            return mHeightBSolider.showMe();
        }
    }

    public class buff_fire extends ABSDecorator
    {
        public buff_fire(HeightBSolider heightBSolider)
        {
            super(heightBSolider);
        }

        public String showMe()
        {
            return "$"+mHeightBSolider.showMe()+"$";
        }
    }

    public class buff_Water extends ABSDecorator
    {
        public buff_Water(HeightBSolider heightBSolider)
        {
            super(heightBSolider);
        }

        public String showMe()
        {
            return "~"+mHeightBSolider.showMe()+"~";
        }
    }





    public void Run()
    {
        Soldier LeeDragen=new Soldier();
        String show=LeeDragen.showMe();
        show=LeeDragen.addFire(show);
        show=LeeDragen.addWater(show);
        LSComponentsHelper.LS_Log.Log_INFO(show);

        HeightBSolider Lee=new HeightBSolider();
        Lee=new buff_fire(Lee);
        Lee=new buff_Water(Lee);
        Lee=new buff_Water(Lee);
        Lee=new buff_Water(Lee);

        LSComponentsHelper.LS_Log.Log_INFO( Lee.showMe());



    }
}