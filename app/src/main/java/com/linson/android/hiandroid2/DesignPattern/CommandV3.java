package com.linson.android.hiandroid2.DesignPattern;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandV3
{
    //假设菜品变动比较大。yesterDayAgain，这个方法违背了开闭原则。
    //我们应该可以让baoziCode，每个菜名，都会关联一个厨师的方法。这样通过属性和方法的结合，也就是类，达到开闭原则。
    //小结一下，对于常见的方法调用XXX.fun1().XXX.fun2(). 无法保存和组合方法顺序。所以最简方案。我们会用数字或字符1，2，3来代替方法xxx.fun1,fun2,fun3
    //但是稍微面向对象一点，我们可以把方法放入到某个类中，那么意思就是把方法的执行者也包进来。就是这么简单。
    //再回顾定义：Encapsulate a request as an object，没毛病，把方法放到类中，所以一并把方法的执行者也放到类中。完毕。
    //至于标准的uml图。我觉得invoker在这里是多余，命令类已经包含执行者。不需要invoker.
    //waiter.CheckAndExecuteCommand(BaoziCode) 和BaoziCode.OneReceiverWillDoIt 那个更简洁？
    //个人直觉，这个模式不会太常用，完全可以用数字或字符来代替命令， 解耦和简洁之间并非每次都是解耦胜出。这个场合，我站简洁。

    public void Run()
    {
        String BreakFast="";
        CommandInvoker waiter=new CommandInvoker();

        AbsCommand BaoziCode=new BaoziCommand();
        BreakFast+= waiter.CheckAndExecuteCommand(BaoziCode);
        AbsCommand EggCode=new EggCommand();
        BreakFast+= waiter.CheckAndExecuteCommand(EggCode);
        AbsCommand ChangFenCode=new ChangFenCommand();
        BreakFast+= waiter.CheckAndExecuteCommand(ChangFenCode);
        AbsCommand jianJiaoCode=new JianJiaoCommand();
        BreakFast+= waiter.CheckAndExecuteCommand(jianJiaoCode);

        LSComponentsHelper.LS_Log.Log_INFO(BreakFast);

        //把小胖崽的早餐记录下。
        List<AbsCommand> yesterdayList=new ArrayList<>();
        yesterdayList.add(BaoziCode);
        yesterdayList.add(EggCode);
        yesterdayList.add(ChangFenCode);
        yesterdayList.add(jianJiaoCode);
        waiter.mOrderHistory.put("fatBoy",yesterdayList );

        //第二天直接告诉老板：老板，早餐，照旧。
        LSComponentsHelper.LS_Log.Log_INFO(waiter.yesterDayAgain("fatBoy"));
    }

    //region command
    //抽象命令（抽象菜名）
    public interface AbsCommand
    {
        public String OneReceiverWillDoIt();
    }
    //命令的实现者(菜名和执行厨师类)
    public class BaoziCommand implements AbsCommand
    {
        private ICommandReceiver mCommandReceiver=new BaoziTony();
        @Override
        public String OneReceiverWillDoIt()
        {
            return mCommandReceiver.ExecuteCommand();
        }
    }
    public class EggCommand implements AbsCommand
    {
        private ICommandReceiver mCommandReceiver=new EggXiaoZhang();
        @Override
        public String OneReceiverWillDoIt()
        {
            return mCommandReceiver.ExecuteCommand();
        }
    }
    public class ChangFenCommand implements AbsCommand
    {
        private ICommandReceiver mCommandReceiver=new ChangFenLaoWang();
        @Override
        public String OneReceiverWillDoIt()
        {
            return mCommandReceiver.ExecuteCommand();
        }
    }
    //新加一个菜品：煎饺
    public class JianJiaoCommand implements AbsCommand
    {
        private ICommandReceiver mCommandReceiver=new JiaoJiaoLiu();
        @Override
        public String OneReceiverWillDoIt()
        {
            return mCommandReceiver.ExecuteCommand();
        }
    }
    //endregion

    //region Invoker
    //命令调用者（服务员）：接受客户调用，并且最终去调用厨师的方法。
    public class CommandInvoker
    {
        public Map<String,List<AbsCommand>> mOrderHistory=new HashMap<String,List<AbsCommand>>();

        //还是需要Invoker来执行，因为可能需要更换command的Receiver（需要服务员来做决定，是否要更换厨师做某个菜。）
        public String CheckAndExecuteCommand(AbsCommand command)
        {
            return command.OneReceiverWillDoIt();
        }

        public String yesterDayAgain(String guest)
        {
            String res="";
            List<AbsCommand> commands=mOrderHistory.get(guest);
            for(AbsCommand command:commands)
            {
                res+=command.OneReceiverWillDoIt();
            }
            return res;
        }
    }
    //endregion

    //region Receiver
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
    public class JiaoJiaoLiu implements ICommandReceiver
    {
        @Override
        public String ExecuteCommand()
        {
            return "jianjiaov2";
        }
    }
    //endregion
}