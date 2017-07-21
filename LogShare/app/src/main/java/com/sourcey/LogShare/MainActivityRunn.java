package com.sourcey.LogShare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sourcey.LogShare.R;

public class MainActivityRunn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_runn);
    }
    @Override
    public void onBackPressed()
    {
        //Disable Back Button
    }
}
