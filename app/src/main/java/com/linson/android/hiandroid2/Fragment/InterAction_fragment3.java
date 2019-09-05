package com.linson.android.hiandroid2.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterAction_fragment3 extends Fragment
{


    public InterAction_fragment3()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inter_action_fragment3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        InterAction_index activity= (InterAction_index)getActivity();
        EditText editText= activity.findViewById(R.id.et_indexpage);

        TextView tv=getView().findViewById(R.id.textView15);
        tv.setText(editText.getText());
    }

}
