package com.example.android.belidiet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class ViewAllMeals extends AppCompatActivity {

    String sUrl, sPackName, sPackMainDesc, sPackDesc,filter;
    ArrayList<ModelMP> arrayList = new ArrayList<ModelMP>();
    ListViewAdapterMeals adapter;
    ListView listView;
    ModelMP modelMP;
    RadioGroup rdgFilter;
    Boolean isFilter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_meals_activity);

        listView = findViewById(R.id.lvViewAll);
        final Query query = FirebaseDatabase.getInstance().getReference(Constants.MEAL_LIST_PATH);
        query.addValueEventListener(packListVLE);
        rdgFilter = findViewById(R.id.rdg2);
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
                Log.e("=======",filter);
                query.addValueEventListener(packListVLE);
            }
        });
    }
    ValueEventListener packListVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            arrayList.clear();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                sPackName = "" + postSnapshot.child( "mealName" ).getValue();
                sUrl = "" + postSnapshot.child( "mealImg" ).getValue();
                sPackMainDesc = "Rs. " + postSnapshot.child( "mealPrice" ).getValue();
                sPackDesc = "" + postSnapshot.child( "mealDesc" ).getValue();

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
            adapter = new ListViewAdapterMeals( getApplicationContext(), arrayList );
            listView.setAdapter( adapter );
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
