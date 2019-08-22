package com.linson.android.hiandroid2.UI.NewCustomView.MyView.viewLib;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;

public class GroupTest extends ConstraintLayout implements View.OnClickListener
{
    private TextView mTextView11;
    private Button mButton14;
    private int clickSum=0;


    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mTextView11 = (TextView) findViewById(R.id.textView11);
        mButton14 = (Button) findViewById(R.id.button14);

        //set event handler
        mButton14.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button14:
            {
                clickSum++;
                mTextView11.setText(this.getTag()==null?clickSum+".":this.getTag().toString()+clickSum+".");
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion


    //member variable

    public GroupTest(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.group_test, this, true);
        findControls();
    }
}