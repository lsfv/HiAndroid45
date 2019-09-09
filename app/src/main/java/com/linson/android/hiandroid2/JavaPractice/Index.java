package com.linson.android.hiandroid2.JavaPractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.Model.Sale;
import com.linson.android.hiandroid2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class Index extends LSBaseActivity
{
    private TextView tv_msg;
    //private  static Integer mStaticint=test();

    public  static Integer test()
    {
        try
        {
            Thread.sleep(5000);
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
        LSComponentsHelper.LS_Log.Log_INFO("enter test"+Thread.currentThread().getName());
        return 4;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index2);
        tv_msg = findViewById(R.id.tv_msg);

        //threadpool();

        regex();
    }

    private void regex()
    {
       regex regex=new regex();
//        boolean res=regex.isEmail("aa@173.com");
//        LSComponentsHelper.LS_Log.Log_INFO(res+"");
//
//        try
//        {
//            List<String> res2 = regex.findemails("aa@173.comsdfsdf33a@163.comafsd");
//            for (int i = 0; i < res2.size(); i++)
//            {
//                LSComponentsHelper.LS_Log.Log_INFO(res2.get(i).toString());
//            }
//        } catch (Exception e)
//        {
//            LSComponentsHelper.LS_Log.Log_Exception(e);
//        }

        regex.startStudyRegex();
    }

    private void threadpool()
    {
        ThreadPoolExecutor poolExecutor=new ThreadPoolExecutor(5, 20, 5, TimeUnit.SECONDS ,
                new ArrayBlockingQueue<Runnable>(10),new ThreadPoolExecutor.DiscardOldestPolicy());

        final int[] i = {0};
        while (true)
        {
            poolExecutor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(1000);
                    } catch (Exception e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                    LSComponentsHelper.LS_Log.Log_INFO(i[0] +":"+Thread.currentThread().getId()+".");
                    i[0]++;
                }
            });
        }
    }


    //ListMap();

        //ThreadPractice threadPractice=new ThreadPractice();
        //threadPractice.test();

        //p1();

        //productConsume();

//        BlockQueueTest blockQueueTest=new BlockQueueTest();
//        blockQueueTest.Start();

        //readwrite();

        //listPractice();

