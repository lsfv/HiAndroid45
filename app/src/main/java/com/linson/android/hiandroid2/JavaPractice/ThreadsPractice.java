package com.linson.android.hiandroid2.JavaPractice;

import android.content.BroadcastReceiver;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


//无锁，多线程读，因为removefrist 操作，大概率会出错，推测是同时删除，导致list为空了？ 数据竞增必须加锁。
//writer use millseconds:21087  reader :error.
//单纯性加锁，当然只锁住操作共享数据mBookNum，mReadBookNum，mBooksID。除了不会出错之外。发现效率基本没有变低。大概降低了千分之5.基本没变了。
// writer1use millseconds:21106. reader1use millseconds:22548: reader2use millseconds:22554.num:4001
//那下面confition就无法测试下去了。所以必须做一个特殊场景来体现其作用。就假设内存非常紧张。让写线程，发现数据超过10条。就轮询sleep或yied，等待reader来取走一些数据。这样让writer多做点无效的轮询。
//突然发现sleep或yied，是有bug的，因为必须交出锁给读线程，不然死锁啊。 所以误打误撞，发现了condition的作用，不单是理论上的效率的提高，而是这张场景下必须使用的元素。不然死锁。
//恩，，，也可以不用。把轮询中的轮询改为contion。跳过这次。当然需要休息一下。不能让写线程故意疯狂无用功。
//writer1use millseconds:22499. reader1use millseconds:22552 reader2use millseconds:22556   .sencond:22520
//测试发现单线程无脑轮询，效率降低 5%，不过这里的降低应该是没有降低，而是在等待读线程罢了。应该是记录使用cpu的时间和峰值才对。
//用condition. aweit, single。发现无任何变化。应该是压力不够大。无法测试出condition的优势了。
//基本上这里的短板就在于读线程没有

public class ThreadsPractice
{
    private LinkedList<Integer> mBooksID=new LinkedList<>();
    private int mBookNum=0;
    private int mReadBookNum=0;
    private final  int maxBOOKS=4000;
    private ReentrantLock mReentrantLock=new ReentrantLock();
    private Condition mCondition=mReentrantLock.newCondition();

    public void start() throws Exception
    {
        Thread writer1=new Thread(new Writer(),"writer1");
        writer1.start();
        Thread reader1=new Thread(new Reader(),"reader1");
        reader1.start();
        Thread reader2=new Thread(new Reader(),"reader2");
        reader2.start();
    }

    private class Writer implements Runnable
    {
        @Override
        public void run()
        {
           long startTime= System.currentTimeMillis();

            while (true)
            {
                if(mBookNum>maxBOOKS)
                {
                    break;
                }

                try
                {
                    Thread.sleep(5);
                }
                catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                mReentrantLock.lock();
                try
                {
                    if (mBooksID.size()>10)
                    {
                        //mCondition.await();
                        Thread.yield();
                        continue;
                    }
                    mBooksID.add(mBookNum);
                    //LSComponentsHelper.LS_Log.Log_INFO("write:"+mReadBookNum+"");
                    mBookNum++;
                }
                catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                finally
                {
                    mReentrantLock.unlock();
                }
            }

            long endTime=System.currentTimeMillis();
            LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"use millseconds:"+(endTime-startTime)+". maxid:"+mBookNum);
        }
    }

    private  class Reader implements Runnable
    {
        @Override
        public void run()
        {
            long startTime= System.currentTimeMillis();
            while (true)
            {
                //LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"say :"+mReadBookNum+".size"+mBooksID.size());
                if(mReadBookNum>=maxBOOKS)
                {
                    break;
                }

                try
                {
                    if(mBooksID.size()>0)
                    {
                        Thread.sleep(22);
                        mReentrantLock.lock();
                        try
                        {
                            mBooksID.removeFirst();
                            //LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"read:"+mReadBookNum+".size"+mBooksID.size());
                            mReadBookNum++;
                            //mCondition.signalAll();
                        } catch (Exception e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e,"nofity");
                        }
                        finally
                        {
                            mReentrantLock.unlock();
                        }

                    }
                    else
                    {
                        Thread.yield();
                    }
                } catch (InterruptedException e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }

            long endTime=System.currentTimeMillis();
            LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"use millseconds:"+(endTime-startTime)+".num:"+mReadBookNum);
        }
    }
}