package com.linson.android.hiandroid2.DesignPattern;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

public class Index extends AppCompatActivity
{
    private TextView mTvMsg;

    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
    }
    //endregion

    //member variable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index17);
        findControls();

        //Factory.Run();
        //signal();
        //prototype();
        //Adapter.Run();
        //Bridge bridge=new Bridge();
        //bridge.run();
//        Decorator decorator=new Decorator();
//        decorator.Run();
//        ChainPattern chainPattern=new ChainPattern();
//        chainPattern.Run();

        //CommandV3 command=new CommandV3();
        //command.Run();
//        MyIterator myIterator=new MyIterator();
//        myIterator.Run();
//        MyObserver myObserver=new MyObserver();
//        myObserver.Run();

//        MyState myState=new MyState();
//        myState.Run();
//        myState.Runv2();
//        Template template=new Template();
//        template.Run();
//        Visister visister=new Visister();
//        visister.Run();
//
//        visister.Run2();

        Interpreter interpreter=new Interpreter();
        interpreter.Run();
        interpreter.Run2();
    }

    private void prototype()
    {
        Prototype prototype=new Prototype();
        prototype.mname="linson";
        prototype.mid=1;
        prototype.mYears.add(1);

        try
        {
            Prototype prototype1 = (Prototype) prototype.clone();
            prototype1.mid=2;
            prototype1.mname="tony";
            prototype1.mYears.add(1);

            LSComponentsHelper.LS_Log.Log_INFO(prototype.mid+":"+prototype.mname+""+prototype.mYears.size()+"!!!!!!!!"+ prototype1.mid+":"+prototype1.mname+""+prototype1.mYears.size());
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }


    }

    private void signal()
    {
        Signal signal=Signal.getInstance();
        signal.SysName="hi";

        Signal signal1=Signal.getInstance();
        LSComponentsHelper.LS_Log.Log_INFO(signal1.SysName);
    }
}