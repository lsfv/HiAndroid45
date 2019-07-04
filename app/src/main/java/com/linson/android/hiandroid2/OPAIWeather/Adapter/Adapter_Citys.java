package com.linson.android.hiandroid2.OPAIWeather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.OPAIWeather.Model.City;
import com.linson.android.hiandroid2.OPAIWeather.Model.Province;
import com.linson.android.hiandroid2.R;

import java.util.List;

public class Adapter_Citys extends RecyclerView.Adapter<Adapter_Citys.MyViewHolder>
{
    private List<City> mList;
    private int mRid= R.layout.item_province;
    private Context mContext;
    private Adapter_Province.IOnClickItem mOnClickItem;

    public Adapter_Citys(Context context, List<City> list, Adapter_Province.IOnClickItem item)
    {
        mList=list;
        mContext=context;
        mOnClickItem=item;
    }


    @NonNull
    @Override
    public Adapter_Citys.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LSComponentsHelper.LS_Activity.inflast_Normal(mContext, mRid, viewGroup);
        Adapter_Citys.MyViewHolder myViewHolder=new Adapter_Citys.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Citys.MyViewHolder myViewHolder, int i)
    {
        final City province=mList.get(i);
        myViewHolder.tv_content.setText(province.name);
        myViewHolder.tv_content.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mOnClickItem.OnClickCity(province.id);
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
}