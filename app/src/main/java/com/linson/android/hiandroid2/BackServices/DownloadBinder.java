package com.linson.android.hiandroid2.BackServices;

import android.os.Binder;
import android.support.annotation.NonNull;

import com.linson.android.hiandroid2.Services.DownloadService;

public class DownloadBinder extends Binder
{
    private DownloadServices mDownloadService;

    public DownloadBinder(@NonNull DownloadServices downloadService)
    {
        mDownloadService=downloadService;
    }

    public boolean download(String file)
    {
       return mDownloadService.download(file);
    }

    public boolean cancel()
    {
        return mDownloadService.cancel();
    }

    public boolean pause()
    {
        return mDownloadService.pause();
    }
}