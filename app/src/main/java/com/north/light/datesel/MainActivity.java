package com.north.light.datesel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.north.light.libdatesel.DateMain;
import com.north.light.libdatesel.bean.DateSelResult;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateMain.getInstance().show(MainActivity.this, 1);
            }
        });
        DateMain.getInstance().setOnDateListener(new DateMain.DateSelInfoCallBack() {
            @Override
            public void Date(DateSelResult result) {
                Log.d(TAG, "选择的日期: " + new Gson().toJson(result));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
