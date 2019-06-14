package com.linson.android.hiandroid2.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.linson.android.DAL.SampleData;
import com.linson.android.hiandroid2.R;

import java.util.List;

public class SimpleShop extends ArrayAdapter<SampleData.ShopInfo>
{
    private TextView mTvName;
    private TextView mTvScore;



    private int resourceID;
    public SimpleShop(@NonNull Context context, int resource, @NonNull List<SampleData.ShopInfo> objects)
    {
        super(context, resource, objects);
        resourceID=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ControlsHolder controlsHolder=null;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(resourceID, parent,false);
            controlsHolder=new ControlsHolder();
            controlsHolder.mTvName=(TextView) convertView.findViewById(R.id.tv_name);
            controlsHolder.mTvScore=(TextView) convertView.findViewById(R.id.tv_score);
            convertView.setTag(controlsHolder);
        }
        else
        {
            controlsHolder=(ControlsHolder)convertView.getTag();
        }

        controlsHolder.mTvName.setText(((SampleData.ShopInfo)getItem(position)).mname);
        controlsHolder.mTvScore.setText(((SampleData.ShopInfo)getItem(position)).msocre+"");

        return convertView;
    }

    private static class ControlsHolder
    {
        public TextView mTvName;
        public TextView mTvScore;
    }
}