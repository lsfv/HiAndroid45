package com.linson.android.Model;

public class DownloadItem
{
    public long itemid;
    public String filename;
    public int progress;
    public ENUM_DownloadStatus taskStatus;

    public enum ENUM_DownloadStatus
    {
        prepare,
        downloading,
        pause,
        cancel,
        ok,
        error,
        cancela
    }
}