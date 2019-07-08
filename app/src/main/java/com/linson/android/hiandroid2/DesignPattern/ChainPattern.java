package com.linson.android.hiandroid2.DesignPattern;



import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;



//看装饰模式的时候，如果看明白了。那么职责链就会秒懂了。因为2个东西，是一个技术。只是用来解决不同的问题而已。
//装饰模式，也需要一个链，因为必须一步一步的装饰。
//其实是一个很简单的技术，链表也是这样，只不过这里需要重载方法，所以是个虚类。超级简单。
//当然不用链表的技术，用一个数组来保存一个职责链，也没有任何问题。
//直接用数组替代这个模式，应该是没有违反第一 原则，开闭原则。 但是职责链，有一种简洁的感觉，不需要一个管理类来维护数组，也不需要在他们之间传递数据。
//个人感觉大多数情况可以用管理类+数组来代替这个模式。  除非像处理日志，这种可以肯定不会有更复杂的扩展的需求，可以考虑职责链模式。
//想象一下，如果要扩展变化了。处理职责的功能可能是动态的，需要的方法，对象不等，这就比较麻烦，所以职责链适合 某个底层，孤独模块，可以确定不会有大变动的模块。
//不确定，干脆不用呗。管理类+数组， 大家都秒懂。
public class ChainPattern
{
    public void Run()
    {
        InfoMsg firstChain=new InfoMsg();
        ErrorMsg sChain=new ErrorMsg();
        firstChain.setNextChain(sChain);

        firstChain.printMsg("hi",enum_msgtype.info );
        firstChain.printMsg("error!!",enum_msgtype.error );
    }

    public enum enum_msgtype
    {
        info,
        debug,
        error
    }

    public abstract class MyChain
    {
        public enum_msgtype mMsgtype;
        public MyChain mNextChain;

        public abstract void printMsg(String msg,enum_msgtype msgtype);
        public void setNextChain(MyChain chain)
        {
            mNextChain=chain;
        }
    }

    public class InfoMsg extends MyChain
    {
        @Override
        public void printMsg(String msg,enum_msgtype msgtype)
        {
            if(msgtype==enum_msgtype.info)
            {
                LSComponentsHelper.LS_Log.Log_INFO("info:" + msg);
            }
            if(mNextChain!=null)
            {
                mNextChain.printMsg(msg,msgtype);
            }
        }
    }

    public class ErrorMsg extends MyChain
    {
        @Override
        public void printMsg(String msg,enum_msgtype msgtype)
        {
            if(msgtype==enum_msgtype.error)
            {
                LSComponentsHelper.LS_Log.Log_INFO("Error:" + msg);
            }
            if(mNextChain!=null)
            {
                mNextChain.printMsg(msg,msgtype);
            }
        }
    }
}