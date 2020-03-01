
package com.example.android.belidiet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.example.android.belidiet.firebase.FBCartGetSet;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

public class Select extends AppCompatActivity {

    String sUrl, sName, sPrice, sTime, sQty=" ", qty="";
    int count, p,q, x=0;
    TextView jtvSName;
    ImageView jimgS;
    RadioGroup jrgSQty;
    Button jbtnSBack, jbtnSCart, jbtnPlus, jbtnMinus;
    EditText jetQty;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(Constants.CART_PATH);
    CheckBox jcbTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);

        jtvSName = findViewById(R.id.tvSName);
        jimgS = findViewById(R.id.imgS);

        jbtnSCart = findViewById(R.id.btnCart);
        jbtnSBack = findViewById(R.id.btnBack);

        jbtnPlus = findViewById(R.id.btnSELPlus);
        jbtnMinus = findViewById(R.id.btnSELMinus);
        jrgSQty = findViewById(R.id.rdSQty);
        jcbTime = findViewById(R.id.cbTime);
        jetQty = findViewById(R.id.etSELQty);

        jetQty.setText(String.valueOf(x));
        jbtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = jetQty.getText().toString();
                x = Integer.parseInt(qty);
                x+=1;
                jetQty.setText(String.valueOf(x));

            }
        });
        jbtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = jetQty.getText().toString();
                x = Integer.parseInt(qty);
                x-=1;
                if(x<=0)
                    x=1;
                jetQty.setText(String.valueOf(x));
            }
        });
        jbtnSCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sName.equals("") || !sUrl.equals("")) {
                    boolean flag = getdata();
                    if (flag) {
                        calculatePrize();
                        FBCartGetSet cart = new FBCartGetSet(sName,sQty,Constants.PHONE,String.valueOf(p*q));
                        myRef.push().setValue(cart);
                        Toast.makeText(Select.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else
                {
                    Toast.makeText(Select.this, "Reload this page by going back", Toast.LENGTH_SHORT).show();
                }
            }
        });
        jbtnSBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        jrgSQty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbSQty1:
                        jetQty.setText("10");
                        break;
                    case R.id.rbSQty2:
                        jetQty.setText("22");
                        break;
                    case R.id.rbSQty3:
                        jetQty.setText("44");
                        break;
                }
            }
        });
        Query query = FirebaseDatabase.getInstance().getReference(Constants.MEAL_LIST_PATH).orderByChild("mealName").equalTo(Constants.MEAL_NAME);
        query.addValueEventListener(VLE);

    }

    private void calculatePrize() {
        p = Integer.parseInt(sPrice);
        q = Integer.parseInt(sQty);
    }

    private boolean getdata() {
        boolean flag=false;
        try {
            sQty = jetQty.getText().toString();
            if (sQty.contains(" ") || sQty.equals("0")) {
                Toast.makeText(this, "Select a quantity", Toast.LENGTH_SHORT).show();
            } else
                flag = true;
        }
        catch (Exception e){}
        return flag;
    }


    ValueEventListener VLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                sName = "" + postSnapshot.child("mealName").getValue();
                sUrl = "" + postSnapshot.child("mealImg").getValue();
                sPrice = "" + postSnapshot.child("mealPrice").getValue();
            }
            jtvSName.setText(sName);
            Picasso.get().load(sUrl).into(jimgS);
            getTimeToCheckBox();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void getTimeToCheckBox() {
        if (sName.contains("Breakfast"))
            jcbTime.setText("Breakfast");
        else if (sName.contains("Lunch"))
            jcbTime.setText("Lunch");
        else if (sName.contains("Snack"))
            jcbTime.setText("Snack");
        else
            jcbTime.setText("Dinner");
    }
}