//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                LSComponentsHelper.LS_Log.Log_INFO(mStaticint+Thread.currentThread().getName());
//            }
//        }).start();
//
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                LSComponentsHelper.LS_Log.Log_INFO(mStaticint+Thread.currentThread().getName());
//            }
//        }).start();
//
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                LSComponentsHelper.LS_Log.Log_INFO(mStaticint+Thread.currentThread().getName());
//            }
//        }).start();


  //  }

    private void listPractice()
    {
        List<Sale> sales=new LinkedList<>();
        Sale sale=new Sale(1,"linson");
        Sale sale1=new Sale(2, "tony");
        sales.add(sale);
        sales.add(sale1);
        for(Sale item :sales)
        {
            LSComponentsHelper.LS_Log.Log_INFO(item.mName);
        }

        //是我sb了。这里只是把栈里面的元素的指针的指针设置为0.一个元素是否清空是看它是否引用为0.不要把c++的混进来。
        sale1=null;
        for(Sale item :sales)
        {
            LSComponentsHelper.LS_Log.Log_INFO(item.mName);
        }
    }

    private void readwrite()
    {
        try
        {
            SafeReadWriterCache<Integer, Integer> mycache = new SafeReadWriterCache<>();
            Thread writercach = new Thread(new Writercach(mycache, 1, 10000));
            Thread writercach2 = new Thread(new Writercach(mycache, 10001, 20000));


            Thread r1 = new Thread(new Readercache(mycache), "t1");
            Thread r2 = new Thread(new Readercache(mycache), "t2");

            r1.start();
           r2.start();
            writercach.start();
            writercach2.start();
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
    }


    private void productConsume()
    {
        ThreadsPractice2 threadsPractice2=new ThreadsPractice2();

        Thread w1=new Thread(new Writer(threadsPractice2, 1, 10000));
        Thread w2=new Thread(new Writer(threadsPractice2, 10001, 20000));
        w1.start();
        w2.start();


        Thread r1=new Thread(new Reader(threadsPractice2));
        Thread r2=new Thread(new Reader(threadsPractice2));
        r1.start();
        r2.start();
    }

    private class Readercache implements Runnable
    {
        private SafeReadWriterCache<Integer,Integer> mData;
        public Readercache(SafeReadWriterCache<Integer,Integer> threadsPractice2)
        {
            mData=threadsPractice2;
        }
        @Override
        public void run()
        {
            while (true)
            {
                int id=Thread.currentThread().getName()=="t1"?15000:13000;
                Integer bookid=mData.get(id);
                if(bookid==null)
                {
                    LSComponentsHelper.LS_Log.Log_INFO("bookid: null");
                }
                else {
                    LSComponentsHelper.LS_Log.Log_INFO("bookid: "+bookid);
                }
                try
                {
                    Thread.sleep(2000);
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        }
    }

    private class Writercach implements Runnable
    {
        private SafeReadWriterCache<Integer,Integer> mData;
        private int mstart,mend;
        public Writercach(SafeReadWriterCache<Integer,Integer> cache,int satrt,int end)
        {
            mData=cache;
            mstart=satrt;
            mend=end;
        }
        @Override
        public void run()
        {
            long startTime= System.currentTimeMillis();

            for (int i=mstart;i<=mend;i++)
            {
                mData.set(i,i);
            }

            long endTime=System.currentTimeMillis();
            LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"use millseconds:"+(endTime-startTime)+". maxid:");
        }
    }


    private class Writer implements Runnable
    {
        private ThreadsPractice2 mData;
        private int mstart,mend;
        public Writer(ThreadsPractice2 threadsPractice2,int satrt,int end)
        {
            mData=threadsPractice2;
            mstart=satrt;
            mend=end;
        }
        @Override
        public void run()
        {
            long startTime= System.currentTimeMillis();

            for (int i=mstart;i<=mend;i++)
            {
                mData.addBookID(i);
            }

            long endTime=System.currentTimeMillis();
            LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"use millseconds:"+(endTime-startTime)+". maxid:");
        }
    }

    private class Reader implements Runnable
    {
        private ThreadsPractice2 mData;
        public Reader(ThreadsPractice2 threadsPractice2)
        {
            mData=threadsPractice2;
        }
        @Override
        public void run()
        {
            while (true)
            {
                int bookid=mData.getBookID();
                if(bookid>0)
                {
                    LSComponentsHelper.LS_Log.Log_INFO("bookid:"+bookid);
                }
            }
        }
    }

    private void p1()
    {
        ThreadsPractice threadsPractice=new ThreadsPractice();
        try
        {
            threadsPractice.start();
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
    }


    //thread
    //1.synchronized 是每个类都有的内部锁，可以非常方便的让某段代码单线程访问。
    //2. a.join,会让当前线程调用此方法的线程等待，这里是当前线程会等待a线程完毕。再运行a.join下面的语句。但不会阻止其他线程运行。
    private  class ThreadPractice
    {
        private  int counter=0;
        private Object obj_mutex=new Object();

        public  void nosafeadd()
        {
            int pre=counter;
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                LSComponentsHelper.LS_Log.Log_Exception(e);
            }
            counter=pre+1;
        }

        public void safeadd()
        {
            synchronized (obj_mutex)
            {
                LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"before:"+counter);
                int pre = counter;
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                counter = pre + 1;
                LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"after:"+counter);
            }
        }

        public  void test()
        {
            Thread thread1=new Thread("thread1")
            {
                @Override
                public void run()
                {
                    for(int i=0;i<10;i++)
                    {
                        //nosafeadd();
                        safeadd();
                    }
                }
            };
            thread1.start();

            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                LSComponentsHelper.LS_Log.Log_Exception(e);
            }

            Thread thread2=new Thread("thread2")
            {
                @Override
                public void run()
                {
                    for(int i=0;i<10;i++)
                    {
                        //nosafeadd();
                        safeadd();
                    }
                }
            };
            thread2.start();


            try
            {
                LSComponentsHelper.LS_Log.Log_INFO("thread1join");
                thread1.join();
                LSComponentsHelper.LS_Log.Log_INFO("thread2join");
                thread2.join();
            } catch (InterruptedException e)
            {
                LSComponentsHelper.LS_Log.Log_Exception(e);
            }

            LSComponentsHelper.LS_Log.Log_INFO("counter:"+counter);
        }

    }



    //map
    private void ListMap()
    {
        Map<Integer,String> books=new HashMap<>();
        books.put(1, "c++");
        books.put(2,"java" );

        for(Map.Entry entry : books.entrySet())
        {
            LSComponentsHelper.Log_INFO(entry.getKey()+":"+entry.getValue());
        }

        Integer[] nums=new Integer[]{1,5,6,7,8,2};
        Arrays.sort(nums);

        for(Integer integer :nums)
        {
            LSComponentsHelper.Log_INFO("item:"+integer);
        }

        ArrayList<Integer> nums2=new ArrayList<>();
        nums2.add(5);
        nums2.add(9);
        nums2.add(1);
        Collections.sort(nums2);

        for(Integer integer :nums2)
        {
            LSComponentsHelper.Log_INFO("item2:"+integer);
        }
    }



}