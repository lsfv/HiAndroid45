package com.linson.android.Model;

import com.linson.android.hiandroid2.BackServices.Binder_Download;

public class DownloadItem
{
    public String filename;
    public int progress;
    public ENUM_DownloadStatus taskStatus;

    public enum ENUM_DownloadStatus
    {
        prepare,
        downloading,
        pause,
        cancel
    }
}