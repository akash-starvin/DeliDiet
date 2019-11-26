package com.example.android.belidiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.*;

public class ConfirmAddress extends AppCompatActivity {

    EditText jetAddress;
    String address;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference(Constants.ORDER_PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_address_activity);

         jetAddress= findViewById(R.id.etConAd);

        Query accQuery = FirebaseDatabase.getInstance().getReference(Constants.USER_PROFILE_PATH).orderByChild("email").equalTo(Constants.EMAIL);
        accQuery.addValueEventListener(accVLE);

        findViewById(R.id.btnConEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jetAddress.setEnabled(true);
            }
        });

        findViewById(R.id.btnConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jetAddress.getText().toString().isEmpty())
                    Toast.makeText(ConfirmAddress.this, "Enter address", Toast.LENGTH_SHORT).show();
                else {
                    //Inserting into database
                    FBOrderDetails fbOrderDetails = new FBOrderDetails("",Constants.PHONE,String.valueOf(Constants.CART_PRICE),jetAddress.getText().toString());
                    myRef.push().setValue(fbOrderDetails);

                    finish();   //This is used to close the cuurent activity completly
                    Intent intent = new Intent(getApplicationContext(), Delivery.class);
                    startActivity(intent);
                }
            }
        });
    }
    ValueEventListener accVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                address = "" + postSnapshot.child("city").getValue();
            }
            jetAddress.setText(address);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
