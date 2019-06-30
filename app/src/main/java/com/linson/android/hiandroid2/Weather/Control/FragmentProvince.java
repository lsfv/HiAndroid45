package com.linson.android.hiandroid2.Weather.Control;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.linson.android.hiandroid2.R;


public class FragmentProvince extends Fragment
{
    public FragmentProvince()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_fragment_province, container, false);
    }
}