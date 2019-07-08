package com.linson.android.hiandroid2.DesignPattern;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Command
{

    //Encapsulate a request as an object,thereby letting you parameterize clients with different
    //requests,queue or log requests, and support undoable operations.
    //将一个请求封装成一个对象，从而让你使用不同的请求把客户端参数化，对请求排队或者记录请求日志，可以提供命令的撤销和恢复功能

    //命令模式很多目的，我们来实现其中一个简单的目的：记录请求。看看是否可以自行重构出命令模式。
    //1.首先用点菜来作为模拟场景。需要厨师来做菜，客人不能直接调用厨师的方法，所以需要一个服务员来作为客人和服务员的中介，这个是符合正常人思维并与现实吻合的。
    //第一版可以正常工作。可以看到唯一的不完美就是CommandInvoker中，创建命令的接受者没有符合开闭原则。
    //但是那个是工厂模式的事情。并且我们这里假设厨师是相对稳定的,不稳定的是客户的点餐，以致我们可以从适度设计的原则来抛弃使用工厂模式的想法。
    //2.实现记录命令功能，这里就是 某个常客说，来早餐，和昨天一样。 按道理很简单。存储一下啊。仔细看看BaoziCommand()。这个是方法没法储存。好吧开始迭代升级。
    public void Run()
    {
        String BreakFast="";
        CommandInvoker waiter=new CommandInvoker();
        BreakFast+= waiter.BaoziCommand();
        BreakFast+=waiter.EggCommand();
        BreakFast+=waiter.ChangFenCommand();
        LSComponentsHelper.LS_Log.Log_INFO(BreakFast);
    }

    //命令调用者（服务员）：接受客户调用，并且最终去调用厨师的方法。
    public class CommandInvoker
    {
        private ICommandReceiver mBaozi=new BaoziTony();
        private ICommandReceiver mEgg=new EggXiaoZhang();
        private ICommandReceiver mChangfen=new ChangFenLaoWang();
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
            return "BaoZi";
        }
    }
    public class EggXiaoZhang implements ICommandReceiver
    {
        @Override
        public String ExecuteCommand()
        {
            return "Egg";
        }
    }
    public class ChangFenLaoWang implements ICommandReceiver
    {
        @Override
        public String ExecuteCommand()
        {
            return "ChangFen";
        }
    }

}