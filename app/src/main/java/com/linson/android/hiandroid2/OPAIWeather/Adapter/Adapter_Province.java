package com.linson.android.hiandroid2.OPAIWeather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.OPAIWeather.Model.Province;
import com.linson.android.hiandroid2.R;

import java.util.List;

public class Adapter_Province extends RecyclerView.Adapter<Adapter_Province.MyViewHolder>
{

    private List<Province> mList;
    private int mRid= R.layout.item_province;
    private Context mContext;
    private IOnClickItem mOnClickItem;

    public Adapter_Province(Context context, List<Province> list, IOnClickItem item)
    {
        mList=list;
        mContext=context;
        mOnClickItem=item;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LSComponentsHelper.LS_Activity.inflast_Normal(mContext, mRid, viewGroup);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        final Province province=mList.get(i);
        myViewHolder.tv_content.setText(province.name);
        myViewHolder.tv_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mOnClickItem.OnClickProvince(province.id);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mList.size();
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


    //region interface
    public static interface IOnClickItem
    {
        public void OnClickProvince(int PorvinceCode);
        public void OnClickCity(int citycode);
        public void OnClickCounty(String countyCode);
    }
    //endregion
}