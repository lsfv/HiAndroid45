package com.linson.android.hiandroid2.DesignPattern;


import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandV2
{
    //实现记录命令功能，这里就是 某个常客说，来早餐，和昨天一样。 按道理很简单。存储一下啊。仔细看看BaoziCommand()。这个是方法没法储存。好吧开始迭代升级。
    //很明显，发现我们使用最简迭代，达到了我们的目标，但是却不是标准的命令模式.先看看我们是否符合开闭原则。如果我们符合，那么肯定是写模式设计这本书的作者过度设计。
    //对于点餐来说，我们完全符合开闭原则。yesterdayList.add(EggCode);可以随便组合。非常灵活.那么可以肯定，一定是命令模式过度设计了。
    //ok,那我们也过度设计一下。假设菜品变动比较大。（基本不可能，所以命令模式在这里,确实是设计过度）
    //假设菜品变动比较大。yesterDayAgain，这个方法违背了开闭原则。

    private final Integer baoziCode=1;
    private final Integer EggCode=2;
    private final Integer ChangFenCode=3;
    public void Run()
    {
        String BreakFast="";
        CommandInvoker waiter=new CommandInvoker();
        BreakFast+= waiter.BaoziCommand();
        BreakFast+=waiter.EggCommand();
        BreakFast+=waiter.ChangFenCommand();
        LSComponentsHelper.LS_Log.Log_INFO(BreakFast);

        //把小胖崽的早餐记录下。
        List<Integer> yesterdayList=new ArrayList<>();
        yesterdayList.add(baoziCode);
        yesterdayList.add(EggCode);
        yesterdayList.add(ChangFenCode);
        waiter.mOrderHistory.put("fatBoy",yesterdayList );

        //第二天直接告诉老板：老板，早餐，照旧。
        LSComponentsHelper.LS_Log.Log_INFO(waiter.yesterDayAgain("fatBoy"));
    }

    //命令调用者（服务员）：接受客户调用，并且最终去调用厨师的方法。
    public class CommandInvoker
    {
        private ICommandReceiver mBaozi=new BaoziTony();
        private ICommandReceiver mEgg=new EggXiaoZhang();
        private ICommandReceiver mChangfen=new ChangFenLaoWang();
        public Map<String,List<Integer>> mOrderHistory=new HashMap<String,List<Integer>>();
        public String BaoziCommand()
        {
            return mBaozi.ExecuteCommand();
        }

        public String EggCommand()
        {
            return mEgg.ExecuteCommand();
        }

        public String ChangFenCommand()
        {
            return mChangfen.ExecuteCommand();
        }
        public String yesterDayAgain(String guest)
        {
            String res="";
            List<Integer> commands=mOrderHistory.get(guest);
            for(Integer command:commands)
            {
                if(command==baoziCode)
                {
                    res+=(BaoziCommand());
                }
                else if(command==EggCode)
                {
                    res+=(EggCommand());
                }
                else if(command==ChangFenCode)
                {
                    res+=(ChangFenCommand());
                }
            }
            return res;
        }
    }


    //处理命令接口（抽象厨师）
    public interface ICommandReceiver
    {
        public String ExecuteCommand();
    }

    public class BaoziTony implements ICommandReceiver
    {
        @Override
        public String ExecuteCommand()
        {
            return "BaoZiv2";
        }
    }
    public class EggXiaoZhang implements ICommandReceiver
    {
        @Override
        public String ExecuteCommand()
        {
            return "Eggv2";
        }
    }
    public class ChangFenLaoWang implements ICommandReceiver
    {
        @Override
        public String ExecuteCommand()
        {
            return "ChangFenv2";
        }
    }
}