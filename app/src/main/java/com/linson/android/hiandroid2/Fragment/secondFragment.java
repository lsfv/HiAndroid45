package com.linson.android.hiandroid2.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linson.android.hiandroid2.R;

public class secondFragment extends Fragment
{
    public secondFragment()
    { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }


}