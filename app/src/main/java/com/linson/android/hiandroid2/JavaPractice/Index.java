package com.linson.android.hiandroid2.JavaPractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public class Index extends LSBaseActivity
{
    private TextView tv_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index2);
        tv_msg=findViewById(R.id.tv_msg);

        //ListMap();

        //ThreadPractice threadPractice=new ThreadPractice();
        //threadPractice.test();

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