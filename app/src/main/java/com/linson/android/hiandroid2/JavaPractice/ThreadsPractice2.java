package com.linson.android.hiandroid2.JavaPractice;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//线程安全的list
public class ThreadsPractice2
{
    private LinkedList<Integer> mBookID=new LinkedList<>();
    private ReentrantLock mReentrantLock=new ReentrantLock();
    private Condition full=mReentrantLock.newCondition();
    private Condition empty=mReentrantLock.newCondition();

    public Integer getBookID()
    {
        mReentrantLock.lock();
        try
        {
            while (mBookID.size()<=0)
            {
                empty.await();
            }
            int id = mBookID.removeFirst();
            full.signalAll();
            return id;

        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
            return 0;
        }
        finally
        {
            mReentrantLock.unlock();
        }
    }

    public void addBookID(int id)
    {
        mReentrantLock.lock();
        try
        {
            while (mBookID.size()>=100)
            {
                full.await();
            }
            mBookID.add(id);
            empty.signalAll();
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
        finally
        {
            mReentrantLock.unlock();
        }
    }
}