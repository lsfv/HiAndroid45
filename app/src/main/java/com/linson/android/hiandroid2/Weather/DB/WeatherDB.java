package com.linson.android.hiandroid2.Weather.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.Network.LSOKHttp;
import com.linson.android.hiandroid2.Weather.Model.Json_City;
import com.linson.android.hiandroid2.Weather.Model.Json_County;
import com.linson.android.hiandroid2.Weather.Model.Json_Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherDB extends SQLiteOpenHelper
{
    public static final String db_weatherName="weatherok88";
    public static final int db_weatherversion=1;


    private boolean mIsOk=true;
    private String mErrorMsg="";
    private String mUrlChina="http://guolin.tech/api/china";
    public WeatherDB(@android.support.annotation.Nullable Context context, @android.support.annotation.Nullable String name, @android.support.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(final SQLiteDatabase db)
    {
        //每个表的id 都是单值主键，也是谜一般的数据结构。 无法 看出表中数据的顺序.既然是单键。为什么http查询又要组合。真迷。。。
        mIsOk= createDB(db);

        LSComponentsHelper.LS_Log.Log_INFO("createdb  :"+mIsOk+mErrorMsg);
        if(mIsOk)
        {
            //insert province
            LSOKHttp.get(mUrlChina, new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {
                    mIsOk=false;
                    mErrorMsg="json error:"+e.toString();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    String jsonstr = response.body().string();
                    Type tempType=new TypeToken<List<Json_Province>>(){}.getType();
                    Gson gson=new GsonBuilder().create();
                    List<Json_Province> provinceList=gson.fromJson(jsonstr,tempType);

                    try
                    {
                        for (Json_Province item : provinceList)
                        {
                            String tempSQL = "insert into 'Province' (ProvinceName,ProvinceCode) values ('" + item.name + "'," + item.id + ") ";
                            db.execSQL(tempSQL);
                        }
                        Thread.sleep(3000);
                    } catch (Exception e)
                    {
                        LSComponentsHelper.LS_Log.Log_Exception(e);
                    }
                }
            });

            //insert city
            for(int i=1;i<=34;i++)
            {

                final String curprivonce = i+"";
                String urlcity = "http://guolin.tech/api/china/" + curprivonce;

                try
                {
                    Thread.sleep(500);
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }

                LSOKHttp.get(urlcity, new Callback()
                {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        mIsOk = false;
                        mErrorMsg = "json error:" + e.toString();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException
                    {
                        String jsonstr = response.body().string();
                        Type tempType = new TypeToken<List<Json_Province>>()
                        {
                        }.getType();
                        Gson gson = new GsonBuilder().create();
                        List<Json_Province> provinceList = gson.fromJson(jsonstr, tempType);

                        try
                        {
                            for (Json_Province item : provinceList)
                            {
                                String tempSQL = "insert into 'City' (CityName,CityCode,ProvinceID) values ('" + item.name + "'," + item.id + "," + curprivonce + ") ";
                                db.execSQL(tempSQL);
                                insertCounty(db,curprivonce,item.id+"");

                            }
                        } catch (Exception e)
                        {
                            LSComponentsHelper.LS_Log.Log_Exception(e);
                        }
                    }
                });
            }



        }
    }

    private void insertCounty(final SQLiteDatabase db,String currentp,String currentc) throws IOException
    {
        //insert county
        final String currentprivonce=currentp;
        final String currentcity=currentc;
        String urlcontury="http://guolin.tech/api/china/"+currentprivonce+"/"+currentcity;
        try
        {
            Thread.sleep(500);
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
        LSOKHttp.get(urlcontury, new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                mIsOk=false;
                mErrorMsg="json error:"+e.toString();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String jsonstr = response.body().string();
                Type tempType=new TypeToken<List<Json_County>>(){}.getType();
                Gson gson=new GsonBuilder().create();
                List<Json_County> provinceList=gson.fromJson(jsonstr,tempType);

                try
                {
                    for (Json_County item : provinceList)
                    {
                        String tempSQL = "insert into 'County' (CountyName,WeatherID,CityID,CountyCode) values ('" + item.name + "','" + item.weather_id + "',"+currentcity+","+item.id+")";
                        db.execSQL(tempSQL);
                    }
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
        });
    }

    private boolean createDB(SQLiteDatabase db)
    {
        boolean isok=true;
        String sqlone="create table 'Province' ('ID' INTEGER PRIMARY KEY AUTOINCREMENT,'ProvinceName' TEXT NOT NULL,'ProvinceCode' INTEGER NOT NULL)";
        String sql2="create table 'City' ('ID' INTEGER PRIMARY KEY AUTOINCREMENT,'CityName' TEXT NOT NULL ,'CityCode' TEXT NOT NULL ,'ProvinceID' INTEGER NOT NULL)";
        String sql3="create table 'County' ('ID' INTEGER PRIMARY KEY AUTOINCREMENT,'CountyName' TEXT NOT NULL,'WeatherID' TEXT NOT NULL,'CityID' INTEGER NOT NULL,'CountyCode'  INTEGER NOT NULL)";

        db.beginTransaction();
        try
        {
            db.execSQL(sqlone);
            db.execSQL(sql2);
            db.execSQL(sql3);
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            isok=false;
            mErrorMsg="db error:"+e.toString();
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
        finally
        {
            db.endTransaction();
        }
        return isok;
    }


    public boolean getisok()
    {
        return mIsOk;
    }

    public String getErrorMsg()
    {
        return mErrorMsg;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public List<Json_Province> getAllProvince()
    {
        List<Json_Province> provinces=new LinkedList<>();
        Cursor cursor= this.getReadableDatabase().rawQuery("select ProvinceCode,ProvinceName from province", null);
        cursor.moveToFirst();
        int size=cursor.getCount();
        for(int i=0;i<size;i++)
        {
            Json_Province json_province=new Json_Province();
            json_province.id=cursor.getInt(0);
            json_province.name=cursor.getString(1);
            provinces.add(json_province);
            cursor.moveToNext();
        }
        return provinces;
    }


    public List<Json_City> getAllCitys(int provincecode)
    {
        List<Json_City> res=new ArrayList<>();
        Cursor cursor= getReadableDatabase().rawQuery("select CityCode,CityName from City where ProvinceID="+provincecode, null);
        cursor.moveToFirst();
        int size=cursor.getCount();
        for(int i=0;i<size;i++)
        {
            Json_City json_city=new Json_City();
            json_city.id=cursor.getInt(0);
            json_city.name=cursor.getString(1);
            res.add(json_city);
            cursor.moveToNext();
        }
        return res;
    }

    public List<Json_County> getAllCounty(int citycode)
    {
        List<Json_County> res=new ArrayList<>();
        Cursor cursor= getReadableDatabase().rawQuery("select CountyName,WeatherID from County where CityID="+citycode, null);
        cursor.moveToFirst();
        int size=cursor.getCount();
        for(int i=0;i<size;i++)
        {
            Json_County json_county=new Json_County();
            json_county.name=cursor.getString(0);
            json_county.weather_id=cursor.getString(1);
            res.add(json_county);
            cursor.moveToNext();
        }
        return res;
    }


}