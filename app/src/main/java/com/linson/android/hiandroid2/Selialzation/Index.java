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

import org.litepal.LitePal;


//数据的初始化，可以直接在程序进入第一个页面的时候，就copy数据库。如果为空。
public class Index extends AppCompatActivity implements View.OnClickListener
{
    private Button mBtnDelete2;
    private Button mBtnAdd2;
    private Button mBtnAdd;
    private Button mBtnDelete;
    private Button mBtnInitionliza;
    private Button mBtnInitionliza1;
    private View mView;
    private Button mBtnCreate2;
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
        mBtnDelete2 = (Button) findViewById(R.id.btn_delete2);
        mBtnAdd2 = (Button) findViewById(R.id.btn_add2);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
        mBtnInitionliza = (Button) findViewById(R.id.btn_initionliza);
        mBtnInitionliza1 = (Button) findViewById(R.id.btn_initionliza1);
        mView = (View) findViewById(R.id.view);
        mBtnCreate2 = (Button) findViewById(R.id.btn_create2);

        mBtnAdd.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnInitionliza.setOnClickListener(this);
        mBtnInitionliza1.setOnClickListener(this);
        mBtnCreate2.setOnClickListener(this);
        mBtnAdd2.setOnClickListener(this);
        mBtnDelete2.setOnClickListener(this);
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

            case R.id.btn_create2:
            {
                create2();
                break;
            }
            case R.id.btn_add2:
            {
                add2();
                break;
            }
            case R.id.btn_delete2:
            {
                delete2();
                break;
            }
        }
    }


    /***
     * sql lite 还是不太方便。
     * 是需要一个 数据库 的 ORM   .对象关系映射。
     * 有一个问题。如果是组合关系。数据库该如何处理呢？ 所以应该是所有表都会加自增列？
     * 如果我想要一个增列呢？那么可能会有这样一个概念。自增列就是一个内存指针的概念，除非
     * 还会保留值。那么新列的默认值呢？
     * 1.非常普通的类。直接MAP，没有自增列。
     *
     */
    private void add2()
    {
        try
        {
            LitePal.getDatabase();
            LSComponentsHelper.LS_Log.Log_INFO("add");
            Pen pen = new Pen();
            pen.color = "green";
            pen.type = 1;
            pen.save();

            Student student=new Student();
            student.name="linson";
            student.mPen=pen;
            student.save();


        } catch (Exception e)
        {
            LSComponentsHelper.LS_Log.Log_Exception(e);
        }
    }

    private void create2()
    {

    }
    private void delete2()
    {

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


    //region sqllithelper
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
    //endregion
}