package com.linson.android.hiandroid2.Weather.Control.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.Weather.Model.Json_City;
import com.linson.android.hiandroid2.Weather.Model.Json_County;

import java.util.List;

public class AdapterCounty extends RecyclerView.Adapter<AdapterCounty.MyViewHolder>
{
    private List<Json_County> mData;
    private Context mContext;
    private AdapterCity.IOnClickItem mIOnClickItem;

    public AdapterCounty(List<Json_County> data,Context context,AdapterCity.IOnClickItem onClickItem)
    {
        mData=data;
        mContext=context;
        mIOnClickItem=onClickItem;
    }

    @NonNull @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View theview=LayoutInflater.from(mContext).inflate(R.layout.item_province, viewGroup,false);
        AdapterCounty.MyViewHolder myViewHolder=new AdapterCounty.MyViewHolder(theview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        final Json_County json_county=mData.get(i);
        myViewHolder.tv_content.setText(json_county.name);
        myViewHolder.tv_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mIOnClickItem.OnClickCounty(json_county.weather_id);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    //region viewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder
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