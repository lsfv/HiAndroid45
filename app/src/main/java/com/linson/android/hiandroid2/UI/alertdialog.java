package com.linson.android.hiandroid2.UI;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linson.LSLibrary.AndroidHelper.LSBaseActivity;
import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.LSLibrary.CustomUI.FullScreenDialog;
import com.linson.android.DAL.SampleData;
import com.linson.android.hiandroid2.R;

public class alertdialog extends LSBaseActivity implements View.OnClickListener
{
    private Button mBtnCustomalertdialog;
    private Button mBtnFulldialog;
    private Button mBtnProgress;
    private Button mBtnMulchoose;
    private Button mBtnSingle;
    private Button mBtnYesornot;





    //
    private int mChoosedSubject=-1;
    private boolean[] mChooseShops;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);

        findControls();
        int size=SampleData.getSubjects().length;
        mChooseShops=new boolean[size];
        for(boolean item:mChooseShops)
        {
            item=false;
        }
    }

    private void findControls()
    {
        mBtnCustomalertdialog = (Button) findViewById(R.id.btn_customalertdialog);
        mBtnFulldialog = (Button) findViewById(R.id.btn_fulldialog);
        mBtnProgress = (Button) findViewById(R.id.btn_progress);
        mBtnMulchoose = (Button) findViewById(R.id.btn_mulchoose);
        mBtnSingle = (Button) findViewById(R.id.btn_single);
        mBtnYesornot = (Button) findViewById(R.id.btn_yesornot);

        mBtnFulldialog.setOnClickListener(this);
        mBtnProgress.setOnClickListener(this);
        mBtnMulchoose.setOnClickListener(this);
        mBtnSingle.setOnClickListener(this);
        mBtnYesornot.setOnClickListener(this);
        mBtnCustomalertdialog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_yesornot:
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("DO you like cake?");
                builder.setMessage("Do you want to eat a cake?please choose your choise.");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener()//否定
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        LSComponentsHelper.Log_INFO("ok");
                    }
                });

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener()//积极。
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        LSComponentsHelper.Log_INFO("positive");
                    }
                });


                builder.setNeutralButton("Close", new DialogInterface.OnClickListener()//中立
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        LSComponentsHelper.Log_INFO("close");
                        dialog.dismiss();
                    }

                });
                builder.create().show();
                break;
            }
            case R.id.btn_single:
            {
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Choose subject");
                builder.setSingleChoiceItems(SampleData.getSubjects(), mChoosedSubject, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mChoosedSubject=which;
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            }
            case R.id.btn_mulchoose:
            {
                try
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("Choose subject");
                    builder.setMultiChoiceItems(SampleData.getSubjects(), mChooseShops, new DialogInterface.OnMultiChoiceClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked)
                        {
                            mChooseShops[which]=isChecked;
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                catch (Exception e)
                {
                    LSComponentsHelper.Log_Exception(e);
                }
                break;
            }
            case R.id.btn_progress:
            {
                //progressdiag();
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("DO you like cake?");
                builder.setMessage("Do you want to eat a cake?please choose your choise.");
                builder.setCancelable(false);



                ProgressBar mypb=new ProgressBar(this);
                builder.setView(new ProgressBar(this));

                builder.create().show();
                break;
            }
            case R.id.btn_customalertdialog:
            {
                CustomDialog tempDialog=new CustomDialog(this);
                tempDialog.show();
                break;
            }
            case R.id.btn_fulldialog:
            {
                FullDialog tempDialog=new FullDialog(this);
                tempDialog.show();
                break;
            }
        }
    }



    //class
    //custmo alertdialog
    private static class CustomDialog extends Dialog implements View.OnClickListener
    {
        private EditText mEtPassword;
        private Button mBtnOk;

        protected CustomDialog(@NonNull Context context)
        {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_password);

            findcontrols();
        }

        private void findcontrols()
        {
            mEtPassword = (EditText) findViewById(R.id.et_password);
            mBtnOk = (Button) findViewById(R.id.btn_ok);

            mBtnOk.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            LSComponentsHelper.Log_INFO(mEtPassword.getText().toString());
            this.dismiss();
        }
    }

    private class tt extends FullScreenDialog
    {
        public tt(Context context)
        {
            super(context);
        }
    }



    //和自定义弹框一样。只是中间多继承了一层。中间的类实现了全屏。
    private class FullDialog extends FullScreenDialog implements View.OnClickListener
    {
        private EditText mEtPassword;
        private Button mBtnOk;

        protected FullDialog(@NonNull Context context)
        {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_password);

            findcontrols();
        }

        private void findcontrols()
        {
            mEtPassword = (EditText) findViewById(R.id.et_password);
            mBtnOk = (Button) findViewById(R.id.btn_ok);

            mBtnOk.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            LSComponentsHelper.Log_INFO(mEtPassword.getText().toString());
            this.dismiss();
        }
    }

    private void progressdiag()
    {
        ProgressDialog pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(final DialogInterface dialog)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            Thread.sleep(4000);
                        } catch (InterruptedException e)
                        {
                            LSComponentsHelper.Log_Exception(e);
                        }
                        dialog.dismiss();
                    }
                }).start();
            }
        });
        pd.show();
    }
}
