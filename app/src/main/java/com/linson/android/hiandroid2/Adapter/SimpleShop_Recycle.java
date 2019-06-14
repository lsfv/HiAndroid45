package com.linson.android.hiandroid2.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.DAL.SampleData;
import com.linson.android.hiandroid2.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleShop_Recycle extends RecyclerView.Adapter<SimpleShop_Recycle.ControlsHolder>
{
    private int mresourceID;
    private List<SampleData.ShopInfo> mdata;

    public SimpleShop_Recycle(int resource, @NonNull List<SampleData.ShopInfo> objects)
    {
        mresourceID=resource;
        mdata=objects;
    }

    @NonNull
    @Override
    public ControlsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(mresourceID, viewGroup,false);
        return new ControlsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ControlsHolder controlsHolder, int i)
    {
        controlsHolder.mTvName.setText(mdata.get(i).mname);
        controlsHolder.mTvScore.setText(mdata.get(i).msocre+"");
    }

    @Override
    public int getItemCount()
    {
        return mdata.size();
    }

    public static class ControlsHolder extends RecyclerView.ViewHolder
    {
        public TextView mTvName;
        public TextView mTvScore;
        public ControlsHolder(@NonNull View itemView)
        {
            super(itemView);
            mTvName=(TextView) itemView.findViewById(R.id.tv_name);
            mTvScore=(TextView) itemView.findViewById(R.id.tv_score);
        }
    }
}