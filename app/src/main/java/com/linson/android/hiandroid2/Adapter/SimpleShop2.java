package com.linson.android.hiandroid2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.linson.android.DAL.SampleData;
import com.linson.android.hiandroid2.R;

import java.util.List;

//直接继承arraylist。也是可以的。arraylist很轻量的类，只多加了大概几十字节而已。省去了一些通用做法的函数的编写。
public class SimpleShop2 extends BaseAdapter
{
    private int resourceID;
    private List<SampleData.ShopInfo> mdata;
    private Context mcontext;


    public SimpleShop2(@NonNull Context context, int resource, @NonNull List<SampleData.ShopInfo> objects)
    {
        super();
        mdata=objects;
        resourceID=resource;
        mcontext=context;
    }
    @Override
    public int getCount()
    {
        return mdata.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        SimpleShop2.ControlsHolder controlsHolder=null;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(mcontext).inflate(resourceID, parent,false);
            controlsHolder=new SimpleShop2.ControlsHolder();
            controlsHolder.mTvName=(TextView) convertView.findViewById(R.id.tv_name);
            controlsHolder.mTvScore=(TextView) convertView.findViewById(R.id.tv_score);
            convertView.setTag(controlsHolder);
        }
        else
        {
            controlsHolder=(SimpleShop2.ControlsHolder)convertView.getTag();
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
