package com.linson.android.hiandroid2.JavaPractice;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockQueueTest
{
    private BlockingQueue<Integer> mBlockingQueue=new ArrayBlockingQueue<>(100);
    private Set<Integer> thread1Set=new HashSet<>();
    private Set<Integer> thread2Set=new HashSet<>();
    private Set<Integer> thread3Set=new HashSet<>();
    public void Start()
    {
        Thread w1=new Thread(new Writer(mBlockingQueue,1,10000));
        Thread w2=new Thread(new Writer(mBlockingQueue,10001,20000));

        Thread r1=new Thread(new Reader(mBlockingQueue),"thread1");
        Thread r2=new Thread(new Reader(mBlockingQueue),"thread2");
        Thread r3=new Thread(new Reader(mBlockingQueue),"thread3");

        w1.start();
        w2.start();
        r1.start();
        r2.start();
        r3.start();

        try
        {
            w1.join();
            w2.join();
            LSComponentsHelper.LS_Log.Log_INFO(thread1Set.size()+"."+thread2Set.size()+"."+thread3Set.size()+".");
            Thread.sleep(5000);
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }

        LSComponentsHelper.LS_Log.Log_INFO(thread1Set.size()+"."+thread2Set.size()+"."+thread3Set.size()+".");
    }

    private class Writer implements Runnable
    {
        private BlockingQueue<Integer> mdata;
        private int mStart=0;
        private int mEnd=0;
        public Writer(BlockingQueue<Integer> blockingQueue,int start,int end)
        {
            mdata = blockingQueue;
            mStart=start;
            mEnd=end;
        }
        @Override
        public void run()
        {
            for(int i=mStart;i<=mEnd;i++)
            {
                try
                {
                    mdata.put(i);
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }
    }

    private class Reader implements Runnable
    {
        private BlockingQueue<Integer> mdata;
        public Reader(BlockingQueue<Integer> blockingQueue)
        {
            mdata = blockingQueue;
        }
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    int res = mdata.take();
                    if(Thread.currentThread().getName()=="thread1")
                    {
                        thread1Set.add(res);
                    }
                    if(Thread.currentThread().getName()=="thread2")
                    {
                        thread2Set.add(res);
                    }
                    if(Thread.currentThread().getName()=="thread3")
                    {
                        thread3Set.add(res);
                    }
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }
    }
}
