package com.linson.android.hiandroid2.OPAIWeather.Control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.OPAIWeather.Adapter.Adapter_Citys;
import com.linson.android.hiandroid2.OPAIWeather.Adapter.Adapter_County;
import com.linson.android.hiandroid2.OPAIWeather.Adapter.Adapter_Province;
import com.linson.android.hiandroid2.OPAIWeather.DAL.DAL_Area;
import com.linson.android.hiandroid2.OPAIWeather.Model.City;
import com.linson.android.hiandroid2.OPAIWeather.Model.County;
import com.linson.android.hiandroid2.OPAIWeather.Model.Province;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.Weather.Control.Adapter.AdapterCounty;

import java.util.List;

public class ChooseArea extends Fragment implements View.OnClickListener
{
    private ConstraintLayout mFragemntLayout1;
    private ConstraintLayout mFragemntLayout2;
    private TextView mTvTitle;
    private Button mBtnBack;
    private RecyclerView mRvArea;
    private DAL_Area dal_area;


    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls(View view)
    {   //findControls
        mFragemntLayout1 = (ConstraintLayout) view.findViewById(R.id.fragemnt_layout_1);
        mFragemntLayout2 = (ConstraintLayout) view.findViewById(R.id.fragemnt_layout_2);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mBtnBack = (Button) view.findViewById(R.id.btn_back);
        mRvArea = (RecyclerView) view.findViewById(R.id.rv_area);

        //set event handler
        mBtnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_back:
            {
                back();
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //member variable
    private enum enum_page
    {
        page_province,
        page_city,
        page_county
    }
    private enum_page mCurrentPage=enum_page.page_province;
    private int mLastProvinceCode=0;
    private int mLastCityCode=0;
    private String mLastCounty="";
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = LSComponentsHelper.LS_Activity.inflast_Normal(this.getContext(), R.layout.fragment_choosearea, container);
        dal_area=new DAL_Area(this.getContext());
        findControls(view);
        loadProvince();
        return view;
    }

    private void back()
    {
        if(mCurrentPage==enum_page.page_county)
        {
            loadCitys(mLastProvinceCode);
        }
        else if(mCurrentPage==enum_page.page_city)
        {
            loadProvince();
        }
    }

    private void loadProvince()
    {
        try
        {
            mBtnBack.setVisibility(View.INVISIBLE);
            mTvTitle.setText("china");
            mCurrentPage = enum_page.page_province;
            List<Province> data = dal_area.getAllProvince();
            LSComponentsHelper.LS_Log.Log_INFO("pp:"+data.size());
            mRvArea.setAdapter(new Adapter_Province(getContext(), data, new OnClickItemHander()));
        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
    }
    private void loadCitys(int PorvinceCode)
    {
        mBtnBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(PorvinceCode+"");
        mCurrentPage=enum_page.page_city;
        mLastProvinceCode=PorvinceCode;
        mLastProvinceCode=PorvinceCode;
        List<City> cities=dal_area.getAllCitys(PorvinceCode);
        mRvArea.setAdapter(new Adapter_Citys(getContext(), cities, new OnClickItemHander()));
    }
    private void loadCounty(int citycode)
    {
        mBtnBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(citycode+"");
        mLastCityCode=citycode;
        mCurrentPage=enum_page.page_county;
        mLastCityCode=citycode;
        List<County> counties=dal_area.getAllCounty(citycode);
        mRvArea.setAdapter(new Adapter_County(counties, getContext(),new OnClickItemHander()));
    }
    private void loadWeatherPage(String countycode)
    {
        LSComponentsHelper.LS_Log.Log_INFO(countycode);
        mLastCounty=countycode;
    }


    //region  onclick item
    public class OnClickItemHander implements Adapter_Province.IOnClickItem
    {
        @Override
        public void OnClickProvince(int PorvinceCode)
        {
            loadCitys(PorvinceCode);
        }

        @Override
        public void OnClickCity(int citycode)
        {
            loadCounty(citycode);
        }

        @Override
        public void OnClickCounty(String countyCode)
        {
            loadWeatherPage(countyCode);
        }
    }
    //endregion
}