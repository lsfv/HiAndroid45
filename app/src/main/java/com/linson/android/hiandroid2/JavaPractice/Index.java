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

        ThreadPractice_ps threadPractice_ps=new ThreadPractice_ps();
        threadPractice_ps.start();
    }



    //reentrancelock.可重入锁, codition.
    //06-13 15:49:31.715 14878-14946/com.linson.android.hiandroid2 I/MYCUSTOM: product:1033.size:1
    //06-13 15:49:31.715 14878-14945/com.linson.android.hiandroid2 I/MYCUSTOM: product:1033.size:2

    //06-13 15:52:00.719 15219-15279/com.linson.android.hiandroid2 I/MYCUSTOM: product:211.size:101
    //06-13 15:52:00.719 15219-15280/com.linson.android.hiandroid2 I/MYCUSTOM: product:211.size:101

    //06-13 15:57:24.257 15503-15567/com.linson.android.hiandroid2 I/MYCUSTOM: readerr.java.lang.IndexOutOfBoundsException
    private class ThreadPractice_ps
    {
        private int productNo=0;
        private List<Integer> mStory=new LinkedList<>();
        private ReentrantLock mReentrantLock_story=new ReentrantLock();

        public void start()
        {
            //2 writer .3 reader
            new Thread()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        unsafeWrite();
                        try
                        {
                            Thread.sleep(20);
                        } catch (InterruptedException e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    }
                }
            }.start();

            new Thread()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        unsafeWrite();
                        try
                        {
                            Thread.sleep(20);
                        } catch (InterruptedException e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    }
                }
            }.start();

            new Thread()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        unsafeRead();
                        try
                        {
                            Thread.sleep(20);
                        } catch (InterruptedException e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    }
                }
            }.start();

            new Thread()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        unsafeRead();
                        try
                        {
                            Thread.sleep(20);
                        } catch (InterruptedException e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    }
                }
            }.start();

            new Thread()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        unsafeRead();
                        try
                        {
                            Thread.sleep(20);
                        } catch (InterruptedException e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    }
                }
            }.start();
        }


        public void unsafeWrite()
        {
            mReentrantLock_story.lock();
            try
            {
                if(mStory.size()<100)
                {
                    mStory.add(productNo);
                    LSComponentsHelper.LS_Log.Log_INFO("product:"+productNo+".size:"+mStory.size());
                    productNo++;
                }
            }
            catch (Exception e)
            {
                LSComponentsHelper.LS_Log.Log_Exception(e);
            }
            mReentrantLock_story.unlock();
        }

        public void unsafeRead()
        {
            mReentrantLock_story.lock();
            try
            {
                if(mStory.size()>0)
                {
                    Integer pid= mStory.remove(mStory.size()-1);
                    LSComponentsHelper.LS_Log.Log_INFO("use:"+pid);
                }
            }
            catch (Exception e)
            {
                LSComponentsHelper.LS_Log.Log_Exception(e,"readerr");
            }
            mReentrantLock_story.unlock();
        }
    }

    //thread
    //1.synchronized 是每个类都有的内部锁，可以非常方便的让某段代码单线程访问。
    //2. a.join,会让当前线程等待调用此方法的线程，这里是当前线程会等待a线程完毕。再运行a.join下面的语句。但不会阻止其他线程运行。很多blog说错了。
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