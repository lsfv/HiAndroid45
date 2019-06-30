package com.linson.android.hiandroid2.Weather.Control;

import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.BackServices.CaculateServices;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.Weather.Control.Adapter.AdapterCity;
import com.linson.android.hiandroid2.Weather.Control.Adapter.AdapterCounty;
import com.linson.android.hiandroid2.Weather.Control.Adapter.AdapterProvince;
import com.linson.android.hiandroid2.Weather.DB.WeatherDB;
import com.linson.android.hiandroid2.Weather.Model.Json_City;
import com.linson.android.hiandroid2.Weather.Model.Json_County;
import com.linson.android.hiandroid2.Weather.Model.Json_Province;
import java.util.List;

/**
 * 1 list.more adapters.
keyvalue:choosetype.  part:1.list 2.menu
initialize variable-> call function.  .  change keyvalue->call function.
 */
public class Main extends AppCompatActivity implements View.OnClickListener
{
    private ConstraintLayout mViewBg;
    private Button mButton12;
    private RecyclerView mRv;

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mViewBg = (ConstraintLayout) findViewById(R.id.view_bg);
        mButton12 = (Button) findViewById(R.id.button12);
        mRv = (RecyclerView) findViewById(R.id.rv_);
        //set event handler
        mButton12.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.button12: {
                back();
                break;
            }
            default: {
                break;
            }
        }
    }
    //endregion

    //member variable
    private int page_Current=1;//1 province .2 city. 3county.
    private int province_choose=0;
    private int city_choose=0;
    private WeatherDB mWeatherDB;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findControls();
        page_Current=1;
        mWeatherDB=new WeatherDB(this, WeatherDB.db_weatherName ,null , WeatherDB.db_weatherversion);

        inFlatListAndMenu();
    }

    private void inFlatListAndMenu()
    {
        mButton12.setVisibility(View.VISIBLE);
        if(page_Current==1)
        {
            loadProvince();
            mButton12.setVisibility(View.INVISIBLE);
        }
        else if(page_Current==2)
        {
            loadCity();
        }
        else if(page_Current==3)
        {
            loadCounty();
        }
    }

    private void loadCounty()
    {
        List<Json_County> countyList=mWeatherDB.getAllCounty(city_choose);
        AdapterCounty adapterCounty=new AdapterCounty(countyList, this,new OnClickItemHandler());
        mRv.setAdapter(adapterCounty);
    }

    private void loadProvince()
    {
        List<Json_Province> provinces=mWeatherDB.getAllProvince();
        AdapterProvince adapterProvince=new AdapterProvince(provinces, this,new OnClickItemHandler());
        mRv.setAdapter(adapterProvince);
    }

    private void loadCity()
    {
        List<Json_City> provinces=mWeatherDB.getAllCitys(province_choose);
        AdapterCity adapterProvince=new AdapterCity(provinces, this,new OnClickItemHandler());
        mRv.setAdapter(adapterProvince);
    }

    private void back()
    {
        page_Current--;
        inFlatListAndMenu();
    }

    //region City delegate 's implement.
    private class OnClickItemHandler implements AdapterCity.IOnClickItem
    {
        @Override
        public void OnClickProvince(int PorvinceCode)
        {
            LSComponentsHelper.LS_Log.Log_INFO("click p code"+PorvinceCode);
            Main.this.page_Current++;
            Main.this.province_choose=PorvinceCode;
            inFlatListAndMenu();
        }

        @Override
        public void OnClickCity(int citycode)
        {
            LSComponentsHelper.LS_Log.Log_INFO("click c code"+citycode);
            Main.this.page_Current++;
            Main.this.city_choose=citycode;
            inFlatListAndMenu();
        }

        @Override
        public void OnClickCounty(String countyCode)
        {
            LSComponentsHelper.LS_Log.Log_INFO("weather code"+countyCode);
        }
    }
    //endregion
}