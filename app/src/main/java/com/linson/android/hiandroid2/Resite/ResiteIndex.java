package com.linson.android.hiandroid2.Resite;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;





//trace while wait 的路径
public class ResiteIndex extends AppCompatActivity implements View.OnClickListener
{
    //region Controls member
    private Button mBtnMsg;
    private TextView mTextView13;
    //endregion
    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnMsg = (Button) findViewById(R.id.btn_msg);
        mTextView13 = (TextView) findViewById(R.id.textView13);

        //set event handler
        mBtnMsg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_msg:
            {
                String[] chooseitem={"a","b"};
                boolean[] chooseitemres={true,false};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("hi choose");
                builder.setMultiChoiceItems(chooseitem, chooseitemres, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked)
                    {

                    }
                });
                AlertDialog choose = builder.create();
                choose.show();
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion
    //region other member variable
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resite_index);
        findControls();
        //maptest();
        //sort();
        //produceAndConsume();
        enumTest();
        scrollTextView();


    }

    private void scrollTextView()
    {
        //mTextView13.setScrollbarFadingEnabled(true);
        mTextView13.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    public enum mEnum_books
    {
        c2,
        c,
        java,
        cp,
        ok,
    }

    private void enumTest()
    {
        String booksname=mEnum_books.java.name();
        mEnum_books mybook= mEnum_books.valueOf(booksname);
        if(mybook!=mEnum_books.java)
        {
            LSComponentsHelper.LS_Log.Log_INFO("error");
        }
        int enumIndex=mEnum_books.java.ordinal();
        mEnum_books mybook2=mEnum_books.values()[enumIndex];
        if(mybook2!=mEnum_books.java)
        {
            LSComponentsHelper.LS_Log.Log_INFO("error2");
        }
        mEnum_books.java.ordinal();
    }

    private void maptest()
    {
        Map<Integer,String> book=new HashMap<>();
        book.put(1, "c");
        book.put(2,"c++" );
        for(Map.Entry<Integer,String> item : book.entrySet())
        {
            LSComponentsHelper.LS_Log.Log_INFO(item.getValue());
        }

    }

    private void sort()
    {
        List<Integer> nums=new ArrayList<>();
        nums.add(6);
        nums.add(4);
        nums.add(8);
        Collections.sort(nums);
        for(Integer item : nums)
        {
            LSComponentsHelper.LS_Log.Log_INFO(item.toString());
        }

        //Integer[] nums2=new Integer[3];
        Integer[] nums2={8,7,9};
        Arrays.sort(nums2);
        for(Integer item : nums2)
        {
            LSComponentsHelper.LS_Log.Log_INFO(item.toString());
        }
    }

    //region Product and consume
    private static Stack<Integer> mData=new Stack<>();
    private static Integer mNums=0;
    private static ReentrantLock mlock=new ReentrantLock();
    private static Condition mEmpty=mlock.newCondition();
    private static Condition mFull=mlock.newCondition();


    private void produceAndConsume()
    {
        Thread p1=new Thread(new Producter(),"p1");
        Thread p2=new Thread(new Producter(),"p2");

        Thread c1=new Thread(new Consume(),"c1");

        p1.start();
        p2.start();
        c1.start();
    }


    private class  Producter implements Runnable
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(3000);//模拟一个耗时的操作。注意，必须放到锁外部，不然没意义。所以我们才需要多线程。
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                mlock.lock();
                try
                {
                    while (mData.size()>=15)
                    {
                        mFull.await();
                    }

                    mData.push(mNums);
                    LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+". add "+mNums.toString()+".         size:"+mData.size());
                    mNums+=1;
                    mEmpty.signal();
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                } finally
                {
                    mlock.unlock();
                }
            }
        }
    }

    private class Consume implements Runnable
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(6000);//模拟处理很慢，必须放入到锁外。<1.5秒。读快,集合数据处于处理完的空状态。 >1.5秒读慢,集合数据处于累加状态，最后保持满状态，处理太慢。 可以用 1000和6000来测试。
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                mlock.lock();
                try
                {
                    while (mData.size() <= 0)
                    {
                        mEmpty.await();
                    }
                    Integer getn=mData.pop();
                    LSComponentsHelper.LS_Log.Log_INFO(Thread.currentThread().getName()+"reader:"+getn.toString()+".size:"+mData.size());
                    mFull.signal();

                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                } finally
                {
                    mlock.unlock();
                }
            }
        }
    }
    //endregion
}