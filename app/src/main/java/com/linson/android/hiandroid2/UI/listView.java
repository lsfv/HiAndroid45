package com.linson.android.hiandroid2.UI;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.DAL.SampleData;
import com.linson.android.hiandroid2.Adapter.SimpleShop;
import com.linson.android.hiandroid2.Adapter.SimpleShop2;
import com.linson.android.hiandroid2.Adapter.SimpleShop_Recycle;
import com.linson.android.hiandroid2.R;
public class listView extends LSBaseActivity implements View.OnClickListener
{
    private Button mBtnRecycle3;
    private Button mBtnRecycle;
    private Button mBtnRecycle2;
    private Button mBtnRecycle1;
    private Button mBtnListview;
    private ListView mLvTest;
    private RecyclerView mMCecylelist1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        findControls();
    }

    private void findControls()
    {
        mBtnRecycle3 = (Button) findViewById(R.id.btn_recycle3);
        mBtnRecycle = (Button) findViewById(R.id.btn_recycle);
        mBtnRecycle2 = (Button) findViewById(R.id.btn_recycle2);
        mBtnRecycle1 = (Button) findViewById(R.id.btn_recycle1);
        mBtnListview = (Button) findViewById(R.id.btn_listview);
        mLvTest = (ListView) findViewById(R.id.lv_test);
        mMCecylelist1 = (RecyclerView) findViewById(R.id.mCecylelist1);


        mBtnListview.setOnClickListener(this);
        mBtnRecycle1.setOnClickListener(this);
        mBtnRecycle2.setOnClickListener(this);
        mBtnRecycle.setOnClickListener(this);
        mBtnRecycle3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_listview:
            {
                listview();
                break;
            }
            case R.id.btn_recycle1:
            {
                recycle1();
                break;
            }
            case R.id.btn_recycle2:
            {
                recycle2();
                break;
            }
            case R.id.btn_recycle:
            {
                waterfall();
                break;
            }
            case R.id.btn_recycle3:
            {
                gridlayout();
                break;
            }
        }
    }

    private void gridlayout()
    {
        mLvTest.setVisibility(View.INVISIBLE);
        mMCecylelist1.setVisibility(View.VISIBLE);
        SimpleShop_Recycle simpleShop_recycle=new SimpleShop_Recycle(R.layout.item_shop, SampleData.getShopes2());
        mMCecylelist1.setAdapter(simpleShop_recycle);
        GridLayoutManager layoutManager=new GridLayoutManager(this,3 );
        mMCecylelist1.setLayoutManager(layoutManager);
    }



    private void waterfall()
    {
        mLvTest.setVisibility(View.INVISIBLE);
        mMCecylelist1.setVisibility(View.VISIBLE);
        SimpleShop_Recycle simpleShop_recycle=new SimpleShop_Recycle(R.layout.item_shop, SampleData.getShopes2());
        mMCecylelist1.setAdapter(simpleShop_recycle);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mMCecylelist1.setLayoutManager(layoutManager);
    }

    private void recycle2()
    {
        mLvTest.setVisibility(View.INVISIBLE);
        mMCecylelist1.setVisibility(View.VISIBLE);
        SimpleShop_Recycle simpleShop_recycle=new SimpleShop_Recycle(R.layout.item_shop, SampleData.getShopes2());
        mMCecylelist1.setAdapter(simpleShop_recycle);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mMCecylelist1.setLayoutManager(linearLayoutManager);
    }

    private void recycle1()
    {
        mLvTest.setVisibility(View.INVISIBLE);
        mMCecylelist1.setVisibility(View.VISIBLE);
        SimpleShop_Recycle simpleShop_recycle=new SimpleShop_Recycle(R.layout.item_shop, SampleData.getShopes2());
        mMCecylelist1.setAdapter(simpleShop_recycle);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mMCecylelist1.setLayoutManager(linearLayoutManager);
    }

    private void listview()
    {
        mMCecylelist1.setVisibility(View.INVISIBLE);
        mLvTest.setVisibility(View.VISIBLE);

        SimpleShop2 simpleShop=new SimpleShop2(this, R.layout.item_shop, SampleData.getShopes());
        mLvTest.setAdapter(simpleShop);
    }
}