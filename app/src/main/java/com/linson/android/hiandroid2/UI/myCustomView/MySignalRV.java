package com.linson.android.hiandroid2.UI.myCustomView;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.linson.android.hiandroid2.Adapter.AdapterSignalRecycleView;
import com.linson.android.hiandroid2.R;
import com.linson.android.hiandroid2.UI.myCustomView.cc.SignalRecycleView;

import java.util.ArrayList;
import java.util.List;


public class MySignalRV extends AppCompatActivity implements View.OnClickListener
{
    private SignalRecycleView mSignalrv;



    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mSignalrv = (SignalRecycleView) findViewById(R.id.signalrv);

        //set event handler
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

            default:
            {
                break;
            }
        }
    }
    //endregion

    //member variable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_signal_rv);

        findControls();

        List<Drawable> drawables=new ArrayList<>();
        drawables.add(getResources().getDrawable(R.drawable.wxfix));
        drawables.add(getResources().getDrawable(R.drawable.mypic));
        drawables.add(getResources().getDrawable(R.drawable.back));
        drawables.add(getResources().getDrawable(R.drawable.wxfix));
        drawables.add(getResources().getDrawable(R.drawable.mypic));
        drawables.add(getResources().getDrawable(R.drawable.back));

        AdapterSignalRecycleView adapterSignalRecycleView=new AdapterSignalRecycleView(this, drawables);

        mSignalrv.setAdapter(adapterSignalRecycleView);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        mSignalrv.setLayoutManager(layoutManager);
    }
}