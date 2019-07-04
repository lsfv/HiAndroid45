package com.linson.android.hiandroid2.OPAIWeather.DAL;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.Network.LSOKHttp;
import com.linson.android.hiandroid2.OPAIWeather.Model.City;
import com.linson.android.hiandroid2.OPAIWeather.Model.County;
import com.linson.android.hiandroid2.OPAIWeather.Model.Province;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


//成功必须回调。让程序跳转页面。
//错误必须，删除数据库，并回调。
class DataBaseAccess extends SQLiteOpenHelper
{
    public boolean mIsInvokeCreate=false;
    public static final String db_weatherName="OPWEATHER41";
    public static final int db_weatherversion=1;
    private static String mUrlChina="http://guolin.tech/api/china";
    private IonCreateDB mIonCreateDB;
    private Context mContext;

    public void setIonCreateDB(IonCreateDB ionCreateDB)
    {
        mIonCreateDB = ionCreateDB;
    }

    public DataBaseAccess(@android.support.annotation.Nullable Context context, @android.support.annotation.Nullable String name, @android.support.annotation.Nullable SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        mIsInvokeCreate=true;
        boolean ISOk=createDB(db);
        if(ISOk) {
            inflateData();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    private boolean createDB(SQLiteDatabase db)
    {
        boolean isok=true;
        String sqlone="create table 'Province' ('ID' INTEGER PRIMARY KEY AUTOINCREMENT,'ProvinceName' TEXT NOT NULL,'ProvinceCode' INTEGER NOT NULL)";
        String sql2="create table 'City' ('ID' INTEGER PRIMARY KEY AUTOINCREMENT,'CityName' TEXT NOT NULL ,'CityCode' TEXT NOT NULL ,'ProvinceCode' INTEGER NOT NULL)";
        String sql3="create table 'County' ('ID' INTEGER PRIMARY KEY AUTOINCREMENT,'CountyName' TEXT NOT NULL,'WeatherID' TEXT NOT NULL,'CityCode' INTEGER NOT NULL)";

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
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
        finally
        {
            db.endTransaction();
        }
        return isok;
    }

    //我期望是一个同步的访问。可以加上超时。理想是单后台线程并有回传功能。等等，这不就是asynTask?已经做好了模板啊。
    private void inflateData()
    {
        final FillDataTask fillDataTask=new FillDataTask(mIonCreateDB);
        final AsyncTask tt= fillDataTask.execute();//最长不超过60s.

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                if(tt.getStatus()!= AsyncTask.Status.FINISHED)
                {
                    LSComponentsHelper.LS_Log.Log_INFO("time");
                    fillDataTask.needCancel=true;
                }
                timer.cancel();
            }
        }, 60000 , 9999);
    }

    //region asyncTask
    //1.province.insert into db.2each province 's citys.3.each citys's county. ok. 20,50,100.
    private class FillDataTask extends AsyncTask<Void,Message,Void>
    {
        private IonCreateDB mIonCreateDB;
        public boolean needCancel=false;

        public FillDataTask(IonCreateDB ionCreateDB)
        {
            mIonCreateDB=ionCreateDB;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            boolean provinceOK = false;
            boolean cityok = false;
            boolean countyok = false;

            provinceOK =doProvince();
            if (provinceOK)
            {
                sendMsg(1, 20);
                cityok=doCitys();
                if (cityok)
                {
                    sendMsg(1, 50);
                    countyok=doCunty();
                    if (countyok)
                    {
                        sendMsg(1, 100);
                        sendMsg(2, 1);
                    }
                    else
                    {
                        errorCreatedb();
                    }
                }
                else
                {
                    errorCreatedb();
                }
            }
            else
            {
                errorCreatedb();
            }
            return null;
        }


        private void errorCreatedb()
        {
            sendMsg(2, -1);
        }


        //deal with province. 注意getSync是一个同步调用。
        // 会阻塞在http请求，获得了数据并执行回调。 才会再执行下面语句。所以才可以想这样按顺序处理数据。否则是不行的。
        private boolean doProvince()
        {
            final boolean[] res = {true};
            LSOKHttp.getSync(DataBaseAccess.mUrlChina, new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {
                    res[0] =false;
                    LSComponentsHelper.Log_Exception(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    String jsonStr=response.body().string();
                    doProvince(jsonStr);
                }
            });
            return res[0];
        }

        protected boolean doCitys()
        {
            final boolean[] res = {true};
            List<Province> provinces=getAllProvince();
            for(final Province item :provinces)
            {
                String tempUrl=DataBaseAccess.mUrlChina+"/"+item.id;
                LSOKHttp.getSync(tempUrl, new Callback()
                {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        res[0]=false;
                        LSComponentsHelper.Log_Exception(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException
                    {
                        String jsonStr=response.body().string();
                        doCitys(jsonStr,item.id);
                    }
                });
            }
            return res[0];
        }


        protected boolean doCunty()
        {
            final boolean[] res = {true};
            List<City> cities=getAllCitys();
            for(final City item:cities)
            {
                if(needCancel)
                {
                    res[0]=false;
                    break;
                }

                String tempUrl = DataBaseAccess.mUrlChina + "/xxx" + "/" + item.id;//crazzy url .anystring is ok when replace xxx. /xxx/citycode
                LSOKHttp.getSync(tempUrl, new Callback()
                {
                    @Override
                    public void onFailure(Call call, IOException e)
                    {
                        res[0] = true;
                        sendMsg(2, -1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException
                    {
                        String jsonStr = response.body().string();
                        doCounty(jsonStr,item.id);
                    }
                });
            }
            return res[0];
        }


        private void sendMsg(int what,int arg1)
        {
            Message msssage=new Message();
            msssage.what=what;
            msssage.arg1=arg1;
            publishProgress(msssage);
        }

        @Override
        protected void onProgressUpdate(Message... values)
        {
            if(mIonCreateDB!=null && values!=null && values.length>0)
            {
                mIonCreateDB.OnCreateDB(values[0]);
            }
        }

        //region function for create
        private void doProvince(String jsonstr)
        {
            Gson gson=new GsonBuilder().create();
            Type theTyep=new TypeToken<List<Province>>(){}.getType();

            List<Province> provinceList=gson.fromJson(jsonstr,theTyep);
            for(Province item :provinceList)
            {
                String tempSQL = "insert into 'Province' (ProvinceName,ProvinceCode) values ('" + item.name + "'," + item.id + ") ";
                getWritableDatabase().execSQL(tempSQL);
            }
            LSComponentsHelper.LS_Log.Log_INFO("insert into db province");
        }

        private void doCitys(String jsonstr,int provinceCode)
        {
            Gson gson=new GsonBuilder().create();
            Type theTyep=new TypeToken<List<City>>(){}.getType();

            List<City> provinceList=gson.fromJson(jsonstr,theTyep);
            for(City item :provinceList)
            {
                String tempSQL = "insert into 'City' (CityName,CityCode,ProvinceCode) values ('" + item.name + "'," + item.id + ","+provinceCode+") ";
                getWritableDatabase().execSQL(tempSQL);
            }
            //LSComponentsHelper.LS_Log.Log_INFO("insert into db citys");
        }

        private void doCounty(String jsonstr,int citycode)
        {
            Gson gson=new GsonBuilder().create();
            Type theTyep=new TypeToken<List<County>>(){}.getType();

            List<County> provinceList=gson.fromJson(jsonstr,theTyep);
            for(County item :provinceList)
            {
                try
                {
                    String tempSQL = "insert into 'County' (CountyName,WeatherID,CityCode) values ('" + item.name + "','" + item.weather_id + "'," + citycode + ")";
                    getWritableDatabase().execSQL(tempSQL);
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
            }
            //LSComponentsHelper.LS_Log.Log_INFO("insert into db county");
        }

        private List<Province> getAllProvince()
        {
            List<Province> provinces=new LinkedList<>();
            Cursor cursor= getReadableDatabase().rawQuery("select ProvinceCode,ProvinceName from province", null);
            cursor.moveToFirst();
            int size=cursor.getCount();
            for(int i=0;i<size;i++)
            {
                Province json_province=new Province();
                json_province.id=cursor.getInt(0);
                json_province.name=cursor.getString(1);
                provinces.add(json_province);
                cursor.moveToNext();
            }
            return provinces;
        }

        private List<City> getAllCitys()
        {
            List<City> res=new ArrayList<>();
            Cursor cursor= getReadableDatabase().rawQuery("select CityCode,CityName from City", null);
            cursor.moveToFirst();
            int size=cursor.getCount();
            for(int i=0;i<size;i++)
            {
                City json_city=new City();
                json_city.id=cursor.getInt(0);
                json_city.name=cursor.getString(1);
                res.add(json_city);
                cursor.moveToNext();
            }
            return res;
        }
        //endregion
    }
    //endregion

    //region      OnCreateDB
    public interface IonCreateDB
    {
        public void OnCreateDB(Message progress);
    }
    //endregion
}