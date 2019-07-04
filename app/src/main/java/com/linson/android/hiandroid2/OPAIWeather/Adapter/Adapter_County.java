package com.linson.android.hiandroid2.OPAIWeather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.OPAIWeather.Model.City;
import com.linson.android.hiandroid2.OPAIWeather.Model.County;
import com.linson.android.hiandroid2.R;

import java.util.List;

public class Adapter_County extends RecyclerView.Adapter<Adapter_County.MyViewHolder>
{
    private List<County> mList;
    private Context mContext;
    private Adapter_Province.IOnClickItem mOnClickItem;
    private int rid=R.layout.item_province;


    public Adapter_County(List<County> list, Context context, Adapter_Province.IOnClickItem onClickItem)
    {
        mContext=context;
        mList=list;
        mOnClickItem=onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LSComponentsHelper.LS_Activity.inflast_Normal(mContext, rid, viewGroup);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        final County county=mList.get(i);
        myViewHolder.tv_content.setText(county.name);
        myViewHolder.tv_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mOnClickItem.OnClickCounty(county.weather_id);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
    }

    //region  viewholder
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