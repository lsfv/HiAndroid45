package com.linson.android.hiandroid2.Fragment;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;


//简单模拟底部菜单导航，一般可以设置为不入栈。 需要实现singnaltask,singaletinstance的效果 ,入栈的时候标记一下，退出根据标记来处理，如addToBackStack("fragment1");
public class InterAction_index extends AppCompatActivity implements View.OnClickListener
{
    //region Controls member
    private ConstraintLayout mFragmentContext;
    private Button mButton17;
    private Button mButton16;
    private Button mButton15;
    private EditText mEtIndexpage;
    private Button mButton18;
    private TextView mTextView14;
    //endregion

    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mFragmentContext = (ConstraintLayout) findViewById(R.id.fragment_context);
        mButton17 = (Button) findViewById(R.id.button17);
        mButton16 = (Button) findViewById(R.id.button16);
        mButton15 = (Button) findViewById(R.id.button15);
        mEtIndexpage = (EditText) findViewById(R.id.et_indexpage);
        mButton18 = (Button) findViewById(R.id.button18);
        mTextView14 = (TextView) findViewById(R.id.textView14);

        //set event handler
        mButton15.setOnClickListener(this);
        mButton16.setOnClickListener(this);
        mButton17.setOnClickListener(this);
        mButton18.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button15:
            {
                fragment1();
                break;
            }
            case R.id.button17:
            {
                fragment2();
                break;
            }
            case R.id.button16:
            {
                fragment3();
                break;
            }
            case R.id.button18:
            {
                FragmentManager fragmentManager=getSupportFragmentManager();
                Fragment fragment= fragmentManager.findFragmentById(R.id.fragment_context);

                if(fragment instanceof InterAction_framegent1)
                {
                    EditText tv_1 = fragment.getView().findViewById(R.id.et_fragment1);
                    mTextView14.setText(tv_1.getText());
                }
                else if(fragment instanceof InterAction_fragment2)
                {
                    EditText tv_1 = fragment.getView().findViewById(R.id.et_fragment1);
                    mTextView14.setText(tv_1.getText());
                }
                else if(fragment instanceof InterAction_fragment3)
                {
                    EditText tv_1 = fragment.getView().findViewById(R.id.et_fragment1);
                    mTextView14.setText(tv_1.getText());
                }

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
        setContentView(R.layout.activity_inter_action_index);
        findControls();
        defaltFragment();
    }

    private void defaltFragment()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction_defaultFragment=fragmentManager.beginTransaction();
        fragmentTransaction_defaultFragment.replace(R.id.fragment_context, new InterAction_framegent1());
        fragmentTransaction_defaultFragment.commit();
    }

    private void fragment1()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction_defaultFragment=fragmentManager.beginTransaction();
        fragmentTransaction_defaultFragment.replace(R.id.fragment_context, new InterAction_framegent1());
        //fragmentTransaction_defaultFragment.addToBackStack("fragment1");
        fragmentTransaction_defaultFragment.commit();
    }

    private void fragment2()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction_defaultFragment=fragmentManager.beginTransaction();
        fragmentTransaction_defaultFragment.replace(R.id.fragment_context, new InterAction_fragment2());
        //fragmentTransaction_defaultFragment.addToBackStack("fragment2");
        fragmentTransaction_defaultFragment.commit();
    }

    private void fragment3()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction_defaultFragment=fragmentManager.beginTransaction();
        fragmentTransaction_defaultFragment.replace(R.id.fragment_context, new InterAction_fragment3());
        //fragmentTransaction_defaultFragment.addToBackStack(null);
        fragmentTransaction_defaultFragment.commit();
    }

    //这里并不能立即显示堆栈，因为commit不是立即执行的。
    private void ShowBACKStack()
    {
        FragmentManager fragmentManager=getSupportFragmentManager();
        int size=fragmentManager.getBackStackEntryCount();
        for(int i=0;i<size;i++)
        {
            LSComponentsHelper.LS_Log.Log_INFO(fragmentManager.getBackStackEntryAt(i).getName() +"id:"+fragmentManager.getBackStackEntryAt(i).getId());
        }
    }
}