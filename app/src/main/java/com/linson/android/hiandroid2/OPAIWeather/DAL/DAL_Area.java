package com.linson.android.hiandroid2.OPAIWeather.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.OPAIWeather.Model.City;
import com.linson.android.hiandroid2.OPAIWeather.Model.County;
import com.linson.android.hiandroid2.OPAIWeather.Model.Province;
import com.linson.android.hiandroid2.Weather.Model.Json_City;
import com.linson.android.hiandroid2.Weather.Model.Json_County;
import com.linson.android.hiandroid2.Weather.Model.Json_Province;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DAL_Area
{
    private Context mContext;
    private WeatherDBHelper mWeatherDBHelper;

    public DAL_Area(@NotNull Context context)
    {
        mContext=context;
        mWeatherDBHelper=new WeatherDBHelper(mContext, WeatherDBHelper.db_weatherName,null ,WeatherDBHelper.db_weatherversion);
    }


    public List<Province> getAllProvince()
    {
        List<Province> data=new ArrayList<>();

        SQLiteDatabase database= mWeatherDBHelper.getReadableDatabase();
        Cursor cursor= database.rawQuery("select ProvinceCode,ProvinceName from province", null);
        cursor.moveToFirst();
        int size=cursor.getCount();
        for(int i=0;i<size;i++)
        {
            Province province=new Province();
            province.id=cursor.getInt(0);
            province.name=cursor.getString(1);
            data.add(province);
            cursor.moveToNext();
        }

        return data;
    }

    public List<City> getAllCitys(int provincecode)
    {
        List<City> res=new ArrayList<>();
        Cursor cursor= mWeatherDBHelper.getReadableDatabase().rawQuery("select CityCode,CityName from City where ProvinceCode="+provincecode, null);
        cursor.moveToFirst();
        int size=cursor.getCount();
        for(int i=0;i<size;i++)
        {
            City city=new City();
            city.id=cursor.getInt(0);
            city.name=cursor.getString(1);
            res.add(city);
            cursor.moveToNext();
        }
        return res;
    }

    public List<County> getAllCounty(int citycode)
    {
        List<County> res=new ArrayList<>();
        Cursor cursor= mWeatherDBHelper.getReadableDatabase().rawQuery("select CountyName,WeatherID from County where CityCode="+citycode, null);
        cursor.moveToFirst();
        int size=cursor.getCount();
        for(int i=0;i<size;i++)
        {
            County json_county=new County();
            json_county.name=cursor.getString(0);
            json_county.weather_id=cursor.getString(1);
            res.add(json_county);
            cursor.moveToNext();
        }
        return res;
    }

}