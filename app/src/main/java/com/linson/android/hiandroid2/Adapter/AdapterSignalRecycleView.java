package com.linson.android.hiandroid2.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.linson.android.hiandroid2.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterSignalRecycleView extends RecyclerView.Adapter<AdapterSignalRecycleView.MyViewHolder>
{
    private Context mContext;
    private int mItemXmlID=R.layout.item_img;
    private List<Drawable> mDrawables;

    public AdapterSignalRecycleView(@NotNull Context context,@NotNull List<Drawable> drawables)
    {
        mContext=context;
        mDrawables=drawables;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view=LayoutInflater.from(mContext).inflate(mItemXmlID, viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        myViewHolder.mImageView.setImageDrawable(mDrawables.get(i));
    }

    @Override
    public int getItemCount()
    {
        return mDrawables.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView mImageView;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            mImageView=itemView.findViewById(R.id.iv_item);
        }
    }

}