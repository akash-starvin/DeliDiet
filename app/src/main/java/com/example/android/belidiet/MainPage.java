package com.example.android.belidiet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView jtvMEmail, jtvMName;
    String sFBName, sFBPhone, sUrl;
    ImageView jimgM, jimgChoosePlan, jimgCreatePlan;
    Button jbtnGetStarted;
    Uri uri;
    private StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference( Constants.USER_ACCOUNT_PATH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_activity);

        setMyDrawer();
        storageReference = FirebaseStorage.getInstance().getReference(Constants.USER_PROFILE_IMG_PATH);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        jtvMEmail = (TextView) headerView.findViewById(R.id.tvMEmail);
        jtvMName = (TextView) headerView.findViewById(R.id.tvMName);
        jtvMEmail.setText(Constants.EMAIL);

        jimgM = (ImageView)headerView.findViewById(R.id.imageM);
        Picasso.get().load(R.drawable.clip_art_user).into(jimgM);

        jimgChoosePlan = findViewById(R.id.imgChoosePlan);
        jimgCreatePlan = findViewById(R.id.imgCreatePlan);

        jbtnGetStarted = findViewById(R.id.btnGetStarted);


        jimgM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType( "image/*" );
                intent.setAction( Intent.ACTION_GET_CONTENT );
                startActivityForResult( Intent.createChooser( intent, "Select Picture" ), 1 );
            }
        });

        jimgChoosePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DeitPlans.class);
                startActivity(intent);
            }
        });
        jimgCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ViewAllMeals.class);
                startActivity(intent);
            }
        });
        jbtnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DeitPlans.class);
                startActivity(intent);
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference( Constants.USER_ACCOUNT_PATH ).orderByChild( "email" ).equalTo( Constants.EMAIL );
        query.addValueEventListener( valueEventListener );
    }




    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                 sFBName = "" + postSnapshot.child("accName").getValue();
                 sFBPhone = "" + postSnapshot.child("phone").getValue();
                 sUrl = "" + postSnapshot.child("url").getValue();
            }
            jtvMName.setText(sFBName);
            Constants.PHONE = sFBPhone;
            Constants.URL = sUrl;
            try {
                if (! sUrl.isEmpty())
                    Picasso.get().load(sUrl).into(jimgM);
            }
            catch (Exception e ){
                Toast.makeText(MainPage.this, "Profile image not found, please update it", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private void setMyDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void uploadImage() {
        //name of the image will be users email ID so that it'll be unique
        final StorageReference ref = storageReference.child(Constants.EMAIL);

        //uploads the image
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Uri downloadUrl = uri;
                        sUrl = downloadUrl.toString();
                        FBGetterSetter fbGetterSetter = new FBGetterSetter(sFBName,Constants.PHONE, Constants.EMAIL, sUrl);
                        myRef.child(Constants.USER_ACCOUNT_ID + Constants.PHONE).setValue(fbGetterSetter);
                        Toast.makeText(MainPage.this, "Profile pic uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "Error while uploading image" + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                jimgM.setImageBitmap(bitmap);
                Toast.makeText(this, "Please wait untill profile pic gets uploaded", Toast.LENGTH_SHORT).show();
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //Top rigjt menu
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
           finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here which will be in left side.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(),Profile.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_cart) {
            Intent intent = new Intent(getApplicationContext(),Cart.class);
            startActivity(intent);
        }
        else if (id == R.id.deit_plan) {
            Intent intent = new Intent(getApplicationContext(),DeitPlans.class);
            startActivity(intent);
        }
        else if (id == R.id.meals_list) {
            Intent intent = new Intent(getApplicationContext(),ViewAllMeals.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_mini_doc) {
            Intent intent = new Intent(getApplicationContext(),MiniDoc.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_water) {
            Intent intent = new Intent(getApplicationContext(),Water.class);
            startActivity(intent);
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}


