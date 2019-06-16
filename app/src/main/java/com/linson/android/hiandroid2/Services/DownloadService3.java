package com.linson.android.hiandroid2.Services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.linson.android.Model.DownloadItem;
import com.linson.android.hiandroid2.Adapter.AdapterDownload;
import com.linson.android.hiandroid2.R;

import java.util.LinkedList;
import java.util.List;


//download
//一个下载管理页面。同时最多下载3个任务。
//1.list ,管理下载项目和操作。
//1.检测service是否存在，否则建立。开始链接获得binder，并获得下载任务的数据，进行页面初始化。
public class DownloadService3 extends AppCompatActivity
{
    private RecyclerView mRvDownlist;

    private void findControls()
    {
        mRvDownlist = (RecyclerView) findViewById(R.id.rv_downlist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_service3);
        findControls();

        setupRV();
    }

    private void setupRV()
    {
        List<DownloadItem> tasks=new LinkedList<>();
        DownloadItem item1=new DownloadItem();
        item1.taskStatus=DownloadItem.ENUM_DownloadStatus.prepare;
        item1.filename="file1";
        item1.progress=0;
        DownloadItem item2=new DownloadItem();
        item2.taskStatus=DownloadItem.ENUM_DownloadStatus.downloading;
        item2.filename="file2";
        item2.progress=4;
        DownloadItem item3=new DownloadItem();
        item3.taskStatus=DownloadItem.ENUM_DownloadStatus.pause;
        item3.filename="file3";
        item3.progress=5;
        DownloadItem item4=new DownloadItem();
        item4.taskStatus=DownloadItem.ENUM_DownloadStatus.cancel;
        item4.filename="file3";
        item4.progress=7;
        tasks.add(item1);
        tasks.add(item2);
        tasks.add(item3);
        tasks.add(item4);

        mRvDownlist.setAdapter(new AdapterDownload(tasks,R.layout.item_rvdownload));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRvDownlist.setLayoutManager(linearLayoutManager);
    }


}