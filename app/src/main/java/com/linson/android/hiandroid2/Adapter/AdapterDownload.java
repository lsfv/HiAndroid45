package com.linson.android.hiandroid2.Adapter;

import android.graphics.ColorSpace;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linson.android.Model.DownloadItem;
import com.linson.android.hiandroid2.R;

import java.util.List;

public class AdapterDownload extends RecyclerView.Adapter
{
    private List<DownloadItem> mData;
    private int mlayoutid;
    public AdapterDownload(List<DownloadItem> tasks, @LayoutRes int layoutid)
    {
        mData=tasks;
        mlayoutid=layoutid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        //load xml. and intilization viewholder
        View itemView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rvdownload, viewGroup,false);
        return new vh(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i)
    {
        DownloadItem downloadItem=mData.get(i);
        if(viewHolder instanceof vh)
        {
            vh thevh=(vh)viewHolder;
            thevh.mTvName.setText(downloadItem.filename);
            thevh.mProgressBar2.setProgress(downloadItem.progress);
            if(downloadItem.taskStatus==DownloadItem.ENUM_DownloadStatus.downloading) {
                thevh.mBtnPause.setText("Continue");
            }
            else {
                thevh.mBtnPause.setText("pause");
            }
        }
    }

    public class vh extends RecyclerView.ViewHolder
    {
        public Button mBtnPause;
        public TextView mTvName;
        public ProgressBar mProgressBar2;
        public Button mBtnCancel;

        public void findControls(@NonNull View itemView)
        {
            mBtnPause = (Button)  itemView.findViewById(R.id.btn_pause);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mProgressBar2 = (ProgressBar) itemView.findViewById(R.id.progressBar2);
            mBtnCancel = (Button) itemView.findViewById(R.id.btn_cancel);
        }

        public vh(@NonNull View itemView)
        {
            super(itemView);
            findControls(itemView);
        }
    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }
}