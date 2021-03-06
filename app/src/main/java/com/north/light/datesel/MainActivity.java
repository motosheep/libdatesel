package com.north.light.datesel;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.north.light.libdatesel.DateMain;
import com.north.light.libdatesel.bean.LibDateSelResult;
import com.north.light.libdatesel.callback.DateSelInfoCallBack;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DateMain.getInstance().show(MainActivity.this, 1);
//                DateMain.getInstance().showNormal(MainActivity.this,1);
                DateMain.getDateInstance().showXSel(MainActivity.this);
            }
        });
        DateMain.getDateInstance().setOnDateListener(callBack);
    }

    private DateSelInfoCallBack callBack = new DateSelInfoCallBack() {

        @Override
        public void Date(LibDateSelResult result) {
            Log.d(TAG, "选择的日期: " + new Gson().toJson(result));
        }

        @Override
        public void timeStamp(Long time) {

        }

        @Override
        public void cancel() {

        }
    };

    @Override
    protected void onDestroy() {
        DateMain.getDateInstance().removeDateListener(callBack);
        super.onDestroy();
    }
}
