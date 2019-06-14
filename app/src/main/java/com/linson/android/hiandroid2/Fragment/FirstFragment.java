package com.linson.android.hiandroid2.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

public class FirstFragment extends Fragment
{
    @Override
    public void onAttach(Context context)
    {
        
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onCreateView");
        return inflater.inflate(R.layout.fragment_title_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onActivityCreated");
//        try
//        {
//            ((TextView) getActivity().findViewById(R.id.tv_flag)).setText(mTitle);
//        }
//        catch (Exception e)
//        {
//            LSComponentsHelper.LS_Log.Log_Exception(e);
//        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart()
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onStart");
        super.onStart();
    }

    @Override
    public void onResume()
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onResume");
        super.onResume();
    }

    @Override
    public void onPause()
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onPause");
        super.onPause();
    }

    @Override
    public void onStop()
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach()
    {
        LSComponentsHelper.LS_Log.Log_INFO("fragment1 onDetach");
        super.onDetach();
    }
}
