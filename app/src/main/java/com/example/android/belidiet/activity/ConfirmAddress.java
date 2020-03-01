package com.example.android.belidiet.activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.example.android.belidiet.firebase.FBOrderDetails;
import com.google.firebase.database.*;

public class ConfirmAddress extends AppCompatActivity {

    EditText jetAddress;
    private String address;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference(Constants.ORDER_PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_address_activity);

        getSupportActionBar().setTitle("Confirm your delivery address");
        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
