package com.example.android.belidiet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class DeitPlans extends AppCompatActivity {

    ListView listView;
    String  sPackName, sPackMainDesc, sPackDesc, sUrl;
    ArrayList<ModelMP> arrayList = new ArrayList<ModelMP>();
    ListViewAdapterMP adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deit_plans_activity);
        listView = findViewById( R.id.listView );

        Query packQuery = FirebaseDatabase.getInstance().getReference( Constants.PACK_LIST_PATH);
        packQuery.addValueEventListener( packListVLE);

    }
    ValueEventListener packListVLE = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            arrayList.clear();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                sPackName = "" + postSnapshot.child( "packName" ).getValue();
                sUrl = "" + postSnapshot.child( "packUrl" ).getValue();
                sPackMainDesc = "" + postSnapshot.child( "packMainDesc" ).getValue();
                sPackDesc = "" + postSnapshot.child( "packDesc" ).getValue();

                ModelMP modelMP = new ModelMP( sPackName,sPackDesc,sPackMainDesc,sUrl);
                arrayList.add( modelMP );
            }
            adapter = new ListViewAdapterMP( getApplicationContext(), arrayList );
            listView.setAdapter( adapter );
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
