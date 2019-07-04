package com.linson.LSLibrary.AndroidHelper;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linson.android.hiandroid2.R;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.zip.Inflater;

//类似public static class MultiMedia，把功能还是按组件分类出来。
public abstract class LSComponentsHelper
{
    public  interface Mp3Handler
    {
        public void OnchangeState(int state,int rid);
    }
    //region old version's functions
    public static void Log_INFO(String msg)
    {
        LS_Log.Log_INFO(msg);
    }
    public static void Log_DEBUG(String msg)
    {
        LS_Log.Log_DEBUG(msg);
    }
    public static void Log_Error(String msg)
    {
        LS_Log.Log_Error(msg);
    }
    public static void Log_Exception(Exception e)
    {
        LS_Log.Log_Exception(e);
    }
    public static void startActivity(Context context, Class<?> cls)
    {
        LS_Activity.startActivity(context, cls);
    }
    //endregion old version's functions


    //global define
    public static String LOGTAG="MYCUSTOM~!@";
    public interface VoidHandler
    {
        public void doti();
    }

    //other helper
    public static class LS_Other
    {
        public static void checkPermission(Activity context, final String permission,VoidHandler voidHandler)
        {
            boolean hasPermission=false;
            hasPermission=ContextCompat.checkSelfPermission(context, permission)==PackageManager.PERMISSION_GRANTED;
            if(hasPermission)
            {
                voidHandler.doti();
            }
            else
            {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    //multi helper
    public static class MultiMedia
    {
        public static Bitmap getBitmap(Activity activity, Intent intent)
        {
            Bitmap bitmap=null;
            String imagePath="";
            Uri imageUri=intent.getData();
            if(imagePath!=null)
            {
                if(Build.VERSION.SDK_INT>=19)
                {
                    if(DocumentsContract.isDocumentUri(activity, imageUri))
                    {
                        String docid=DocumentsContract.getDocumentId(imageUri);
                        if("com.android.providers.media.documents".equalsIgnoreCase(imageUri.getAuthority()))
                        {
                            String id=docid.split(":")[1];
                            String selection=MediaStore.Images.Media._ID+"="+id;
                            imagePath=getImagePaht(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, activity);
                        }
                    }
                    else if("content".equalsIgnoreCase(imageUri.getScheme()))
                    {
                        imagePath=getImagePaht(imageUri, null, activity);
                    }
                    else if("file".equalsIgnoreCase(imageUri.getScheme()))
                    {
                        imagePath=imageUri.getPath();
                    }
                }
                else
                {
                    imagePath = getImagePaht(imageUri, null, activity);
                }
            }

            try
            {
                bitmap = BitmapFactory.decodeFile(imagePath);
            }
            catch (Exception e)
            {
                LSComponentsHelper.Log_Exception(e);
            }
            return bitmap;
        }

        private static String getImagePaht(Uri uri,String select,Activity activity)
        {
            String path=null;
            Cursor cursor=activity.getContentResolver().query(uri, null, select, null,null);
            if(cursor!=null)
            {
                if(cursor.moveToFirst())
                {
                    path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
                cursor.close();
            }
            return path;
        }
    }

    //activity helper
    public static class LS_Activity
    {
        public static void startActivity(Context context, Class<?> cls)
        {
            Intent myIntent=new Intent(context,cls);
            context.startActivity(myIntent);
        }

        public static View inflast_Normal(Context context,int rid,ViewGroup viewGroup)
        {
            return LayoutInflater.from(context).inflate(rid, viewGroup,false);
        }

        public static View inflast_WrapFather(Context context,int rid,ViewGroup viewGroup)
        {
            return LayoutInflater.from(context).inflate(rid, viewGroup,true);
        }

        public static View inflast_ClearArrer(Context context,int rid)
        {
            return LayoutInflater.from(context).inflate(rid, null,false);
        }
    }

    //log
    public static class LS_Log
    {
        public static void Log_INFO(String msg)
        {
            Log.i(LOGTAG, getAutoJumpLogInfos()+" : "+msg);
        }
        public static void Log_DEBUG(String msg)
        {
            Log.d(LOGTAG, msg);
        }
        public static void Log_Error(String msg)
        {
            Log.e(LOGTAG, msg);
        }
        public static void Log_Exception(Exception e)
        {
            Log.i(LOGTAG, getAutoJumpLogInfos()+" : "+e.toString()+".\r\n");
            for(StackTraceElement item : e.getStackTrace())
            {
                Log.i(LOGTAG, item.toString()+".\r\n");
            }
        }
        public static void Log_Exception(Exception e,String prefix) {
            Log.i(LOGTAG,prefix+"."+ e.toString()+"."+e.getStackTrace()[0].getClassName()+"."+e.getStackTrace()[0].getLineNumber());
        }

        private static String getAutoJumpLogInfos() {
            String[] infos = new String[]{"", "", ""};
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (elements.length < 5) {
                return "";
            } else {
                infos[0] = elements[4].getClassName().substring(
                        elements[4].getClassName().lastIndexOf(".") + 1);
                infos[1] = " ["+elements[4].getMethodName() + "].";
                infos[2] = " Line:" +elements[4].getLineNumber() + "";
                return infos[0]+infos[1]+infos[2];
            }
        }
    }

    //region notification
    public static class LS_Notification
    {
        public static NotificationManager getManager(Context context)
        {
            return (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        public static Notification getNotification(Context context, String title, @Nullable Integer progress, int icon,String content,Class<?> activityClass)
        {
            Notification.Builder builder=new Notification.Builder(context);
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setSmallIcon(icon);
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon));

            if(progress!=null && progress>=0)
            {
                builder.setProgress(100, progress, false);
            }

            if(activityClass!=null)
            {
                Intent intent = new Intent(context, activityClass);
                PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, intent, 0);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
            }

            return builder.build();
        }
    }
    //endregion

    //region AsyncTask
    public static abstract class LS_AsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>
    {
        public SafeControlAction mControlAction=new SafeControlAction();


        public static class SafeControlAction
        {
            private boolean bPause=false;
            private boolean bCancel=false;
            private int otherAction=-1;

            public SafeControlAction()
            {
                bPause=false;
                bCancel=false;
                otherAction=-1;
            }

            private ReentrantLock mReentrantLock=new ReentrantLock();

            public boolean getPauseAndRestoreIfTrue()
            {
                boolean res=false;
                mReentrantLock.lock();
                try
                {
                    if(bPause)
                    {
                        res=true;
                        bPause=false;
                    }
                }
                finally { mReentrantLock.unlock(); }
                return res;
            }

            public void setPause()
            {
                mReentrantLock.lock();
                try { bPause=true; }
                finally { mReentrantLock.unlock(); }
            }

            public boolean getCancelAndRestoreIfTrue()
            {
                boolean res=false;
                mReentrantLock.lock();
                try
                {
                    if(bCancel)
                    {
                        res=true;
                        bCancel=false;
                    }
                }
                finally { mReentrantLock.unlock(); }
                return res;
            }

            public void setCancel()
            {
                LS_Log.Log_INFO("safesetcancel");
                mReentrantLock.lock();
                try { bCancel=true;}
                finally { mReentrantLock.unlock();}
            }

            public int getOtherActionAndRestoreIFNotNagative1()
            {
                int res=-1;
                mReentrantLock.lock();
                try {
                    if(otherAction!=-1)
                    {
                        res = otherAction;
                        otherAction=-1;
                    }
                }
                finally { mReentrantLock.unlock(); }
                return res;
            }

            public void setOtherAction(int vv)
            {
                mReentrantLock.lock();
                try {otherAction=vv;}
                finally { mReentrantLock.unlock();}
            }

        }


    }

    //endregion
}