package com.example.android.belidiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Delivery extends AppCompatActivity {

    TextView jetDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_activity);

        jetDate =findViewById(R.id.tvDate);

        findViewById(R.id.btnDDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(),MainPage.class);
                startActivity(intent);
            }
        });
    }
}
