package com.example.android.belidiet.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.example.android.belidiet.firebase.FBWaterGetSet;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Water extends AppCompatActivity {

    Button jbtnDec, jbtnInc,jbtn1,jbtn2,jbtn3,jbtn4,jbtn5;
    TextView jtvCount;
    int count = 0, max = 10;
    String date, sCount, sMin, fbDate="";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(Constants.WATER_PATH);
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.water_activity);

        jbtnDec = findViewById(R.id.btnWADec);
        jbtnInc = findViewById(R.id.btnWAInc);
        jtvCount = findViewById(R.id.tvWACount);
        jbtn1 = findViewById(R.id.btnW1);
        jbtn2 = findViewById(R.id.btnW2);
        jbtn3 = findViewById(R.id.btnW3);
        jbtn4 = findViewById(R.id.btnW4);
        jbtn5 = findViewById(R.id.btnW5);

        query = FirebaseDatabase.getInstance().getReference(Constants.WATER_PATH).orderByChild("email").equalTo(Constants.EMAIL);
        query.addValueEventListener(VLE);
        jbtnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCount(count += 1);
                setColor(count);
                if (count >= max) {
                    Toast.makeText(Water.this, "Wow!!! You had enough water today", Toast.LENGTH_SHORT).show();
                }
            }
        });
        jbtnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0)
                    Toast.makeText(Water.this, "Can't decrement", Toast.LENGTH_SHORT).show();
                else
                    setCount(count-=1);
                removeColor(count);
            }
        });
        Query query2 = FirebaseDatabase.getInstance().getReference( Constants.USER_ACCOUNT_PATH ).orderByChild( "email" ).equalTo( Constants.EMAIL );
        query2.addValueEventListener( valueEventListener );


    }

    private void removeColor(int x) {
        int col = Color.parseColor("#b6bdc6");
        switch (x)
        {
            case 0:
                jbtn5.setBackgroundColor(col);
                break;
            case 2:
                jbtn4.setBackgroundColor(col);
                break;
            case 4:
                jbtn3.setBackgroundColor(col);
                break;
            case 6:
                jbtn2.setBackgroundColor(col);
                break;
            case 8:
                jbtn1.setBackgroundColor(col);
                break;
        }
    }
    private void setColor(int x) {
        int col = Color.parseColor("#80c5de");
        switch (x)
        {
            case 2:
                jbtn5.setBackgroundColor(col);
                break;
            case 4:
                jbtn4.setBackgroundColor(col);
                break;
            case 6:
                jbtn3.setBackgroundColor(col);
                break;
            case 8:
                jbtn2.setBackgroundColor(col);
                break;
            case 10:
                jbtn1.setBackgroundColor(col);
                break;
        }

    }
    private void setCount(int x) {
        try {
            date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
            if (!fbDate.equals(date)) {
                x = 1;
                count = 1;
            }
            jtvCount.setText(x + " of " + max + " glasses");
            Log.e("=======",String.valueOf(x));
            FBWaterGetSet water = new FBWaterGetSet(date, String.valueOf(x), Constants.EMAIL);
            myRef.child(Constants.WATER_ID + Constants.PHONE).setValue(water);

        }catch (NullPointerException e){}
    }
    ValueEventListener VLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                sCount = "" + postSnapshot.child("num").getValue();
                fbDate = "" + postSnapshot.child("date").getValue();
            }
            try {
                count = Integer.parseInt(sCount);
                setCount(count);
                int col = Color.parseColor("#80c5de");
                if (count > 0) {
                    if (count >= 2)
                        jbtn5.setBackgroundColor(col);
                    if (count >= 4)
                        jbtn4.setBackgroundColor(col);
                    if (count >= 6)
                        jbtn3.setBackgroundColor(col);
                    if (count >= 8)
                        jbtn2.setBackgroundColor(col);
                    if (count >= 10)
                        jbtn1.setBackgroundColor(col);
                }
            }catch (Exception e){}
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Constants.PHONE = "" + postSnapshot.child("phone").getValue();
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
