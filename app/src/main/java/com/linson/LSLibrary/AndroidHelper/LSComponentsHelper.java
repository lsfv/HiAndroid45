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
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.linson.android.hiandroid2.R;

import java.util.Date;

//类似public static class MultiMedia，把功能还是按组件分类出来。
public abstract class LSComponentsHelper
{
    public  interface Mp3Handler
    {
        public void OnchangeState(int state,int rid);
    }
    //old version's functions
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
    //old version's functions


    //global define
    public static String LOGTAG="MYCUSTOM";
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
    }

    //log
    public static class LS_Log
    {
        public static void Log_INFO(String msg)
        {
            Log.i(LOGTAG, msg);
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
            Log.i(LOGTAG, e.toString());
        }
        public static void Log_Exception(Exception e,String prefix)
        {
            Log.i(LOGTAG,prefix+"."+ e.toString());
        }
    }

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
}