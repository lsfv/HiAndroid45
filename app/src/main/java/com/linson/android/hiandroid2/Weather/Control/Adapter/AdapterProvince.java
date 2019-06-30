package com.linson.android.hiandroid2.Weather.Control.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.Weather.Model.Json_Province;

import java.util.List;

public class AdapterProvince extends RecyclerView.Adapter<AdapterProvince.MyViewHolder>
{
    private List<Json_Province> mData;
    private Context mContext;
    private AdapterCity.IOnClickItem mIOnClickItem;

    public AdapterProvince(@NonNull List<Json_Province> data,@NonNull Context context,@NonNull AdapterCity.IOnClickItem onClickItem)
    {
        mData=data;
        mContext=context;
        mIOnClickItem=onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View theview= LayoutInflater.from(mContext).inflate(R.layout.item_province, viewGroup,false);
        MyViewHolder theviewHolder=new MyViewHolder(theview);
        return theviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        final Json_Province json_province=mData.get(i);
        myViewHolder.tv_content.setText(json_province.name);
        myViewHolder.tv_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIOnClickItem.OnClickProvince(json_province.id);
            }
        });
    }

    @Override
    public int getItemCount()
    {
       // return 0;
        try
        {
            if (mData != null)
            {
                return mData.size();
            } else
            {
                return 0;
            }
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
            return 0;
        }
    }

    //region adapter
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_content;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tv_content=(TextView) itemView.findViewById(R.id.tv_item);
        }
    }
    //endregion

}