package com.north.light.datesel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.north.light.libdatesel.DateMain;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateMain.getInstance().show(MainActivity.this,1);
            }
        });
    }
}
