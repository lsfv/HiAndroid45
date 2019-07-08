package com.linson.android.hiandroid2.DesignPattern;


import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

//没太看懂，恩，直接写例子吧。根据这个模式的说明，大量增加状态时，会需要状态模式，自己看看是否会迭代出状态模式
//v1：建一个机器人，有3个状态，开心，平静，愤怒。 有2个行为，吃饭和睡觉。
//v2:试图增加状态,并符合开闭状态。
//看网上很多例子。各不一样。但是根本意思是一样。
//如这里，机器人有不同的情绪，那么吃饭的结果完全不同。如果情绪更丰厚。那么吃饭的结果就太多了。
//干脆把吃饭作为一个类，那么有高兴吃饭类，平静吃饭类。生气吃饭类。等等，想加什么加什么。当然必须把机器人传递给吃饭类，因为毕竟吃饭类是要知道谁吃饭吧。
//个人总结，除非非常符合情景，否则使用的也不多。因为会宁愿修改代码也不会去实现状态模式。
// 有一点像命令模式，虽然目的不同，一个为了存储，一个为了变化。但是根本手段是一致。把方法提出来放到类中。总的指导思想是一致的。
public class MyState
{
    public void Run()
    {
        Robot robot=new Robot("v1");
        robot.changeState(0);
        robot.eat();
        robot.sleep();
        robot.changeState(2);
        robot.eat();
    }

    public void Runv2()
    {
        Robotv2 robot2=new Robotv2("v2");
        happy happystats=new happy();
        Calm calmstats=new Calm();

        robot2.eat();
        robot2.sleep();

        calmstats.eat(robot2);
    }

    //region v1 ,存在增加状态时，必须需改函数内部，违反开闭原则。
    public class Robot
    {
        private int MState=0;//0:calm    1:happy   2:angry.
        private String mName;

        public Robot(String name)
        {
            mName=name;
        }

        public void changeState(int ss)
        {
            MState=ss;
        }

        public void eat()
        {
            if(MState==0)
            {
                LSComponentsHelper.LS_Log.Log_INFO(mName+"eat half");
            }
            else if(MState==1)
            {
                LSComponentsHelper.LS_Log.Log_INFO(mName+"eat all");
            }
            else if(MState==2)
            {
                LSComponentsHelper.LS_Log.Log_INFO(mName+"eat nothing");
            }
//            else if(MState==3)
//            {
//                LSComponentsHelper.LS_Log.Log_INFO(mName+"eat nothing");
//            }
        }

        public void sleep()
        {
            if(MState==0)
            {
                LSComponentsHelper.LS_Log.Log_INFO(mName+"sleep slowly");
            }
            else if(MState==1)
            {
                LSComponentsHelper.LS_Log.Log_INFO(mName+"sleep fast");
            }
            else if(MState==2)
            {
                LSComponentsHelper.LS_Log.Log_INFO(mName+"sleep ,hardly");
            }
            //            else if(MState==3)
//            {
//                LSComponentsHelper.LS_Log.Log_INFO(mName+"eat nothing");
//            }
        }
    }
    //endregion

    //region  常见的对修改关闭的手段，就是把要修改的部分放到一个类中，并让他们继承某个接口。那么原来需要修改的地方。只要使用接口就可以。变化可以由增加新类来实现。
    //把增加的内部判断，改为了到外部增加新类
    public class Robotv2
    {
        private int MState=0;//0:calm    1:happy   2:angry.
        private String mName;

        public Robotv2(String name)
        {
            mName=name;
        }

        public void changeState(int ss)
        {
            MState=ss;
        }



        public void eat()
        {
            MState=0;
            LSComponentsHelper.LS_Log.Log_INFO(mName+"start eat");
            MState=1;//初始状态，吃完东西会变开心。
        }


        public void sleep()
        {
            MState=0;
            LSComponentsHelper.LS_Log.Log_INFO(mName+"start sleep");
            MState=2;//初始状态，睡觉，会变不开心.
        }
    }

    public interface IAction
    {
        public void eat(Robotv2 robotv2);
        public void sleep(Robotv2 robotv2);
    }

    public class happy implements IAction
    {
        @Override
        public void eat(Robotv2 robotv2)
        {
            LSComponentsHelper.LS_Log.Log_INFO(robotv2.mName+"eat all");
            robotv2.changeState(2);
        }

        @Override
        public void sleep(Robotv2 robotv2)
        {
            LSComponentsHelper.LS_Log.Log_INFO(robotv2.mName+"sleep fast");
        }
    }

    public class Calm implements IAction
    {
        @Override
        public void eat(Robotv2 robotv2)
        {
            LSComponentsHelper.LS_Log.Log_INFO(robotv2.mName+"eat half");
            robotv2.changeState(1);
        }

        @Override
        public void sleep(Robotv2 robotv2)
        {
            LSComponentsHelper.LS_Log.Log_INFO(robotv2.mName+"sleep slowly");
        }
    }



    //endregion
}