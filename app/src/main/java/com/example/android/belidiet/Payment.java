package com.example.android.belidiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.*;

public class Payment extends AppCompatActivity {

    Button jbtnPay;
    Spinner jspinMM, jspinYY;
    EditText jetName, jetNum, jetCvv;
    String name, num, cvv, month, year, cardType="";
    RadioGroup jrgCardType;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference(Constants.CART_PATH);
    String[] x = new String[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);

        jbtnPay = findViewById(R.id.btnPay);
        jetName = findViewById(R.id.etPAYName);
        jetNum = findViewById(R.id.etPAYNum);
        jetCvv = findViewById(R.id.etPAYCvv);
        jrgCardType = findViewById(R.id.rgPAYCardType);

        setSpinner();
        jbtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag = getdata();
                if(flag) {
                    finish();
                    removeItems();
                    Intent intent = new Intent(getApplicationContext(),VerifyPhone.class);
                    startActivity(intent);
                }

            }
        });
        findViewById(R.id.btnPAYCOD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                removeItems();
                Intent intent = new Intent(getApplicationContext(),ConfirmAddress.class);
                startActivity(intent);
            }
        });
        jrgCardType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbPAYVisa:
                        cardType = "Visa";
                        break;
                    case R.id.rbPAYMaster:
                        cardType = "Master";
                        break;
                }
            }
        });

        Query proQuery = FirebaseDatabase.getInstance().getReference(Constants.USER_ACCOUNT_PATH).orderByChild("email").equalTo(Constants.EMAIL);
        proQuery.addValueEventListener(nameVLE);

        Query packQuery = FirebaseDatabase.getInstance().getReference(Constants.CART_PATH).orderByChild("cPhone").equalTo(Constants.PHONE);
        packQuery.addValueEventListener(cartVLE);
    }

    private void removeItems() {
        try {
            Log.e("======array", String.valueOf(x.length));
            for (int j = 0; j < x.length; j++) {
                ref.child(x[j]).removeValue();
                Log.e("======", String.valueOf(j));
            }

        }catch (Exception e){}
    }

    ValueEventListener cartVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            int i=-1;
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                try {
                    i++;
                    x[i] = "" + postSnapshot.getKey();

                }
                catch (Exception e){}
                }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private boolean getdata() {
        boolean flag=true;
        num = jetNum.getText().toString();
        cvv = jetCvv.getText().toString();
        month = jspinMM.getSelectedItem().toString();
        year = jspinYY.getSelectedItem().toString();
        if(num.equals("")||num.length()!=16)
        {
            jetNum.setError("Enter a valid card number");
            flag = false;
        }
        else if(cvv.equals("")||cvv.length()!=3)
        {
            jetCvv.setError("Enter a valid CVV");
            flag = false;
        }else if(jspinMM.getSelectedItemPosition()==0)
        {
            Toast.makeText(this, "Select expiry month", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        else if(jspinYY.getSelectedItemPosition()==0)
        {
            Toast.makeText(this, "Select expiry year", Toast.LENGTH_SHORT).show();
            flag = false;
        }else if(cardType.equals(""))
        {
            Toast.makeText(this, "Select card type", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        return flag;
    }

    private void setSpinner() {
        jspinMM = findViewById(R.id.spinPAYMM);
        jspinYY = findViewById(R.id.spinPAYYY);
        ArrayAdapter<CharSequence> adapterMM = ArrayAdapter.createFromResource( this, R.array.expiryMM, android.R.layout.simple_spinner_item );
        adapterMM.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        jspinMM.setAdapter( adapterMM );

        ArrayAdapter<CharSequence> adapterYY = ArrayAdapter.createFromResource( this, R.array.expiryYY, android.R.layout.simple_spinner_item );
        adapterYY.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        jspinYY.setAdapter( adapterYY );

    }
    ValueEventListener nameVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                name = "" + postSnapshot.child("accName").getValue();
            }
            jetName.setText(name);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
