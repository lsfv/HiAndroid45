package com.linson.android.hiandroid2.DesignPattern;


import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;


//一直以为不举模式，是模板模式。因为最初不举模式和模板模式都没看懂。干脆就没去看。
//道可道，非常道，名可名，非常名。一个名字称呼而已。实在区分不了。先把握开闭的基本原则就好。后面慢慢体会。
//做一个debug信息的输出模板
public class Bridge
{
    public void run()
    {
        //2中变化。可以组合成为4种。 这个还不是重点。重点是变化的东西（接口），是在另一个变化当中（虚类）.
        //如果有5个变化点。那么就有2×2×2×2×2种变化。不过有多少变化不是目的。而是说，如果有这种需求。那么不举模式bridge适合你。

        //来一个最普通的，头部简单的的debug信息.
        //注意:normal就说明了它普通。而又传入了SimpleHeader
        Pringlog_normal pringlog_normal=new Pringlog_normal(new SimpleHeader());
        String msg1=pringlog_normal.getLog("overtime");

        //来一个最普通的，头部信息稍微详尽的的debug信息.
        //注意:normal就说明了它普通。而又传入了SimpleHeader
        Pringlog_normal pringlog_normal1=new Pringlog_normal(new DetailHeader());
        String msg2=pringlog_normal1.getLog("overtime");

        //来一个警告版本，头部信息稍微详尽的的debug信息.
        //注意:normal就说明了它普通。而又传入了SimpleHeader
        Pringlog_error pringlog_error=new Pringlog_error(new DetailHeader());
        String msg3=pringlog_error.getLog("overtime");

        LSComponentsHelper.LS_Log.Log_INFO(msg1+"\r\n"+msg2+"\r\n"+msg3);
    }

    public interface IDebugMsgHead
    {
        public String PrintHead();
    }

    public class SimpleHeader implements IDebugMsgHead
    {
        @Override
        public String PrintHead()
        {
            return "1982:";
        }
    }

    public class DetailHeader implements IDebugMsgHead
    {
        @Override
        public String PrintHead()
        {
            return "1982 01 01;line: 25";
        }
    }


    public abstract class PringLog
    {
        public IDebugMsgHead mDebugMsgHead;
        public String getLog(String msgbody)
        {
            return mDebugMsgHead.PrintHead()+msgbody;
        }
    }

    public class Pringlog_normal extends PringLog
    {
        public Pringlog_normal(IDebugMsgHead msgHead)
        {
            mDebugMsgHead=msgHead;
        }

        @Override
        public String getLog(String msgbody)
        {
            return mDebugMsgHead.PrintHead()+". it it a msg body:"+msgbody;
        }
    }

    public class Pringlog_error extends PringLog
    {
        public Pringlog_error(IDebugMsgHead msgHead)
        {
            mDebugMsgHead=msgHead;
        }

        @Override
        public String getLog(String msgbody)
        {
            return "!!!!!!!!!!"+mDebugMsgHead.PrintHead()+msgbody;
        }
    }
}
