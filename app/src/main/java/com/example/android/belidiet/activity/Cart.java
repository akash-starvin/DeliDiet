package com.example.android.belidiet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.belidiet.Constants;
import com.example.android.belidiet.R;
import com.example.android.belidiet.adapter.ListViewAdapterCart;
import com.example.android.belidiet.model.ModelCart;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ListView listView;
    TextView jtvCRTotal;
    Button jbtnPayment;

    private ArrayList<ModelCart> arrayList = new ArrayList<ModelCart>();
    private ListViewAdapterCart adapter;
    private String sName, sQty, sPrice, sId;
    private int price;

    private ModelCart model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        getSupportActionBar().setTitle("Cart");

        listView = findViewById(R.id.lvCart);
        jtvCRTotal = findViewById(R.id.tvCRTotal);
        jbtnPayment = findViewById(R.id.btnPayment);
        jbtnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(price==0)
                    Toast.makeText(Cart.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                else {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), Payment.class);
                    startActivity(intent);
                }
            }
        });
        //Query is used to get data from database
        //It'll refer to CART ROOT in firebase , then it'll order the items by PHONE NUMBER then it'll compare phone number with current user's number
        Query packQuery = FirebaseDatabase.getInstance().getReference(Constants.CART_PATH).orderByChild("cPhone").equalTo(Constants.PHONE);
        packQuery.addValueEventListener(cartVLE);

    }

    //This will be called when the query is executed
    ValueEventListener cartVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            arrayList.clear();
            price = 0;

            //dataSnapshot will have all the data items fetched from firebase
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                sName = "" + postSnapshot.child( "cName" ).getValue();
                sQty = "Quantity : " + postSnapshot.child( "cQty" ).getValue();
                sPrice = "" + postSnapshot.child( "cPrice" ).getValue();
                sId = "" + postSnapshot.getKey();

                //calculating total cart amount
                price += Integer.parseInt(sPrice);

                //Adding those data items to MODEL Class
                model = new ModelCart( sName,sQty,"Rs."+sPrice, sId);

                //Adding MODEL to array
                arrayList.add( model );

                Constants.CART_PRICE = price;
            }

            //Adds items from array to Listview
            adapter = new ListViewAdapterCart( getApplicationContext(), arrayList );
            listView.setAdapter( adapter );
            jtvCRTotal.setText("Total Rs."+price);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
