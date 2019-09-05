package com.linson.android.hiandroid2.MyContent;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linson.LSLibrary.AndroidHelper.LSComponentsHelper;
import com.linson.android.hiandroid2.R;

import java.util.List;


public class ContentIndex extends AppCompatActivity implements View.OnClickListener
{

    private Button mBtnShowcontact;



    //region  findcontrols and bind click event.
    private void findControls()
    {   //findControls
        mBtnShowcontact = (Button) findViewById(R.id.btn_showcontact);

        //set event handler
        mBtnShowcontact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_showcontact:
            {
                try
                {
                    showcontact();
                } catch (Exception e)
                {
                    LSComponentsHelper.LS_Log.Log_Exception(e);
                }
                break;
            }
            default:
            {
                break;
            }
        }
    }
    //endregion

    //region other member variable
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_index);
        findControls();
    }

    private void showcontact()
    {
        if(ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE")==PackageManager.PERMISSION_GRANTED)
        {
            call();
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode==1)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                call();
            }
            else
            {
                Toast.makeText(this, "no", Toast.LENGTH_SHORT);
            }
        }
    }

    private void call()
    {
        Intent intent_call=new Intent(Intent.ACTION_CALL);
        intent_call.setData(Uri.parse("tel:10086"));
        startActivity(intent_call);
    }
}