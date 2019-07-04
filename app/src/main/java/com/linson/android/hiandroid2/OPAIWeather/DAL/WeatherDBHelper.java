package com.linson.android.hiandroid2.OPAIWeather.DAL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDBHelper extends SQLiteOpenHelper
{
    public static final String db_weatherName=DataBaseAccess.db_weatherName;
    public static final int db_weatherversion=DataBaseAccess.db_weatherversion;

    public WeatherDBHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}