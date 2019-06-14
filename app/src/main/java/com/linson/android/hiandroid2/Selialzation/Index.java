package com.linson.android.hiandroid2.Selialzation;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.IO.LSFileHelper;
import com.linson.android.hiandroid2.R;

import java.lang.reflect.Type;


//数据的初始化，可以直接在程序进入第一个页面的时候，就copy数据库。如果为空。
public class Index extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnAdd;
    private Button mBtnDelete;
    private Button mBtnInitionliza;
    private Button mBtnInitionliza1;

    private NormalData mnormalData;
    private  int mid=1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index6);

        findControls();
    }

    private void findControls()
    {
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        mBtnInitionliza = (Button) findViewById(R.id.btn_initionliza);
        mBtnInitionliza1 = (Button) findViewById(R.id.btn_initionliza1);

        mBtnAdd.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnInitionliza.setOnClickListener(this);
        mBtnInitionliza1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_initionliza1:
            {
                if(mnormalData!=null)
                {
                    mnormalData.close();
                }
                mnormalData=new NormalData(this, "mybooks", null, 1, 1);
                break;
            }
            case R.id.btn_initionliza:
            {
                if(mnormalData!=null)
                {
                    mnormalData.close();
                }
                //
                LSFileHelper.CopyDataBaseFromResource("mybooks", this,true);
                mnormalData=new NormalData(this, "mybooks", null, 1, 2);
                break;
            }
            case R.id.btn_add:
            {
                try
                {
                    mnormalData.getReadableDatabase().execSQL("insert into books (id) values ("+mid+")");
                    LSComponentsHelper.Log_INFO(mid+"dddddddddd");
                    mid=mid+1;
                    showdb("books");
                } catch (SQLException e)
                {
                    LSComponentsHelper.Log_Exception(e);
                }
                break;
            }
            case R.id.btn_delete:
            {
                mnormalData.getReadableDatabase().execSQL("delete  from books ");
                showdb("books");
                break;
            }
        }
    }

    private void showdb(String tablename)
    {
        LSComponentsHelper.Log_INFO(mnormalData.getDatabaseName());
        Cursor mycursor= mnormalData.getReadableDatabase().rawQuery("select * from "+tablename, null);
        if(mycursor.moveToFirst())
        {
            int rows=mycursor.getCount();
            int columns=mycursor.getColumnCount();

            LSComponentsHelper.Log_INFO(rows+"."+columns);

            String[] mydata=new String[rows*columns];

            for(int i=0;i<rows;i++)
            {

                for(int j=0;j<columns;j++)
                {
                    mydata[j+(i*columns)]=mycursor.getInt(j)+"";
                }
                mycursor.moveToNext();
            }


            mycursor.close();
            for(String item :mydata)
            {
                LSComponentsHelper.Log_INFO(item);
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mnormalData.close();
    }

    private static class NormalData extends SQLiteOpenHelper
    {
        private int mType=1;
        public NormalData(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version,int type)
        {
            super(context, name, factory, version);
            mType=type;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            if(mType==1)
            {
                String sql_table="Create table books(id int)";
                db.execSQL(sql_table);
            }
            else if(mType==2)
            {

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }

}