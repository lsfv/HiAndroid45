package com.linson.android.hiandroid2.OPAIWeather.Control;

import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.Fragment.FirstFragment;
import com.linson.android.hiandroid2.OPAIWeather.DAL.DAL_Area;
import com.linson.android.hiandroid2.OPAIWeather.DAL.DAL_CreateDB;
import com.linson.android.hiandroid2.R;

import org.litepal.LitePal;


//书上的范例写的实在有点糟糕。总体 从数据库或者网络的思路还可以，但是封装太差，而又用一个adapter，通过变化数据来展示不同界面。实在是忍受不了。
//可扩展性实在太糟糕。实在忍受不了。而作者水平是牛的，只能解释为只想把代码减少,好阅读。所以层次性，封装性不是优先考虑的问题。
//再吐槽一次。连json的设计也无力吐槽。
//数据库必须创建成功才能进入首页。否则推出程序。
public class Index extends AppCompatActivity
{
    private ProgressBar mPBPROGRESS;
    private TextView mTVPBMSG;

    //region  findcontrols and bind click event.  remember call me in fun:onCreate!!!
    private void findControls()
    {   //findControls
        mPBPROGRESS = (ProgressBar) findViewById(R.id.PB_PROGRESS);
        mTVPBMSG = (TextView) findViewById(R.id.TV_PBMSG);
    }
    //endregion
    //member variable

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index16);

        findControls();
        createDB();
    }

    //存在就直接进入，否则就等待回调：1.成功，进入。2 失败，回退
    private void createDB()
    {
        DAL_CreateDB dal_createDB = new DAL_CreateDB(this);
        boolean  isExist= dal_createDB.isExistOtherwiseCreate(new OnCreateHander());
        if(isExist)
        {
            LSComponentsHelper.startActivity(Index.this, Main.class);
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        finish();//不需要回退
    }


    //region hander
    private class OnCreateHander implements DAL_CreateDB.IOncreateDBHandle
    {
        @Override
        public void OngetProcess(Message progress)
        {
            if(progress.what==1)
            {
                LSComponentsHelper.LS_Log.Log_INFO("progress:"+progress.arg1+"");
                mPBPROGRESS.setProgress(progress.arg1);
                mTVPBMSG.setText(progress.arg1 + "%");
            }
            else if(progress.what==2)
            {
                LSComponentsHelper.LS_Log.Log_INFO("result:"+progress.arg1+"");

                if(progress.arg1==1)
                {
                    LSComponentsHelper.startActivity(Index.this, Main.class);
                }
                else
                {
                    mTVPBMSG.setText("error:"+progress.arg1);
                    DAL_CreateDB dal_createDB = new DAL_CreateDB(Index.this);
                    dal_createDB.deletedb();
                    Toast.makeText(Index.this, "error:"+progress.arg1, Toast.LENGTH_SHORT).show();
                    finish();//如果失败就删除数据库并返回。
                }
            }
        }
    }
    //endregion
}