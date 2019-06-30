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
import com.linson.android.hiandroid2.Weather.Model.Json_City;
import com.linson.android.hiandroid2.Weather.Model.Json_Province;

import java.util.List;

public class AdapterCity extends RecyclerView.Adapter<AdapterCity.MyViewHolder>
{
    private List<Json_City> mData;
    private Context mContext;
    private AdapterCity.IOnClickItem mIOnClickItem;

    public AdapterCity(@NonNull List<Json_City> data, @NonNull Context context, @NonNull AdapterCity.IOnClickItem onClickItem)
    {
        mData=data;
        mContext=context;
        mIOnClickItem=onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View theview=LayoutInflater.from(mContext).inflate(R.layout.item_province, viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(theview);
        return myViewHolder;
    }

    //最佳的，绑定事物的地方。控件找到了。数据又有。
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        final Json_City json_city=mData.get(i);
        myViewHolder.tv_content.setText(json_city.name);
        myViewHolder.tv_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LSComponentsHelper.LS_Log.Log_INFO(json_city.id+".city code.");
                mIOnClickItem.OnClickCity(json_city.id);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    //region viewholder
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

    //region IOnClickItem
    public static interface IOnClickItem
    {
        public void OnClickProvince(int PorvinceCode);
        public void OnClickCity(int citycode);
        public void OnClickCounty(String countyCode);
    }
    //endregion
}