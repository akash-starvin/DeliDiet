package com.example.android.belidiet;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.*;
import com.google.firebase.database.*;
import java.util.ArrayList;

public class Meals extends AppCompatActivity {

    TextView jetMealsPackName;
    String sUrl, sPackName, sPackMainDesc, sPackDesc, filter;
    Boolean isFilter = false;
    ListView listView;
    RadioGroup rdgFilter;
    ArrayList<ModelMP> arrayList = new ArrayList<ModelMP>();
    ListViewAdapterMeals adapter;
    ModelMP modelMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meals_activity);

        listView = findViewById(R.id.listviewMeals);
        jetMealsPackName = findViewById(R.id.tvMealsPackName);
        jetMealsPackName.setText(Constants.PACK_NAME);

        rdgFilter = findViewById(R.id.rdg);
        final Query packQuery = FirebaseDatabase.getInstance().getReference(Constants.MEAL_LIST_PATH).orderByChild("mealPack").equalTo(Constants.PACK_NAME);
        packQuery.addValueEventListener(packListVLE);
        rdgFilter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rd1:
                        filter = "Breakfast";
                        isFilter = true;
                        break;
                    case R.id.rd2:
                        filter = "Lunch";
                        isFilter = true;
                        break;
                    case R.id.rd3:
                        filter = "Snacks";
                        isFilter = true;
                        break;
                    case R.id.rd4:
                        filter = "Dinner";
                        isFilter = true;
                        break;
                    case R.id.rd5:
                        isFilter = false;
                        break;
                }
                packQuery.addValueEventListener(packListVLE);
            }
        });

    }
    ValueEventListener packListVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            try {
                arrayList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    sPackName = "" + postSnapshot.child("mealName").getValue();
                    sUrl = "" + postSnapshot.child("mealImg").getValue();
                    sPackMainDesc = "Rs. " + postSnapshot.child("mealPrice").getValue();
                    sPackDesc = "" + postSnapshot.child("mealDesc").getValue();

                    if (isFilter) {
                        if (sPackName.contains(filter)) {
                            modelMP = new ModelMP(sPackName, sPackDesc, sPackMainDesc, sUrl);
                            arrayList.add(modelMP);
                        }
                    } else {
                        modelMP = new ModelMP(sPackName, sPackDesc, sPackMainDesc, sUrl);
                        arrayList.add(modelMP);
                    }
                }
                adapter = new ListViewAdapterMeals(getApplicationContext(), arrayList);
                listView.setAdapter(adapter);
            }
            catch (Exception e){}
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void onCheckboxClicked(View view) {

    }
}
